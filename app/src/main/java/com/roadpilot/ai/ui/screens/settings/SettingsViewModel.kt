package com.roadpilot.ai.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roadpilot.ai.domain.model.*
import com.roadpilot.ai.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val operatingMode: OperatingMode = OperatingMode.HYBRID,
    val videoQuality: VideoQuality = VideoQuality.HIGH,
    val loopDuration: LoopDuration = LoopDuration.THREE,
    val aiProvider: AiProvider = AiProvider.GEMINI,
    val useMetricUnits: Boolean = true,
    val darkModeEnabled: Boolean = true,
    val wakePhraseEnabled: Boolean = true,
    val autoStartRecording: Boolean = false,
    val parkingModeEnabled: Boolean = false,
    val impactDetectionEnabled: Boolean = true,
    val emergencySosEnabled: Boolean = true,
    val driverFatigueAlert: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val safetyRepository: SafetyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.operatingMode.collect { mode ->
                _uiState.update { it.copy(operatingMode = mode) }
            }
        }
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
        viewModelScope.launch {
            settingsRepository.darkModeEnabled.collect { enabled ->
                _uiState.update { it.copy(darkModeEnabled = enabled) }
            }
        }
        viewModelScope.launch {
            settingsRepository.wakePhraseEnabled.collect { enabled ->
                _uiState.update { it.copy(wakePhraseEnabled = enabled) }
            }
        }
        viewModelScope.launch {
            settingsRepository.autoStartRecording.collect { enabled ->
                _uiState.update { it.copy(autoStartRecording = enabled) }
            }
        }
        viewModelScope.launch {
            settingsRepository.parkingModeEnabled.collect { enabled ->
                _uiState.update { it.copy(parkingModeEnabled = enabled) }
            }
        }
        
        viewModelScope.launch {
            safetyRepository.safetySettings.collect { settings ->
                _uiState.update {
                    it.copy(
                        impactDetectionEnabled = settings.impactDetectionEnabled,
                        emergencySosEnabled = settings.emergencySosEnabled,
                        driverFatigueAlert = settings.driverFatigueAlert
                    )
                }
            }
        }
    }

    fun setOperatingMode(mode: OperatingMode) {
        viewModelScope.launch {
            try {
                settingsRepository.setOperatingMode(mode)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update operating mode") }
            }
        }
    }

    fun setVideoQuality(quality: VideoQuality) {
        viewModelScope.launch {
            try {
                settingsRepository.setVideoQuality(quality)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update video quality") }
            }
        }
    }

    fun setLoopDuration(duration: LoopDuration) {
        viewModelScope.launch {
            try {
                settingsRepository.setLoopDuration(duration)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update loop duration") }
            }
        }
    }

    fun setAiProvider(provider: AiProvider) {
        viewModelScope.launch {
            try {
                settingsRepository.setAiProvider(provider)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update AI provider") }
            }
        }
    }

    fun setUseMetricUnits(useMetric: Boolean) {
        viewModelScope.launch {
            try {
                settingsRepository.setUseMetricUnits(useMetric)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update unit preference") }
            }
        }
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                settingsRepository.setDarkModeEnabled(enabled)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update theme setting") }
            }
        }
    }

    fun setWakePhraseEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                settingsRepository.setWakePhraseEnabled(enabled)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update wake phrase setting") }
            }
        }
    }

    fun setAutoStartRecording(enabled: Boolean) {
        viewModelScope.launch {
            try {
                settingsRepository.setAutoStartRecording(enabled)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update auto recording setting") }
            }
        }
    }

    fun setParkingModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                settingsRepository.setParkingModeEnabled(enabled)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update parking mode setting") }
            }
        }
    }

    fun setImpactDetectionEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                safetyRepository.saveSafetySettings(
                    SafetySettings(impactDetectionEnabled = enabled)
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update impact detection") }
            }
        }
    }

    fun setEmergencySosEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                safetyRepository.saveSafetySettings(
                    SafetySettings(emergencySosEnabled = enabled)
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update emergency SOS") }
            }
        }
    }

    fun setDriverFatigueAlert(enabled: Boolean) {
        viewModelScope.launch {
            try {
                safetyRepository.saveSafetySettings(
                    SafetySettings(driverFatigueAlert = enabled)
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update fatigue alert") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
