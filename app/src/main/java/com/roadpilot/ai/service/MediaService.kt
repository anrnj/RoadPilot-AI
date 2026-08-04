package com.roadpilot.ai.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.roadpilot.ai.R
import com.roadpilot.ai.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaService : android.app.Service() {

    private val binder = MediaBinder()
    private val audioManager by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    var isPlaying = false
        private set
    
    var currentTrack = ""
        private set
    
    var artist = ""
        private set

    inner class MediaBinder : Binder() {
        fun getService(): MediaService = this@MediaService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
            ACTION_NEXT -> next()
            ACTION_PREVIOUS -> previous()
            ACTION_VOLUME_UP -> volumeUp()
            ACTION_VOLUME_DOWN -> volumeDown()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun play() {
        isPlaying = true
        sendBroadcast(Intent(ACTION_MEDIA_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_PLAYING, true)
        })
        startForeground(NOTIFICATION_ID, createNotification())
    }

    fun pause() {
        isPlaying = false
        sendBroadcast(Intent(ACTION_MEDIA_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_PLAYING, false)
        })
        updateNotification()
    }

    fun next() {
        sendBroadcast(Intent(ACTION_MEDIA_COMMAND).apply {
            putExtra(EXTRA_COMMAND, "next")
        })
    }

    fun previous() {
        sendBroadcast(Intent(ACTION_MEDIA_COMMAND).apply {
            putExtra(EXTRA_COMMAND, "previous")
        })
    }

    fun volumeUp() {
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
    }

    fun volumeDown() {
        audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
    }

    fun setTrack(track: String, artist: String) {
        currentTrack = track
        this.artist = artist
        updateNotification()
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Media Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Media playback controls"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    private fun createNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val playPauseIntent = PendingIntent.getService(
            this,
            1,
            Intent(this, MediaService::class.java).apply {
                action = if (isPlaying) ACTION_PAUSE else ACTION_PLAY
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val previousIntent = PendingIntent.getService(
            this,
            2,
            Intent(this, MediaService::class.java).apply { action = ACTION_PREVIOUS },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val nextIntent = PendingIntent.getService(
            this,
            3,
            Intent(this, MediaService::class.java).apply { action = ACTION_NEXT },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(if (currentTrack.isNotEmpty()) currentTrack else "RoadPilot Media")
            .setContentText(if (artist.isNotEmpty()) artist else "Ready to play")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(isPlaying)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_TRANSPORT)
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Previous",
                previousIntent
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                if (isPlaying) "Pause" else "Play",
                playPauseIntent
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Next",
                nextIntent
            )
            .build()
    }

    companion object {
        const val CHANNEL_ID = "media_service_channel"
        const val NOTIFICATION_ID = 1004
        
        const val ACTION_PLAY = "com.roadpilot.ai.MEDIA_PLAY"
        const val ACTION_PAUSE = "com.roadpilot.ai.MEDIA_PAUSE"
        const val ACTION_NEXT = "com.roadpilot.ai.MEDIA_NEXT"
        const val ACTION_PREVIOUS = "com.roadpilot.ai.MEDIA_PREVIOUS"
        const val ACTION_VOLUME_UP = "com.roadpilot.ai.VOLUME_UP"
        const val ACTION_VOLUME_DOWN = "com.roadpilot.ai.VOLUME_DOWN"
        
        const val ACTION_MEDIA_STATE_CHANGED = "com.roadpilot.ai.MEDIA_STATE_CHANGED"
        const val ACTION_MEDIA_COMMAND = "com.roadpilot.ai.MEDIA_COMMAND"
        
        const val EXTRA_IS_PLAYING = "is_playing"
        const val EXTRA_COMMAND = "command"
    }
}
