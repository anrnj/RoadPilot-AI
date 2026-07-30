package com.roadpilot.ai.core.camera

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.PendingRecording
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.text.SimpleDateFormat
import java.util.Locale

class CameraManager(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) {

    private var cameraProvider: ProcessCameraProvider? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    var isRecording = false
        private set

    var onRecordingStateChanged: ((Boolean) -> Unit)? = null


    private val scope = CoroutineScope(Dispatchers.Main)

    private var loopJob: Job? = null

    private var loopDurationMillis = 3 * 60 * 1000L // Default: 3 minutes

    fun setLoopDuration(minutes: Int) {
        loopDurationMillis = when (minutes) {
            1 -> 60_000L
            3 -> 180_000L
            5 -> 300_000L
            else -> 180_000L
        }
    }

    fun startCamera(previewView: PreviewView) {

        val providerFuture = ProcessCameraProvider.getInstance(context)

        providerFuture.addListener({

            cameraProvider = providerFuture.get()

            val preview = Preview.Builder().build().apply {
                surfaceProvider = previewView.surfaceProvider
            }

            val recorder = Recorder.Builder()
                .setQualitySelector(
                    QualitySelector.from(Quality.FHD)
                )
                .build()

            videoCapture = VideoCapture.withOutput(recorder)

            cameraProvider?.unbindAll()

            cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                videoCapture
            )

        }, ContextCompat.getMainExecutor(context))
    }

    @SuppressLint("MissingPermission")
    fun startRecording() {

        if (isRecording) return

        val videoCapture = videoCapture ?: return

        val fileName = SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss",
            Locale.US
        ).format(System.currentTimeMillis())

        val values = ContentValues().apply {

            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(
                    MediaStore.Video.Media.RELATIVE_PATH,
                    "Movies/RoadPilotAI"
                )
            }
        }

        val outputOptions =
            MediaStoreOutputOptions.Builder(
                context.contentResolver,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            )
                .setContentValues(values)
                .build()

        val pendingRecording: PendingRecording =
            videoCapture.output
                .prepareRecording(
                    context,
                    outputOptions
                )
                .withAudioEnabled()

        recording =
            pendingRecording.start(
                ContextCompat.getMainExecutor(context)
            ) { event ->

                when (event) {

                    is VideoRecordEvent.Start -> {

                        isRecording = true
                        onRecordingStateChanged?.invoke(true)
                    }

                    is VideoRecordEvent.Finalize -> {

                        recording = null
                        isRecording = false
                        onRecordingStateChanged?.invoke(false)
                    }
                }
            }
    }

    fun stopRecording() {

        loopJob?.cancel()
        loopJob = null

        recording?.stop()
    }
}