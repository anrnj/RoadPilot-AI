package com.roadpilot.ai.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.content.getSystemService
import com.roadpilot.ai.data.local.database.EmergencyContactDao
import com.roadpilot.ai.data.local.database.RoadPilotDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sqrt

@AndroidEntryPoint
class ImpactDetectionReceiver : BroadcastReceiver(), SensorEventListener {

    @Inject
    lateinit var database: RoadPilotDatabase

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var lastImpactTime = 0L
    
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var lastUpdate = 0L

    private val impactThreshold = 25f // Adjust based on testing
    private val impactCooldown = 5000L // 5 seconds between impact detections

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_START_TRACKING -> startTracking(context)
            ACTION_STOP_TRACKING -> stopTracking(context)
        }
    }

    fun startTracking(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        
        accelerometer?.let {
            sensorManager?.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    fun stopTracking(context: Context) {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                detectImpact(it)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }

    private fun detectImpact(event: SensorEvent) {
        val currentTime = System.currentTimeMillis()
        
        if (currentTime - lastUpdate > 100) {
            val diffTime = currentTime - lastUpdate
            lastUpdate = currentTime
            
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            val speed = sqrt(
                (x - lastX) * (x - lastX) +
                (y - lastY) * (y - lastY) +
                (z - lastZ) * (z - lastZ)
            ) / diffTime * 10000
            
            lastX = x
            lastY = y
            lastZ = z
            
            if (speed > impactThreshold && currentTime - lastImpactTime > impactCooldown) {
                lastImpactTime = currentTime
                onImpactDetected()
            }
        }
    }

    private fun onImpactDetected() {
        Log.d(TAG, "Impact detected!")
        
        // Vibrate
        vibrate()
        
        // Protect current recording
        // This would be handled by the service
        
        // Notify through broadcast
        android.content.Intent(ACTION_IMPACT_DETECTED).apply {
            setPackage("com.roadpilot.ai")
            addFlags(android.content.Intent.FLAG_RECEIVER_REPLACE_PENDING)
        }
    }

    private fun vibrate() {
        // This is a simplified vibration - in production you'd use the context
        try {
            val vibratorManager = android.app.Application().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            val vibrator = vibratorManager?.defaultVibrator
            
            vibrator?.vibrate(
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to vibrate", e)
        }
    }

    private fun sendEmergencyAlerts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val contacts = database.emergencyContactDao().getAllContacts()
                contacts.collect { contactList ->
                    val primaryContact = contactList.firstOrNull { it.isPrimary } ?: contactList.firstOrNull()
                    
                    primaryContact?.let { contact ->
                        sendSosMessage(contact.phoneNumber)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to send emergency alerts", e)
            }
        }
    }

    private fun sendSosMessage(phoneNumber: String) {
        try {
            val smsManager = android.app.Application().getSystemService(SmsManager::class.java)
            val message = "Emergency: I may have been in an accident. RoadPilot AI detected a potential impact."
            smsManager?.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send SOS", e)
        }
    }

    companion object {
        private const val TAG = "ImpactDetection"
        
        const val ACTION_START_TRACKING = "com.roadpilot.ai.START_IMPACT_TRACKING"
        const val ACTION_STOP_TRACKING = "com.roadpilot.ai.STOP_IMPACT_TRACKING"
        const val ACTION_IMPACT_DETECTED = "com.roadpilot.ai.IMPACT_DETECTED"
    }
}
