package com.roadpilot.ai.ui.screens.drive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roadpilot.ai.domain.model.*
import com.roadpilot.ai.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DriveUiState(
    val currentLocation: Location? = null,
    val speed: Float = 0f,
    val bearing: Float = 0f,
    val compassDirection: String = "N",
    val isRecording: Boolean = false,
    val recordingDuration: String = "00:00",
    val isNavigating: Boolean = false,
    val navigationDestination: String = "",
    val eta: String = "",
    val navigationDistance: String = "",
    val operatingMode: OperatingMode = OperatingMode.HYBRID,
    val aiProvider: AiProvider = AiProvider.GEMINI,
    val isAiListening: Boolean = false,
    val isAiProcessing: Boolean = false,
    val currentTrip: TripInfo? = null,
    val weather: WeatherInfo? = null,
    val useMetricUnits: Boolean = true,
    val error: String? = null
)

data class WeatherInfo(
    val temperature: Int = 22,
    val condition: String = "Clear",
    val humidity: Int = 65
)

@HiltViewModel
class DriveViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val tripRepository: TripRepository,
    private val settingsRepository: SettingsRepository,
    private val aiRepository: AiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DriveUiState())
    val uiState: StateFlow<DriveUiState> = _uiState.asStateFlow()

    init {
        observeLocation()
        observeSettings()
        observeCurrentTrip()
    }

    private fun observeLocation() {
        viewModelScope.launch {
            locationRepository.currentLocation.collect { location ->
                location?.let {
                    _uiState.update { state ->
                        state.copy(
                            currentLocation = it,
                            speed = it.speed * 3.6f, // Convert m/s to km/h
                            bearing = it.bearing,
                            compassDirection = getCompassDirection(it.bearing)
                        )
                    }
                }
            }
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.operatingMode.collect { mode ->
                _uiState.update { it.copy(operatingMode = mode) }
            }
        }
        
        viewModelScope.launch {
            settingsRepository.aiProvider.collect { provider ->
                _uiState.update { it.copy(aiProvider = provider) }
            }
        }
        
        viewModelScope.launch {
            settingsRepository.useMetricUnits.collect { useMetric ->
                _uiState.update { it.copy(useMetricUnits = useMetric) }
            }
        }
    }

    private fun observeCurrentTrip() {
        viewModelScope.launch {
            tripRepository.currentTrip.collect { trip ->
                _uiState.update { it.copy(currentTrip = trip) }
            }
        }
    }

    fun startLocationTracking() {
        viewModelScope.launch {
            try {
                locationRepository.startLocationUpdates()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to start location tracking") }
            }
        }
    }

    fun stopLocationTracking() {
        viewModelScope.launch {
            try {
                locationRepository.stopLocationUpdates()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to stop location tracking") }
            }
        }
    }

    fun startTrip() {
        viewModelScope.launch {
            try {
                tripRepository.startTrip(_uiState.value.currentLocation)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to start trip") }
            }
        }
    }

    fun endTrip() {
        viewModelScope.launch {
            _uiState.value.currentTrip?.let { trip ->
                try {
                    tripRepository.endTrip(trip.id, _uiState.value.currentLocation)
                } catch (e: Exception) {
                    _uiState.update { it.copy(error = "Failed to end trip") }
                }
            }
        }
    }

    fun setRecordingState(isRecording: Boolean) {
        _uiState.update { it.copy(isRecording = isRecording) }
    }

    fun updateRecordingDuration(duration: String) {
        _uiState.update { it.copy(recordingDuration = duration) }
    }

    fun setAiListening(isListening: Boolean) {
        _uiState.update { it.copy(isAiListening = isListening, isAiProcessing = false) }
    }

    fun setAiProcessing(isProcessing: Boolean) {
        _uiState.update { it.copy(isAiProcessing = isProcessing, isAiListening = false) }
    }

    fun startNavigation(destination: String) {
        _uiState.update {
            it.copy(
                isNavigating = true,
                navigationDestination = destination,
                eta = "25 min",
                navigationDistance = "15.2 km"
            )
        }
    }

    fun stopNavigation() {
        _uiState.update {
            it.copy(
                isNavigating = false,
                navigationDestination = "",
                eta = "",
                navigationDistance = ""
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun getCompassDirection(bearing: Float): String {
        return when {
            bearing >= 337.5 || bearing < 22.5 -> "N"
            bearing >= 22.5 && bearing < 67.5 -> "NE"
            bearing >= 67.5 && bearing < 112.5 -> "E"
            bearing >= 112.5 && bearing < 157.5 -> "SE"
            bearing >= 157.5 && bearing < 202.5 -> "S"
            bearing >= 202.5 && bearing < 247.5 -> "SW"
            bearing >= 247.5 && bearing < 292.5 -> "W"
            bearing >= 292.5 && bearing < 337.5 -> "NW"
            else -> "N"
        }
    }
}
