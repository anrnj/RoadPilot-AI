package com.roadpilot.ai.domain.usecase

import com.roadpilot.ai.domain.model.*
import com.roadpilot.ai.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Location?> = locationRepository.currentLocation
}

class StartLocationTrackingUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke() = locationRepository.startLocationUpdates()
}

class StopLocationTrackingUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke() = locationRepository.stopLocationUpdates()
}

class StartTripUseCase @Inject constructor(
    private val tripRepository: TripRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Long {
        val currentLocation = locationRepository.getLastKnownLocation()
        return tripRepository.startTrip(currentLocation)
    }
}

class EndTripUseCase @Inject constructor(
    private val tripRepository: TripRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(tripId: Long) {
        val currentLocation = locationRepository.getLastKnownLocation()
        tripRepository.endTrip(tripId, currentLocation)
    }
}

class GetRecordingsUseCase @Inject constructor(
    private val recordingRepository: RecordingRepository
) {
    operator fun invoke(): Flow<List<Recording>> = recordingRepository.recordings
}

class ProtectRecordingUseCase @Inject constructor(
    private val recordingRepository: RecordingRepository
) {
    suspend operator fun invoke(id: Long, protect: Boolean = true) {
        recordingRepository.protectRecording(id, protect)
    }
}

class DeleteRecordingUseCase @Inject constructor(
    private val recordingRepository: RecordingRepository
) {
    suspend operator fun invoke(recording: Recording) {
        recordingRepository.deleteRecording(recording)
    }
}

class GetAiResponseUseCase @Inject constructor(
    private val aiRepository: AiRepository
) {
    suspend operator fun invoke(
        userMessage: String,
        conversationHistory: List<AiMessage>
    ): Result<String> {
        return aiRepository.getResponse(userMessage, conversationHistory)
    }
}

class GetAiSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    val operatingMode: Flow<OperatingMode> = settingsRepository.operatingMode
    val aiProvider: Flow<AiProvider> = settingsRepository.aiProvider
}

class UpdateAiSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun setOperatingMode(mode: OperatingMode) = settingsRepository.setOperatingMode(mode)
    suspend fun setAiProvider(provider: AiProvider) = settingsRepository.setAiProvider(provider)
}

class GetVideoSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    val videoQuality: Flow<VideoQuality> = settingsRepository.videoQuality
    val loopDuration: Flow<LoopDuration> = settingsRepository.loopDuration
    val autoStartRecording: Flow<Boolean> = settingsRepository.autoStartRecording
    val parkingModeEnabled: Flow<Boolean> = settingsRepository.parkingModeEnabled
}

class UpdateVideoSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun setVideoQuality(quality: VideoQuality) = settingsRepository.setVideoQuality(quality)
    suspend fun setLoopDuration(duration: LoopDuration) = settingsRepository.setLoopDuration(duration)
    suspend fun setAutoStartRecording(enabled: Boolean) = settingsRepository.setAutoStartRecording(enabled)
    suspend fun setParkingModeEnabled(enabled: Boolean) = settingsRepository.setParkingModeEnabled(enabled)
}

class GetSafetySettingsUseCase @Inject constructor(
    private val safetyRepository: SafetyRepository
) {
    operator fun invoke(): Flow<SafetySettings> = safetyRepository.safetySettings
}

class UpdateSafetySettingsUseCase @Inject constructor(
    private val safetyRepository: SafetyRepository
) {
    suspend operator fun invoke(settings: SafetySettings) = safetyRepository.saveSafetySettings(settings)
}

class GetVehicleInfoUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    operator fun invoke(): Flow<VehicleInfo?> = vehicleRepository.vehicleInfo
}

class SaveVehicleInfoUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(info: VehicleInfo) = vehicleRepository.saveVehicleInfo(info)
}

class GetTripsUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    operator fun invoke(): Flow<List<TripInfo>> = tripRepository.trips
    fun getCurrentTrip(): Flow<TripInfo?> = tripRepository.currentTrip
}
