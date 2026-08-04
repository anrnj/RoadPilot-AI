package com.roadpilot.ai.data.local.database

import androidx.room.*

@Entity(tableName = "recordings")
data class RecordingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val filePath: String,
    val duration: Long,
    val size: Long,
    val timestamp: Long,
    val isProtected: Boolean = false,
    val isAccident: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val thumbnailPath: String? = null
)

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long? = null,
    val distance: Float = 0f,
    val averageSpeed: Float = 0f,
    val maxSpeed: Float = 0f,
    val startLatitude: Double? = null,
    val startLongitude: Double? = null,
    val endLatitude: Double? = null,
    val endLongitude: Double? = null
)

@Entity(tableName = "emergency_contacts")
data class EmergencyContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val phoneNumber: String,
    val isPrimary: Boolean = false
)

@Entity(tableName = "fuel_logs")
data class FuelLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val liters: Float,
    val cost: Float,
    val odometer: Float,
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Entity(tableName = "maintenance_reminders")
data class MaintenanceReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
    val dueDate: Long,
    val dueOdometer: Float? = null,
    val isCompleted: Boolean = false
)

@Entity(tableName = "vehicle_info")
data class VehicleInfoEntity(
    @PrimaryKey
    val id: Int = 1,
    val licensePlate: String = "",
    val make: String = "",
    val model: String = "",
    val year: Int = 0,
    val fuelType: String = "PETROL",
    val tankCapacity: Float = 0f
)

@Entity(tableName = "ai_conversations")
data class AiConversationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val messagesJson: String,
    val timestamp: Long = System.currentTimeMillis()
)
