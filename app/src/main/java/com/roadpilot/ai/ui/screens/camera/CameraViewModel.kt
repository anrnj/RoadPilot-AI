package com.roadpilot.ai.ui.screens.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roadpilot.ai.domain.model.LoopDuration
import com.roadpilot.ai.domain.model.Recording as RoadPilotRecording
import com.roadpilot.ai.domain.model.VideoQuality
import com.roadpilot.ai.domain.repository.LocationRepository
import com.roadpilot.ai.domain.repository.RecordingRepository
import com.roadpilot.ai.domain.repository.SettingsRepository
import com.roadpilot.ai.util.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class CameraUiState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val recordingDuration: Long = 0L,
    val recordingDurationFormatted: String = "00:00",
    val isProtected: Boolean = false,
    val currentRecording: RoadPilotRecording? = null,
    val recordings: List<RoadPilotRecording> = emptyList(),
    val videoQuality: VideoQuality = VideoQuality.HIGH,
    val loopDuration: LoopDuration = LoopDuration.THREE,
    val useFrontCamera: Boolean = false,
    val hasStoragePermission: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val error: String? = null,
    val showSettings: Boolean = false
)

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recordingRepository: RecordingRepository,
    private val settingsRepository: SettingsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    private var recordingJob: Job? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var activeRecording: androidx.camera.video.Recording? = null
    private var recordingStartTime: Long = 0L

    init {
        observeRecordings()
        observeSettings()
    }

    private fun observeRecordings() {
        viewModelScope.launch {
            recordingRepository.recordings.collect { recordings ->
                _uiState.update { it.copy(recordings = recordings) }
            }
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.videoQuality.collect { quality ->
                _uiState.update { it.copy(videoQuality = quality) }
            }
        }
        viewModelScope.launch {
            settingsRepository.loopDuration.collect { duration ->
                _uiState.update { it.copy(loopDuration = duration) }
            }
        }
    }

    fun setupCamera(
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val recorder = Recorder.Builder()
                .setQualitySelector(
                    QualitySelector.from(
                        when (_uiState.value.videoQuality) {
                            VideoQuality.HIGH -> Quality.FHD
                            VideoQuality.MEDIUM -> Quality.HD
                            VideoQuality.LOW -> Quality.SD
                        }
                    )
                )
                .build()
            
            videoCapture = VideoCapture.withOutput(recorder)

            val cameraSelector = if (_uiState.value.useFrontCamera) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    videoCapture
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to setup camera: ${e.message}") }
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun startRecording() {
        val videoCapture = this.videoCapture ?: run {
            _uiState.update { it.copy(error = "Camera not initialized") }
            return
        }

        val recordingsDir = recordingRepository.getRecordingsDirectory()
        val timestamp = TimeUtils.formatForFileName(System.currentTimeMillis())
        val fileName = "RoadPilot_$timestamp.mp4"
        val file = File(recordingsDir, fileName)

        val outputOptions = FileOutputOptions.Builder(file).build()

        activeRecording = videoCapture.output
            .prepareRecording(context, outputOptions)
            .apply {
                if (hasAudioPermission()) {
                    withAudioEnabled()
                }
            }
            .start(ContextCompat.getMainExecutor(context)) { event ->
                when (event) {
                    is VideoRecordEvent.Start -> {
                        recordingStartTime = System.currentTimeMillis()
                        _uiState.update {
                            it.copy(
                                isRecording = true,
                                isPaused = false,
                                currentRecording = RoadPilotRecording(
                                    filePath = file.absolutePath,
                                    duration = 0L,
                                    size = 0L,
                                    timestamp = recordingStartTime
                                )
                            )
                        }
                        startDurationTimer()
                    }
                    is VideoRecordEvent.Finalize -> {
                        if (event.hasError()) {
                            _uiState.update {
                                it.copy(error = "Recording failed: ${event.cause?.message}")
                            }
                            file.delete()
                        } else {
                            saveRecording(file)
                        }
                        stopDurationTimer()
                        _uiState.update {
                            it.copy(
                                isRecording = false,
                                isPaused = false,
                                recordingDuration = 0L,
                                currentRecording = null
                            )
                        }
                    }
                    is VideoRecordEvent.Status -> {
                        val duration = event.recordingStats.recordedDurationNanos / 1_000_000_000
                        _uiState.update {
                            it.copy(recordingDuration = duration)
                        }
                    }
                    is VideoRecordEvent.Pause -> {
                        _uiState.update { it.copy(isPaused = true) }
                    }
                    is VideoRecordEvent.Resume -> {
                        _uiState.update { it.copy(isPaused = false) }
                    }
                }
            }
    }

    fun stopRecording() {
        activeRecording?.stop()
        activeRecording = null
    }

    fun pauseRecording() {
        activeRecording?.pause()
    }

    fun resumeRecording() {
        activeRecording?.resume()
    }

    fun toggleCamera() {
        _uiState.update { it.copy(useFrontCamera = !it.useFrontCamera) }
    }

    fun protectCurrentRecording() {
        _uiState.update { it.copy(isProtected = true) }
        _uiState.value.currentRecording?.let { recording ->
            viewModelScope.launch {
                recordingRepository.protectRecording(recording.id, true)
            }
        }
    }

    fun deleteRecording(recToDelete: RoadPilotRecording) {
        viewModelScope.launch {
            try {
                recordingRepository.deleteRecording(recToDelete)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to delete recording") }
            }
        }
    }

    fun setPermissions(cameraGranted: Boolean, storageGranted: Boolean) {
        _uiState.update {
            it.copy(
                hasCameraPermission = cameraGranted,
                hasStoragePermission = storageGranted
            )
        }
    }

    fun setVideoQuality(quality: VideoQuality) {
        viewModelScope.launch {
            settingsRepository.setVideoQuality(quality)
        }
    }

    fun setLoopDuration(duration: LoopDuration) {
        viewModelScope.launch {
            settingsRepository.setLoopDuration(duration)
        }
    }

    fun toggleSettings() {
        _uiState.update { it.copy(showSettings = !it.showSettings) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun startDurationTimer() {
        recordingJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val duration = System.currentTimeMillis() - recordingStartTime
                _uiState.update {
                    it.copy(
                        recordingDuration = duration,
                        recordingDurationFormatted = TimeUtils.formatDuration(duration)
                    )
                }
            }
        }
    }

    private fun stopDurationTimer() {
        recordingJob?.cancel()
        recordingJob = null
    }

    private fun saveRecording(file: File) {
        viewModelScope.launch {
            val location = locationRepository.getLastKnownLocation()
            val recording = RoadPilotRecording(
                filePath = file.absolutePath,
                duration = _uiState.value.recordingDuration,
                size = file.length(),
                timestamp = recordingStartTime,
                isProtected = _uiState.value.isProtected,
                latitude = location?.latitude,
                longitude = location?.longitude
            )
            try {
                recordingRepository.saveRecording(recording)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to save recording info") }
            }
        }
    }

    private fun hasAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.RECORD_AUDIO
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
}
