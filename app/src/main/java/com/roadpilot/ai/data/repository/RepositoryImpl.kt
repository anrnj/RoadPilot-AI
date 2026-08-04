package com.roadpilot.ai.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.os.Looper
import com.google.android.gms.location.*
import com.roadpilot.ai.data.local.database.*
import com.roadpilot.ai.data.local.datastore.PreferencesDataStore
import com.roadpilot.ai.domain.model.*
import com.roadpilot.ai.domain.repository.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    override val currentLocation: Flow<Location?> = _currentLocation.asStateFlow()

    private val _locationUpdates = MutableSharedFlow<Location>(replay = 1)

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        1000L
    ).apply {
        setMinUpdateIntervalMillis(500L)
        setWaitForAccurateLocation(false)
    }.build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { androidLocation ->
                val location = Location(
                    latitude = androidLocation.latitude,
                    longitude = androidLocation.longitude,
                    altitude = androidLocation.altitude,
                    speed = androidLocation.speed,
                    bearing = androidLocation.bearing,
                    accuracy = androidLocation.accuracy,
                    timestamp = androidLocation.time
                )
                _currentLocation.value = location
                _locationUpdates.tryEmit(location)
            }
        }
    }

    override val locationUpdates: Flow<Location> = _locationUpdates.asSharedFlow()

    @SuppressLint("MissingPermission")
    override suspend fun startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): Location? {
        return try {
            val androidLocation = fusedLocationClient.lastLocation.await()
            androidLocation?.let {
                Location(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    altitude = it.altitude,
                    speed = it.speed,
                    bearing = it.bearing,
                    accuracy = it.accuracy,
                    timestamp = it.time
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}

@Singleton
class RecordingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recordingDao: RecordingDao
) : RecordingRepository {

    override val recordings: Flow<List<Recording>> = recordingDao.getAllRecordings()
        .map { entities -> entities.map { it.toDomain() } }

    override suspend fun saveRecording(recording: Recording): Long {
        return recordingDao.insertRecording(recording.toEntity())
    }

    override suspend fun updateRecording(recording: Recording) {
        recordingDao.updateRecording(recording.toEntity())
    }

    override suspend fun deleteRecording(recording: Recording) {
        // Delete the actual file
        try {
            File(recording.filePath).delete()
            recording.thumbnailPath?.let { File(it).delete() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        recordingDao.deleteRecording(recording.toEntity())
    }

    override suspend fun getRecordingById(id: Long): Recording? {
        return recordingDao.getRecordingById(id)?.toDomain()
    }

    override suspend fun protectRecording(id: Long, protect: Boolean) {
        recordingDao.protectRecording(id, protect)
    }

    override fun getRecordingsDirectory(): String {
        val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), "RoadPilot")
        if (!dir.exists()) dir.mkdirs()
        return dir.absolutePath
    }

    private fun RecordingEntity.toDomain() = Recording(
        id = id,
        filePath = filePath,
        duration = duration,
        size = size,
        timestamp = timestamp,
        isProtected = isProtected,
        isAccident = isAccident,
        latitude = latitude,
        longitude = longitude,
        thumbnailPath = thumbnailPath
    )

    private fun Recording.toEntity() = RecordingEntity(
        id = id,
        filePath = filePath,
        duration = duration,
        size = size,
        timestamp = timestamp,
        isProtected = isProtected,
        isAccident = isAccident,
        latitude = latitude,
        longitude = longitude,
        thumbnailPath = thumbnailPath
    )
}

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao
) : TripRepository {

    override val trips: Flow<List<TripInfo>> = tripDao.getAllTrips()
        .map { entities -> entities.map { it.toDomain() } }

    override val currentTrip: Flow<TripInfo?> = tripDao.getCurrentTrip()
        .map { it?.toDomain() }

    override suspend fun startTrip(startLocation: Location?): Long {
        val entity = TripEntity(
            startTime = System.currentTimeMillis(),
            startLatitude = startLocation?.latitude,
            startLongitude = startLocation?.longitude
        )
        return tripDao.insertTrip(entity)
    }

    override suspend fun endTrip(tripId: Long, endLocation: Location?) {
        tripDao.endTrip(
            id = tripId,
            endTime = System.currentTimeMillis(),
            lat = endLocation?.latitude,
            lng = endLocation?.longitude
        )
    }

    override suspend fun updateTrip(trip: TripInfo) {
        tripDao.updateTrip(trip.toEntity())
    }

    override suspend fun getTripById(id: Long): TripInfo? {
        return tripDao.getTripById(id)?.toDomain()
    }

    override suspend fun deleteTrip(trip: TripInfo) {
        tripDao.deleteTrip(trip.toEntity())
    }

    private fun TripEntity.toDomain() = TripInfo(
        id = id,
        startTime = startTime,
        endTime = endTime,
        distance = distance,
        averageSpeed = averageSpeed,
        maxSpeed = maxSpeed,
        startLocation = if (startLatitude != null && startLongitude != null) {
            Location(startLatitude, startLongitude)
        } else null,
        endLocation = if (endLatitude != null && endLongitude != null) {
            Location(endLatitude, endLongitude)
        } else null
    )

    private fun TripInfo.toEntity() = TripEntity(
        id = id,
        startTime = startTime,
        endTime = endTime,
        distance = distance,
        averageSpeed = averageSpeed,
        maxSpeed = maxSpeed,
        startLatitude = startLocation?.latitude,
        startLongitude = startLocation?.longitude,
        endLatitude = endLocation?.latitude,
        endLongitude = endLocation?.longitude
    )
}

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : SettingsRepository {

    override val operatingMode: Flow<OperatingMode> = preferencesDataStore.operatingMode
    override val videoQuality: Flow<VideoQuality> = preferencesDataStore.videoQuality
    override val loopDuration: Flow<LoopDuration> = preferencesDataStore.loopDuration
    override val aiProvider: Flow<AiProvider> = preferencesDataStore.aiProvider
    override val useMetricUnits: Flow<Boolean> = preferencesDataStore.useMetricUnits
    override val darkModeEnabled: Flow<Boolean> = preferencesDataStore.darkModeEnabled
    override val wakePhraseEnabled: Flow<Boolean> = preferencesDataStore.wakePhraseEnabled
    override val autoStartRecording: Flow<Boolean> = preferencesDataStore.autoStartRecording
    override val parkingModeEnabled: Flow<Boolean> = preferencesDataStore.parkingModeEnabled

    override suspend fun setOperatingMode(mode: OperatingMode) = preferencesDataStore.setOperatingMode(mode)
    override suspend fun setVideoQuality(quality: VideoQuality) = preferencesDataStore.setVideoQuality(quality)
    override suspend fun setLoopDuration(duration: LoopDuration) = preferencesDataStore.setLoopDuration(duration)
    override suspend fun setAiProvider(provider: AiProvider) = preferencesDataStore.setAiProvider(provider)
    override suspend fun setUseMetricUnits(useMetric: Boolean) = preferencesDataStore.setUseMetricUnits(useMetric)
    override suspend fun setDarkModeEnabled(enabled: Boolean) = preferencesDataStore.setDarkModeEnabled(enabled)
    override suspend fun setWakePhraseEnabled(enabled: Boolean) = preferencesDataStore.setWakePhraseEnabled(enabled)
    override suspend fun setAutoStartRecording(enabled: Boolean) = preferencesDataStore.setAutoStartRecording(enabled)
    override suspend fun setParkingModeEnabled(enabled: Boolean) = preferencesDataStore.setParkingModeEnabled(enabled)
}

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val vehicleInfoDao: VehicleInfoDao,
    private val fuelLogDao: FuelLogDao,
    private val maintenanceReminderDao: MaintenanceReminderDao
) : VehicleRepository {

    override val vehicleInfo: Flow<VehicleInfo?> = vehicleInfoDao.getVehicleInfo()
        .map { it?.toDomain() }

    override val fuelLogs: Flow<List<FuelLog>> = fuelLogDao.getAllFuelLogs()
        .map { entities -> entities.map { it.toDomain() } }

    override val maintenanceReminders: Flow<List<MaintenanceReminder>> = maintenanceReminderDao.getActiveReminders()
        .map { entities -> entities.map { it.toDomain() } }

    override suspend fun saveVehicleInfo(info: VehicleInfo) {
        vehicleInfoDao.insertVehicleInfo(info.toEntity())
    }

    override suspend fun addFuelLog(log: FuelLog): Long {
        return fuelLogDao.insertFuelLog(log.toEntity())
    }

    override suspend fun deleteFuelLog(log: FuelLog) {
        fuelLogDao.deleteFuelLog(log.toEntity())
    }

    override suspend fun addMaintenanceReminder(reminder: MaintenanceReminder): Long {
        return maintenanceReminderDao.insertReminder(reminder.toEntity())
    }

    override suspend fun completeMaintenanceReminder(id: Long) {
        maintenanceReminderDao.completeReminder(id)
    }

    private fun VehicleInfoEntity.toDomain() = VehicleInfo(
        licensePlate = licensePlate,
        make = make,
        model = model,
        year = year,
        fuelType = FuelType.valueOf(fuelType),
        tankCapacity = tankCapacity
    )

    private fun VehicleInfo.toEntity() = VehicleInfoEntity(
        licensePlate = licensePlate,
        make = make,
        model = model,
        year = year,
        fuelType = fuelType.name,
        tankCapacity = tankCapacity
    )

    private fun FuelLogEntity.toDomain() = FuelLog(
        id = id,
        timestamp = timestamp,
        liters = liters,
        cost = cost,
        odometer = odometer,
        location = if (latitude != null && longitude != null) Location(latitude, longitude) else null
    )

    private fun FuelLog.toEntity() = FuelLogEntity(
        id = id,
        timestamp = timestamp,
        liters = liters,
        cost = cost,
        odometer = odometer,
        latitude = location?.latitude,
        longitude = location?.longitude
    )

    private fun MaintenanceReminderEntity.toDomain() = MaintenanceReminder(
        id = id,
        type = MaintenanceType.valueOf(type),
        dueDate = dueDate,
        dueOdometer = dueOdometer,
        isCompleted = isCompleted
    )

    private fun MaintenanceReminder.toEntity() = MaintenanceReminderEntity(
        id = id,
        type = type.name,
        dueDate = dueDate,
        dueOdometer = dueOdometer,
        isCompleted = isCompleted
    )
}

@Singleton
class SafetyRepositoryImpl @Inject constructor(
    private val emergencyContactDao: EmergencyContactDao,
    private val preferencesDataStore: PreferencesDataStore
) : SafetyRepository {

    override val safetySettings: Flow<SafetySettings> = combine(
        preferencesDataStore.impactDetectionEnabled,
        preferencesDataStore.impactSensitivity,
        preferencesDataStore.emergencySosEnabled,
        preferencesDataStore.driverFatigueAlert,
        preferencesDataStore.fatigueAlertHours,
        emergencyContactDao.getAllContacts()
    ) { values ->
        SafetySettings(
            impactDetectionEnabled = values[0] as Boolean,
            impactSensitivity = values[1] as Float,
            emergencySosEnabled = values[2] as Boolean,
            driverFatigueAlert = values[3] as Boolean,
            fatigueAlertHours = values[4] as Float,
            emergencyContacts = @Suppress("UNCHECKED_CAST") (values[5] as List<EmergencyContactEntity>)
                .map { it.toDomain() }
        )
    }

    override val emergencyContacts: Flow<List<EmergencyContact>> = emergencyContactDao.getAllContacts()
        .map { entities -> entities.map { it.toDomain() } }

    override suspend fun saveSafetySettings(settings: SafetySettings) {
        preferencesDataStore.setImpactDetectionEnabled(settings.impactDetectionEnabled)
        preferencesDataStore.setImpactSensitivity(settings.impactSensitivity)
        preferencesDataStore.setEmergencySosEnabled(settings.emergencySosEnabled)
        preferencesDataStore.setDriverFatigueAlert(settings.driverFatigueAlert)
        preferencesDataStore.setFatigueAlertHours(settings.fatigueAlertHours)
    }

    override suspend fun addEmergencyContact(contact: EmergencyContact): Long {
        return emergencyContactDao.insertContact(contact.toEntity())
    }

    override suspend fun removeEmergencyContact(contact: EmergencyContact) {
        emergencyContactDao.deleteContact(contact.toEntity())
    }

    override suspend fun updateEmergencyContact(contact: EmergencyContact) {
        emergencyContactDao.updateContact(contact.toEntity())
    }

    private fun EmergencyContactEntity.toDomain() = EmergencyContact(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        isPrimary = isPrimary
    )

    private fun EmergencyContact.toEntity() = EmergencyContactEntity(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        isPrimary = isPrimary
    )
}
