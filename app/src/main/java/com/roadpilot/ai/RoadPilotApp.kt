package com.roadpilot.ai

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoadPilotApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // Recording Channel
            val recordingChannel = NotificationChannel(
                CHANNEL_RECORDING,
                getString(R.string.notification_channel_recording),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Dashcam recording notifications"
                setShowBadge(false)
            }

            // Location Channel
            val locationChannel = NotificationChannel(
                CHANNEL_LOCATION,
                getString(R.string.notification_channel_location),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Location tracking notifications"
                setShowBadge(false)
            }

            // AI Assistant Channel
            val aiChannel = NotificationChannel(
                CHANNEL_AI,
                getString(R.string.notification_channel_ai),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "AI Assistant notifications"
                setShowBadge(false)
            }

            // Safety Alerts Channel
            val safetyChannel = NotificationChannel(
                CHANNEL_SAFETY,
                getString(R.string.notification_channel_safety),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Safety and emergency alerts"
                enableVibration(true)
                setShowBadge(true)
            }

            notificationManager.createNotificationChannels(
                listOf(recordingChannel, locationChannel, aiChannel, safetyChannel)
            )
        }
    }

    companion object {
        const val CHANNEL_RECORDING = "recording_channel"
        const val CHANNEL_LOCATION = "location_channel"
        const val CHANNEL_AI = "ai_channel"
        const val CHANNEL_SAFETY = "safety_channel"
    }
}
