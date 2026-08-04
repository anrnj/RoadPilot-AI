package com.roadpilot.ai.domain.repository

import com.roadpilot.ai.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val currentLocation: Flow<Location?>
    val locationUpdates: Flow<Location>
    suspend fun startLocationUpdates()
    suspend fun stopLocationUpdates()
    suspend fun getLastKnownLocation(): Location?
}

interface RecordingRepository {
    val recordings: Flow<List<Recording>>
    suspend fun saveRecording(recording: Recording): Long
    suspend fun updateRecording(recording: Recording)
    suspend fun deleteRecording(recording: Recording)
    suspend fun getRecordingById(id: Long): Recording?
    suspend fun protectRecording(id: Long, protect: Boolean)
    fun getRecordingsDirectory(): String
}

interface TripRepository {
    val trips: Flow<List<TripInfo>>
    val currentTrip: Flow<TripInfo?>
    suspend fun startTrip(startLocation: Location?): Long
    suspend fun endTrip(tripId: Long, endLocation: Location?)
    suspend fun updateTrip(trip: TripInfo)
    suspend fun getTripById(id: Long): TripInfo?
    suspend fun deleteTrip(trip: TripInfo)
}

interface SettingsRepository {
    val operatingMode: Flow<OperatingMode>
    val videoQuality: Flow<VideoQuality>
    val loopDuration: Flow<LoopDuration>
    val aiProvider: Flow<AiProvider>
    val useMetricUnits: Flow<Boolean>
    val darkModeEnabled: Flow<Boolean>
    val wakePhraseEnabled: Flow<Boolean>
    val autoStartRecording: Flow<Boolean>
    val parkingModeEnabled: Flow<Boolean>

    suspend fun setOperatingMode(mode: OperatingMode)
    suspend fun setVideoQuality(quality: VideoQuality)
    suspend fun setLoopDuration(duration: LoopDuration)
    suspend fun setAiProvider(provider: AiProvider)
    suspend fun setUseMetricUnits(useMetric: Boolean)
    suspend fun setDarkModeEnabled(enabled: Boolean)
    suspend fun setWakePhraseEnabled(enabled: Boolean)
    suspend fun setAutoStartRecording(enabled: Boolean)
    suspend fun setParkingModeEnabled(enabled: Boolean)
}

interface VehicleRepository {
    val vehicleInfo: Flow<VehicleInfo?>
    val fuelLogs: Flow<List<FuelLog>>
    val maintenanceReminders: Flow<List<MaintenanceReminder>>

    suspend fun saveVehicleInfo(info: VehicleInfo)
    suspend fun addFuelLog(log: FuelLog): Long
    suspend fun deleteFuelLog(log: FuelLog)
    suspend fun addMaintenanceReminder(reminder: MaintenanceReminder): Long
    suspend fun completeMaintenanceReminder(id: Long)
}

interface SafetyRepository {
    val safetySettings: Flow<SafetySettings>
    val emergencyContacts: Flow<List<EmergencyContact>>

    suspend fun saveSafetySettings(settings: SafetySettings)
    suspend fun addEmergencyContact(contact: EmergencyContact): Long
    suspend fun removeEmergencyContact(contact: EmergencyContact)
    suspend fun updateEmergencyContact(contact: EmergencyContact)
}

interface AiRepository {
    suspend fun getResponse(userMessage: String, conversationHistory: List<AiMessage>): Result<String>
    suspend fun getStreamingResponse(userMessage: String, conversationHistory: List<AiMessage>): Flow<String>
    fun getCurrentProvider(): AiProvider
}
