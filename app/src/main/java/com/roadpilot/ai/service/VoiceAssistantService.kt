package com.roadpilot.ai.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.core.app.NotificationCompat
import com.roadpilot.ai.R
import com.roadpilot.ai.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class VoiceAssistantService : Service() {

    private var speechRecognizer: SpeechRecognizer? = null
    private var textToSpeech: TextToSpeech? = null
    private var ttsInitialized = false

    var isListening = false
        private set

    var isSpeaking = false
        private set

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initializeTts()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_LISTENING -> startListening()
            ACTION_STOP_LISTENING -> stopListening()
            ACTION_SPEAK -> {
                val text = intent.getStringExtra(EXTRA_SPEAK_TEXT) ?: ""
                speak(text)
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
        textToSpeech?.shutdown()
    }

    private fun initializeTts() {
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                ttsInitialized = true
            }
        }
    }

    fun initSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: android.os.Bundle?) {
                    isListening = true
                    updateNotification()
                    broadcastState()
                }

                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {
                    isListening = false
                    updateNotification()
                    broadcastState()
                }

                override fun onError(error: Int) {
                    isListening = false
                    updateNotification()
                    broadcastState()
                    
                    // Try to restart listening after error
                    if (error != SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS &&
                        error != SpeechRecognizer.ERROR_NO_MATCH) {
                        android.os.Handler(mainLooper).postDelayed({
                            startListening()
                        }, 1000)
                    }
                }

                override fun onResults(results: android.os.Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    matches?.firstOrNull()?.let { text ->
                        processVoiceInput(text)
                    }
                }

                override fun onPartialResults(partialResults: android.os.Bundle?) {}
                override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
            })
        }
    }

    fun startListening() {
        if (speechRecognizer == null) {
            initSpeechRecognizer()
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        try {
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        isListening = false
        updateNotification()
        broadcastState()
    }

    private fun processVoiceInput(text: String) {
        // Check for wake phrase
        if (text.lowercase().contains("hey roadpilot") || text.lowercase().contains("roadpilot")) {
            // Broadcast command to be processed
            val command = text
                .replace(Regex("(?i)hey roadpilot\\s*"), "")
                .replace(Regex("(?i)roadpilot\\s*"), "")
                .trim()
            
            sendBroadcast(Intent(ACTION_VOICE_COMMAND).apply {
                putExtra(EXTRA_COMMAND, command)
            })
        }
    }

    fun speak(text: String) {
        if (ttsInitialized) {
            isSpeaking = true
            updateNotification()
            broadcastState()
            
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "roadpilot_utterance")
            
            // Set listener for completion
            textToSpeech?.setOnUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    isSpeaking = false
                    updateNotification()
                    broadcastState()
                }
                override fun onError(utteranceId: String?) {
                    isSpeaking = false
                    updateNotification()
                    broadcastState()
                }
            })
        }
    }

    fun stopSpeaking() {
        textToSpeech?.stop()
        isSpeaking = false
        updateNotification()
        broadcastState()
    }

    private fun broadcastState() {
        sendBroadcast(Intent(ACTION_ASSISTANT_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_LISTENING, isListening)
            putExtra(EXTRA_IS_SPEAKING, isSpeaking)
        })
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Voice Assistant",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "RoadPilot AI voice assistant"
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

        val contentText = when {
            isSpeaking -> "Speaking..."
            isListening -> "Listening for commands..."
            else -> "Ready"
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("RoadPilot AI")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    companion object {
        const val CHANNEL_ID = "voice_assistant_channel"
        const val NOTIFICATION_ID = 1003
        
        const val ACTION_START_LISTENING = "com.roadpilot.ai.START_LISTENING"
        const val ACTION_STOP_LISTENING = "com.roadpilot.ai.STOP_LISTENING"
        const val ACTION_SPEAK = "com.roadpilot.ai.SPEAK"
        const val ACTION_VOICE_COMMAND = "com.roadpilot.ai.VOICE_COMMAND"
        const val ACTION_ASSISTANT_STATE_CHANGED = "com.roadpilot.ai.ASSISTANT_STATE_CHANGED"
        
        const val EXTRA_SPEAK_TEXT = "speak_text"
        const val EXTRA_COMMAND = "command"
        const val EXTRA_IS_LISTENING = "is_listening"
        const val EXTRA_IS_SPEAKING = "is_speaking"
    }
}
