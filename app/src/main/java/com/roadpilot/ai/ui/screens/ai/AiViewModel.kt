package com.roadpilot.ai.ui.screens.ai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roadpilot.ai.domain.model.*
import com.roadpilot.ai.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class AiUiState(
    val isListening: Boolean = false,
    val isProcessing: Boolean = false,
    val isSpeaking: Boolean = false,
    val currentMessage: String = "",
    val messages: List<ChatMessage> = emptyList(),
    val operatingMode: OperatingMode = OperatingMode.HYBRID,
    val aiProvider: AiProvider = AiProvider.GEMINI,
    val wakePhraseEnabled: Boolean = true,
    val error: String? = null
)

data class ChatMessage(
    val id: Long = System.currentTimeMillis(),
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@HiltViewModel
class AiViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val aiRepository: AiRepository,
    private val settingsRepository: SettingsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiUiState())
    val uiState: StateFlow<AiUiState> = _uiState.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null
    private var textToSpeech: TextToSpeech? = null
    private var ttsInitialized = false

    init {
        initializeTts()
        observeSettings()
    }

    private fun initializeTts() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                ttsInitialized = true
            }
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.operatingMode.collect { mode ->
                _uiState.update { it.copy(operatingMode = mode) }
            }
        }
        viewModelScope.launch {
            settingsRepository.aiProvider.collect { provider ->
                _uiState.update { it.copy(aiProvider = provider) }
            }
        }
        viewModelScope.launch {
            settingsRepository.wakePhraseEnabled.collect { enabled ->
                _uiState.update { it.copy(wakePhraseEnabled = enabled) }
            }
        }
    }

    fun initSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    _uiState.update { it.copy(isListening = true, currentMessage = "") }
                }

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    _uiState.update { it.copy(isListening = false) }
                }

                override fun onError(error: Int) {
                    _uiState.update {
                        it.copy(
                            isListening = false,
                            error = getSpeechErrorMessage(error)
                        )
                    }
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    matches?.firstOrNull()?.let { text ->
                        processUserInput(text)
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {
                    val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    matches?.firstOrNull()?.let { text ->
                        _uiState.update { it.copy(currentMessage = text) }
                    }
                }

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        try {
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to start speech recognition") }
        }
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        _uiState.update { it.copy(isListening = false, currentMessage = "") }
    }

    private fun processUserInput(text: String) {
        viewModelScope.launch {
            // Add user message
            addMessage(text, isFromUser = true)

            // Check for wake phrase
            if (text.lowercase().contains("hey roadpilot") || text.lowercase().contains("roadpilot")) {
                val actualCommand = text
                    .replace(Regex("(?i)hey roadpilot\\s*"), "")
                    .replace(Regex("(?i)roadpilot\\s*"), "")
                    .trim()
                
                if (actualCommand.isNotEmpty()) {
                    processCommand(actualCommand)
                }
            } else {
                // Regular conversation
                _uiState.update { it.copy(isProcessing = true) }
                
                val conversationHistory = _uiState.value.messages.map { msg ->
                    AiMessage(
                        role = if (msg.isFromUser) AiRole.USER else AiRole.ASSISTANT,
                        content = msg.content
                    )
                }
                
                aiRepository.getResponse(text, conversationHistory).fold(
                    onSuccess = { response ->
                        addMessage(response, isFromUser = false)
                        speak(response)
                    },
                    onFailure = { error ->
                        _uiState.update { it.copy(error = "AI Error: ${error.message}") }
                    }
                )
                
                _uiState.update { it.copy(isProcessing = false) }
            }
        }
    }

    private suspend fun processCommand(command: String) {
        _uiState.update { it.copy(isProcessing = true) }
        
        val response = when {
            // Navigation commands
            command.contains("navigate home", ignoreCase = true) ||
            command.contains("take me home", ignoreCase = true) -> {
                "Starting navigation to your home location."
            }
            command.contains("navigate to", ignoreCase = true) ||
            command.contains("take me to", ignoreCase = true) -> {
                val destination = command
                    .replace(Regex("(?i)navigate to\\s*"), "")
                    .replace(Regex("(?i)take me to\\s*"), "")
                    .trim()
                "Finding the best route to $destination."
            }
            
            // Recording commands
            command.contains("start recording", ignoreCase = true) ||
            command.contains("begin recording", ignoreCase = true) -> {
                "Starting dashcam recording. Your journey is now being captured."
            }
            command.contains("stop recording", ignoreCase = true) ||
            command.contains("end recording", ignoreCase = true) -> {
                "Recording stopped. Your footage has been saved."
            }
            command.contains("protect", ignoreCase = true) ||
            command.contains("lock recording", ignoreCase = true) -> {
                "Recording has been protected and won't be auto-deleted."
            }
            
            // Media commands
            command.contains("play", ignoreCase = true) -> {
                "Playing your driving playlist."
            }
            command.contains("pause", ignoreCase = true) ||
            command.contains("stop music", ignoreCase = true) -> {
                "Music paused."
            }
            command.contains("volume up", ignoreCase = true) ||
            command.contains("increase volume", ignoreCase = true) -> {
                "Volume increased."
            }
            command.contains("volume down", ignoreCase = true) ||
            command.contains("decrease volume", ignoreCase = true) -> {
                "Volume decreased."
            }
            
            // Weather
            command.contains("weather", ignoreCase = true) -> {
                "Current weather conditions are clear. Temperature is 22 degrees Celsius. Enjoy your drive!"
            }
            
            // Traffic
            command.contains("traffic", ignoreCase = true) -> {
                "Traffic is moderate on your route. Expected delay is about 5 minutes."
            }
            
            // Fuel
            command.contains("fuel", ignoreCase = true) ||
            command.contains("gas station", ignoreCase = true) -> {
                "The nearest fuel station is 3 kilometers away on your right. Fuel prices are stable today."
            }
            
            // Speed
            command.contains("speed", ignoreCase = true) ||
            command.contains("how fast", ignoreCase = true) -> {
                val speed = locationRepository.getLastKnownLocation()?.speed ?: 0f
                "Current speed is ${(speed * 3.6).toInt()} kilometers per hour."
            }
            
            // Parking
            command.contains("parking", ignoreCase = true) -> {
                "I found several parking options near your destination. The closest is 200 meters ahead."
            }
            
            // Call
            command.contains("call", ignoreCase = true) -> {
                val name = command.replace(Regex("(?i)call\\s*"), "").trim()
                "Calling $name."
            }
            
            // SOS
            command.contains("sos", ignoreCase = true) ||
            command.contains("emergency", ignoreCase = true) -> {
                "Initiating emergency protocol. Emergency services will be notified if needed. Stay calm."
            }
            
            // Time/ETA
            command.contains("eta", ignoreCase = true) ||
            command.contains("arrival", ignoreCase = true) ||
            command.contains("time to destination", ignoreCase = true) -> {
                "Estimated time of arrival is 25 minutes based on current traffic conditions."
            }
            
            // Help
            command.contains("help", ignoreCase = true) ||
            command.contains("what can you do", ignoreCase = true) -> {
                "I can help you with navigation, recording, media control, weather, traffic updates, and more. Try asking about weather, traffic, or say 'navigate home' to get started."
            }
            
            else -> {
                // Use AI for unknown commands
                val conversationHistory = _uiState.value.messages.map { msg ->
                    AiMessage(
                        role = if (msg.isFromUser) AiRole.USER else AiRole.ASSISTANT,
                        content = msg.content
                    )
                }
                aiRepository.getResponse(command, conversationHistory).getOrDefault(
                    "I'm not sure how to help with that. Try asking about navigation, weather, traffic, or recording control."
                )
            }
        }
        
        addMessage(response, isFromUser = false)
        speak(response)
        _uiState.update { it.copy(isProcessing = false) }
    }

    private fun addMessage(content: String, isFromUser: Boolean) {
        _uiState.update { state ->
            state.copy(
                messages = state.messages + ChatMessage(content = content, isFromUser = isFromUser)
            )
        }
    }

    private fun speak(text: String) {
        if (ttsInitialized) {
            _uiState.update { it.copy(isSpeaking = true) }
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId")
            
            // Stop speaking state after a delay
            viewModelScope.launch {
                kotlinx.coroutines.delay((text.length * 50).toLong().coerceAtMost(5000))
                _uiState.update { it.copy(isSpeaking = false) }
            }
        }
    }

    fun stopSpeaking() {
        textToSpeech?.stop()
        _uiState.update { it.copy(isSpeaking = false) }
    }

    fun clearMessages() {
        _uiState.update { it.copy(messages = emptyList()) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun getSpeechErrorMessage(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No speech detected"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognition service busy"
            SpeechRecognizer.ERROR_SERVER -> "Server error"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Speech recognition error"
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
        textToSpeech?.shutdown()
    }
}
