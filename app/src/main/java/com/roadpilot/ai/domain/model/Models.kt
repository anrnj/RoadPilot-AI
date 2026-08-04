package com.roadpilot.ai.domain.model

data class Location(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double = 0.0,
    val speed: Float = 0f,
    val bearing: Float = 0f,
    val accuracy: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)

data class TripInfo(
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long? = null,
    val distance: Float = 0f,
    val averageSpeed: Float = 0f,
    val maxSpeed: Float = 0f,
    val startLocation: Location? = null,
    val endLocation: Location? = null
)

data class Recording(
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

enum class OperatingMode {
    OFFLINE,
    ONLINE,
    HYBRID
}

enum class VideoQuality(val resolution: String, val bitrate: Int) {
    HIGH("1920x1080", 10_000_000),
    MEDIUM("1280x720", 5_000_000),
    LOW("854x480", 2_500_000)
}

enum class LoopDuration(val minutes: Int) {
    ONE(1),
    THREE(3),
    FIVE(5),
    TEN(10)
}

data class AiConversation(
    val id: Long = 0,
    val messages: List<AiMessage> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

data class AiMessage(
    val role: AiRole,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class AiRole {
    USER,
    ASSISTANT,
    SYSTEM
}

enum class AiProvider {
    GEMINI,
    CHATGPT,
    OFFLINE
}

data class EmergencyContact(
    val id: Long = 0,
    val name: String,
    val phoneNumber: String,
    val isPrimary: Boolean = false
)

data class VehicleInfo(
    val licensePlate: String = "",
    val make: String = "",
    val model: String = "",
    val year: Int = 0,
    val fuelType: FuelType = FuelType.PETROL,
    val tankCapacity: Float = 0f
)

enum class FuelType {
    PETROL,
    DIESEL,
    ELECTRIC,
    HYBRID,
    LPG,
    CNG
}

data class FuelLog(
    val id: Long = 0,
    val timestamp: Long,
    val liters: Float,
    val cost: Float,
    val odometer: Float,
    val location: Location? = null
)

data class MaintenanceReminder(
    val id: Long = 0,
    val type: MaintenanceType,
    val dueDate: Long,
    val dueOdometer: Float? = null,
    val isCompleted: Boolean = false
)

enum class MaintenanceType {
    TYRE_CHANGE,
    OIL_CHANGE,
    SERVICE,
    INSPECTION,
    INSURANCE_RENEWAL
}

data class SafetySettings(
    val impactDetectionEnabled: Boolean = true,
    val impactSensitivity: Float = 0.5f,
    val emergencySosEnabled: Boolean = true,
    val emergencyContacts: List<EmergencyContact> = emptyList(),
    val autoProtectRecording: Boolean = true,
    val driverFatigueAlert: Boolean = true,
    val fatigueAlertHours: Float = 2f
)
