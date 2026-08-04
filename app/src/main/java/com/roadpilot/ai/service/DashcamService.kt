package com.roadpilot.ai.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.roadpilot.ai.R
import com.roadpilot.ai.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashcamService : Service() {

    private val binder = DashcamBinder()
    
    var isRecording = false
        private set
    
    var isPaused = false
        private set
    
    var recordingDuration = 0L
        private set

    inner class DashcamBinder : Binder() {
        fun getService(): DashcamService = this@DashcamService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_RECORDING -> startRecording()
            ACTION_STOP_RECORDING -> stopRecording()
            ACTION_PAUSE_RECORDING -> pauseRecording()
            ACTION_RESUME_RECORDING -> resumeRecording()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun startRecording() {
        if (isRecording) return
        
        isRecording = true
        isPaused = false
        recordingDuration = 0L
        
        startForeground(NOTIFICATION_ID, createNotification())
        
        // Notify UI
        sendBroadcast(Intent(ACTION_RECORDING_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_RECORDING, true)
            putExtra(EXTRA_IS_PAUSED, false)
        })
    }

    fun stopRecording() {
        if (!isRecording) return
        
        isRecording = false
        isPaused = false
        recordingDuration = 0L
        
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        
        // Notify UI
        sendBroadcast(Intent(ACTION_RECORDING_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_RECORDING, false)
            putExtra(EXTRA_IS_PAUSED, false)
        })
    }

    fun pauseRecording() {
        if (!isRecording || isPaused) return
        
        isPaused = true
        updateNotification()
        
        sendBroadcast(Intent(ACTION_RECORDING_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_RECORDING, true)
            putExtra(EXTRA_IS_PAUSED, true)
        })
    }

    fun resumeRecording() {
        if (!isRecording || !isPaused) return
        
        isPaused = false
        updateNotification()
        
        sendBroadcast(Intent(ACTION_RECORDING_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_RECORDING, true)
            putExtra(EXTRA_IS_PAUSED, false)
        })
    }

    fun protectRecording() {
        // Send protected recording broadcast
        sendBroadcast(Intent(ACTION_PROTECT_RECORDING))
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Dashcam Recording",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows when dashcam is recording"
                setShowBadge(true)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = PendingIntent.getService(
            this,
            1,
            Intent(this, DashcamService::class.java).apply { action = ACTION_STOP_RECORDING },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pauseIntent = PendingIntent.getService(
            this,
            2,
            Intent(this, DashcamService::class.java).apply {
                action = if (isPaused) ACTION_RESUME_RECORDING else ACTION_PAUSE_RECORDING
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("RoadPilot Dashcam")
            .setContentText(
                if (isPaused) "Recording paused" 
                else "Recording: ${formatDuration(recordingDuration)}"
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .addAction(
                R.drawable.ic_launcher_foreground,
                if (isPaused) "Resume" else "Pause",
                pauseIntent
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Stop",
                stopIntent
            )
            .build()
    }

    private fun formatDuration(durationMs: Long): String {
        val seconds = (durationMs / 1000) % 60
        val minutes = (durationMs / (1000 * 60)) % 60
        val hours = durationMs / (1000 * 60 * 60)
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }

    companion object {
        const val CHANNEL_ID = "dashcam_service_channel"
        const val NOTIFICATION_ID = 1002
        
        const val ACTION_START_RECORDING = "com.roadpilot.ai.START_RECORDING"
        const val ACTION_STOP_RECORDING = "com.roadpilot.ai.STOP_RECORDING"
        const val ACTION_PAUSE_RECORDING = "com.roadpilot.ai.PAUSE_RECORDING"
        const val ACTION_RESUME_RECORDING = "com.roadpilot.ai.RESUME_RECORDING"
        const val ACTION_PROTECT_RECORDING = "com.roadpilot.ai.PROTECT_RECORDING"
        
        const val ACTION_RECORDING_STATE_CHANGED = "com.roadpilot.ai.RECORDING_STATE_CHANGED"
        const val EXTRA_IS_RECORDING = "is_recording"
        const val EXTRA_IS_PAUSED = "is_paused"
        const val EXTRA_DURATION = "duration"
    }
}
