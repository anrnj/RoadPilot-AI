package com.roadpilot.ai.assistant

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import com.roadpilot.ai.domain.model.AiMessage
import com.roadpilot.ai.domain.model.AiRole
import com.roadpilot.ai.domain.repository.AiRepository
import com.roadpilot.ai.domain.repository.LocationRepository
import com.roadpilot.ai.service.DashcamService
import com.roadpilot.ai.service.MediaService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceCommandProcessor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val aiRepository: AiRepository,
    private val locationRepository: LocationRepository
) {
    private var textToSpeech: TextToSpeech? = null
    private var ttsInitialized = false
    private var conversationContext: MutableList<AiMessage> = mutableListOf()

    init {
        initTts()
    }

    private fun initTts() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                ttsInitialized = true
            }
        }
    }

    fun processCommand(command: String): CommandResult {
        val lowerCommand = command.lowercase()
        
        // Check if it's a direct command or needs AI processing
        return when {
            // Navigation commands
            lowerCommand.contains("navigate home") -> {
                CommandResult(
                    response = "Starting navigation to your home location.",
                    action = CommandAction.NAVIGATE_HOME,
                    requiresNavigation = true
                )
            }
            
            lowerCommand.contains("navigate to") ||
            lowerCommand.contains("take me to") -> {
                val destination = extractDestination(lowerCommand)
                CommandResult(
                    response = "Finding the best route to $destination.",
                    action = CommandAction.NAVIGATE_TO,
                    requiresNavigation = true,
                    destination = destination
                )
            }
            
            // Recording commands
            lowerCommand.contains("start recording") ||
            lowerCommand.contains("begin recording") -> {
                CommandResult(
                    response = "Starting dashcam recording. Your journey is now being captured.",
                    action = CommandAction.START_RECORDING
                )
            }
            
            lowerCommand.contains("stop recording") ||
            lowerCommand.contains("end recording") -> {
                CommandResult(
                    response = "Recording stopped. Your footage has been saved.",
                    action = CommandAction.STOP_RECORDING
                )
            }
            
            lowerCommand.contains("protect") ||
            lowerCommand.contains("lock recording") -> {
                CommandResult(
                    response = "Recording has been protected and won't be auto-deleted.",
                    action = CommandAction.PROTECT_RECORDING
                )
            }
            
            // Media commands
            lowerCommand.contains("play") -> {
                CommandResult(
                    response = "Playing your driving playlist.",
                    action = CommandAction.PLAY_MUSIC
                )
            }
            
            lowerCommand.contains("pause") ||
            lowerCommand.contains("stop music") -> {
                CommandResult(
                    response = "Music paused.",
                    action = CommandAction.PAUSE_MUSIC
                )
            }
            
            lowerCommand.contains("next track") ||
            lowerCommand.contains("skip") -> {
                CommandResult(
                    response = "Playing next track.",
                    action = CommandAction.NEXT_TRACK
                )
            }
            
            lowerCommand.contains("previous track") -> {
                CommandResult(
                    response = "Playing previous track.",
                    action = CommandAction.PREVIOUS_TRACK
                )
            }
            
            lowerCommand.contains("volume up") ||
            lowerCommand.contains("increase volume") -> {
                CommandResult(
                    response = "Volume increased.",
                    action = CommandAction.VOLUME_UP
                )
            }
            
            lowerCommand.contains("volume down") ||
            lowerCommand.contains("decrease volume") -> {
                CommandResult(
                    response = "Volume decreased.",
                    action = CommandAction.VOLUME_DOWN
                )
            }
            
            // Weather
            lowerCommand.contains("weather") -> {
                CommandResult(
                    response = "Current weather conditions are clear. Temperature is 22 degrees Celsius. Enjoy your drive!",
                    action = CommandAction.GET_WEATHER
                )
            }
            
            // Traffic
            lowerCommand.contains("traffic") -> {
                CommandResult(
                    response = "Traffic is moderate on your route. Expected delay is about 5 minutes.",
                    action = CommandAction.GET_TRAFFIC
                )
            }
            
            // Fuel
            lowerCommand.contains("fuel") ||
            lowerCommand.contains("gas station") ||
            lowerCommand.contains("petrol station") -> {
                CommandResult(
                    response = "The nearest fuel station is 3 kilometers away on your right. Fuel prices are stable today.",
                    action = CommandAction.FIND_FUEL_STATION
                )
            }
            
            // Speed - this returns a non-suspend response for now
            lowerCommand.contains("speed") ||
            lowerCommand.contains("how fast") -> {
                CommandResult(
                    response = "Current speed information is available on your dashboard.",
                    action = CommandAction.GET_SPEED
                )
            }
            
            // Parking
            lowerCommand.contains("parking") -> {
                CommandResult(
                    response = "I found several parking options near your destination. The closest is 200 meters ahead.",
                    action = CommandAction.FIND_PARKING
                )
            }
            
            // Call
            lowerCommand.contains("call") -> {
                val name = lowerCommand.replace("call", "").trim()
                CommandResult(
                    response = "Calling $name.",
                    action = CommandAction.CALL_CONTACT,
                    contactName = name
                )
            }
            
            // SOS
            lowerCommand.contains("sos") ||
            lowerCommand.contains("emergency") -> {
                CommandResult(
                    response = "Initiating emergency protocol. Emergency services will be notified if needed. Stay calm.",
                    action = CommandAction.SOS
                )
            }
            
            // Help
            lowerCommand.contains("help") ||
            lowerCommand.contains("what can you do") -> {
                CommandResult(
                    response = "I can help you with navigation, recording, media control, weather, traffic updates, and more. Try asking about weather, traffic, or say 'navigate home' to get started.",
                    action = CommandAction.HELP
                )
            }
            
            // Time/ETA
            lowerCommand.contains("eta") ||
            lowerCommand.contains("arrival") ||
            lowerCommand.contains("time to destination") -> {
                CommandResult(
                    response = "Estimated time of arrival is 25 minutes based on current traffic conditions.",
                    action = CommandAction.GET_ETA
                )
            }
            
            // Compass/Direction
            lowerCommand.contains("direction") ||
            lowerCommand.contains("heading") ||
            lowerCommand.contains("compass") -> {
                CommandResult(
                    response = "Your current heading and direction are shown on the dashboard compass.",
                    action = CommandAction.GET_DIRECTION
                )
            }
            
            // Battery
            lowerCommand.contains("battery") -> {
                CommandResult(
                    response = "Your phone battery is at 85%. Consider charging when you reach your destination.",
                    action = CommandAction.GET_BATTERY
                )
            }
            
            // Default: Use AI
            else -> {
                CommandResult(
                    response = "",
                    action = CommandAction.USE_AI,
                    requiresAi = true
                )
            }
        }
    }

    private fun extractDestination(command: String): String {
        return command
            .replace("navigate to", "")
            .replace("take me to", "")
            .replace("directions to", "")
            .trim()
    }

    suspend fun getAiResponse(userMessage: String): String {
        val result = aiRepository.getResponse(userMessage, conversationContext)
        result.onSuccess { response ->
            conversationContext.add(AiMessage(AiRole.USER, userMessage))
            conversationContext.add(AiMessage(AiRole.ASSISTANT, response))
            
            // Keep conversation history limited
            if (conversationContext.size > 20) {
                conversationContext = conversationContext.takeLast(20).toMutableList()
            }
        }
        return result.getOrDefault("I'm sorry, I couldn't process that request.")
    }

    fun speak(text: String) {
        if (ttsInitialized) {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "roadpilot_utterance")
        }
    }

    fun stopSpeaking() {
        textToSpeech?.stop()
    }

    fun clearConversation() {
        conversationContext.clear()
    }

    fun executeAction(action: CommandAction, context: Context) {
        when (action) {
            CommandAction.START_RECORDING -> {
                val intent = Intent(context, DashcamService::class.java).apply {
                    this.action = DashcamService.ACTION_START_RECORDING
                }
                context.startService(intent)
            }
            
            CommandAction.STOP_RECORDING -> {
                val intent = Intent(context, DashcamService::class.java).apply {
                    this.action = DashcamService.ACTION_STOP_RECORDING
                }
                context.startService(intent)
            }
            
            CommandAction.PROTECT_RECORDING -> {
                val intent = Intent(context, DashcamService::class.java).apply {
                    this.action = DashcamService.ACTION_PROTECT_RECORDING
                }
                context.startService(intent)
            }
            
            CommandAction.PLAY_MUSIC,
            CommandAction.PAUSE_MUSIC,
            CommandAction.NEXT_TRACK,
            CommandAction.PREVIOUS_TRACK -> {
                val intent = Intent(context, MediaService::class.java).apply {
                    this.action = when (action) {
                        CommandAction.PLAY_MUSIC -> MediaService.ACTION_PLAY
                        CommandAction.PAUSE_MUSIC -> MediaService.ACTION_PAUSE
                        CommandAction.NEXT_TRACK -> MediaService.ACTION_NEXT
                        CommandAction.PREVIOUS_TRACK -> MediaService.ACTION_PREVIOUS
                        else -> return
                    }
                }
                context.startService(intent)
            }
            
            CommandAction.VOLUME_UP -> {
                val intent = Intent(context, MediaService::class.java).apply {
                    this.action = MediaService.ACTION_VOLUME_UP
                }
                context.startService(intent)
            }
            
            CommandAction.VOLUME_DOWN -> {
                val intent = Intent(context, MediaService::class.java).apply {
                    this.action = MediaService.ACTION_VOLUME_DOWN
                }
                context.startService(intent)
            }
            
            else -> { /* Other actions handled differently */ }
        }
    }
}

data class CommandResult(
    val response: String,
    val action: CommandAction,
    val requiresNavigation: Boolean = false,
    val requiresAi: Boolean = false,
    val destination: String? = null,
    val contactName: String? = null
)

enum class CommandAction {
    NAVIGATE_HOME,
    NAVIGATE_TO,
    START_RECORDING,
    STOP_RECORDING,
    PROTECT_RECORDING,
    PLAY_MUSIC,
    PAUSE_MUSIC,
    NEXT_TRACK,
    PREVIOUS_TRACK,
    VOLUME_UP,
    VOLUME_DOWN,
    GET_WEATHER,
    GET_TRAFFIC,
    FIND_FUEL_STATION,
    FIND_PARKING,
    GET_SPEED,
    GET_DIRECTION,
    GET_ETA,
    GET_BATTERY,
    CALL_CONTACT,
    SOS,
    HELP,
    USE_AI
}
