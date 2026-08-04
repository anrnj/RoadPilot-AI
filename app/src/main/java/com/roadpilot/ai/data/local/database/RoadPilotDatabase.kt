package com.roadpilot.ai.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        RecordingEntity::class,
        TripEntity::class,
        EmergencyContactEntity::class,
        FuelLogEntity::class,
        MaintenanceReminderEntity::class,
        VehicleInfoEntity::class,
        AiConversationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoadPilotDatabase : RoomDatabase() {
    abstract fun recordingDao(): RecordingDao
    abstract fun tripDao(): TripDao
    abstract fun emergencyContactDao(): EmergencyContactDao
    abstract fun fuelLogDao(): FuelLogDao
    abstract fun maintenanceReminderDao(): MaintenanceReminderDao
    abstract fun vehicleInfoDao(): VehicleInfoDao
    abstract fun aiConversationDao(): AiConversationDao

    companion object {
        const val DATABASE_NAME = "roadpilot_database"
    }
}
