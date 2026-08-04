package com.roadpilot.ai.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordingDao {
    @Query("SELECT * FROM recordings ORDER BY timestamp DESC")
    fun getAllRecordings(): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM recordings WHERE id = :id")
    suspend fun getRecordingById(id: Long): RecordingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecording(recording: RecordingEntity): Long

    @Update
    suspend fun updateRecording(recording: RecordingEntity)

    @Delete
    suspend fun deleteRecording(recording: RecordingEntity)

    @Query("UPDATE recordings SET isProtected = :protect WHERE id = :id")
    suspend fun protectRecording(id: Long, protect: Boolean)

    @Query("DELETE FROM recordings WHERE id IN (SELECT id FROM recordings WHERE isProtected = 0 ORDER BY timestamp ASC LIMIT :count)")
    suspend fun deleteOldUnprotectedRecordings(count: Int)
}

@Dao
interface TripDao {
    @Query("SELECT * FROM trips ORDER BY startTime DESC")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE endTime IS NULL ORDER BY startTime DESC LIMIT 1")
    fun getCurrentTrip(): Flow<TripEntity?>

    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getTripById(id: Long): TripEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity): Long

    @Update
    suspend fun updateTrip(trip: TripEntity)

    @Delete
    suspend fun deleteTrip(trip: TripEntity)

    @Query("UPDATE trips SET endTime = :endTime, endLatitude = :lat, endLongitude = :lng WHERE id = :id")
    suspend fun endTrip(id: Long, endTime: Long, lat: Double?, lng: Double?)
}

@Dao
interface EmergencyContactDao {
    @Query("SELECT * FROM emergency_contacts ORDER BY isPrimary DESC, name ASC")
    fun getAllContacts(): Flow<List<EmergencyContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: EmergencyContactEntity): Long

    @Update
    suspend fun updateContact(contact: EmergencyContactEntity)

    @Delete
    suspend fun deleteContact(contact: EmergencyContactEntity)
}

@Dao
interface FuelLogDao {
    @Query("SELECT * FROM fuel_logs ORDER BY timestamp DESC")
    fun getAllFuelLogs(): Flow<List<FuelLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFuelLog(log: FuelLogEntity): Long

    @Delete
    suspend fun deleteFuelLog(log: FuelLogEntity)
}

@Dao
interface MaintenanceReminderDao {
    @Query("SELECT * FROM maintenance_reminders WHERE isCompleted = 0 ORDER BY dueDate ASC")
    fun getActiveReminders(): Flow<List<MaintenanceReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: MaintenanceReminderEntity): Long

    @Query("UPDATE maintenance_reminders SET isCompleted = 1 WHERE id = :id")
    suspend fun completeReminder(id: Long)
}

@Dao
interface VehicleInfoDao {
    @Query("SELECT * FROM vehicle_info WHERE id = 1")
    fun getVehicleInfo(): Flow<VehicleInfoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicleInfo(info: VehicleInfoEntity)

    @Query("DELETE FROM vehicle_info")
    suspend fun clearVehicleInfo()
}

@Dao
interface AiConversationDao {
    @Query("SELECT * FROM ai_conversations ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestConversation(): AiConversationEntity?

    @Query("SELECT * FROM ai_conversations WHERE id = :id")
    suspend fun getConversationById(id: Long): AiConversationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: AiConversationEntity): Long

    @Query("DELETE FROM ai_conversations WHERE id IN (SELECT id FROM ai_conversations WHERE id != :keepId ORDER BY timestamp ASC LIMIT 1)")
    suspend fun deleteOldConversations(keepId: Long)
}
