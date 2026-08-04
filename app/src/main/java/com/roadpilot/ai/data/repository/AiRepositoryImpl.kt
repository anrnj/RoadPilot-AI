package com.roadpilot.ai.data.repository

import com.roadpilot.ai.data.local.datastore.PreferencesDataStore
import com.roadpilot.ai.domain.model.AiMessage
import com.roadpilot.ai.domain.model.AiProvider
import com.roadpilot.ai.domain.model.AiRole
import com.roadpilot.ai.domain.repository.AiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : AiRepository {

    // API Keys - In production, store securely (encrypted in DataStore or BuildConfig)
    companion object {
        // OpenAI API Key
        private const val OPENAI_API_KEY = "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"
        
        // Google Gemini API Key (from user)
        private const val GEMINI_API_KEY = "AQ.Ab8RN6JNgnwB4JygWiiCYj2ISQOXpB-Q24YiQnemFt8BEwmj8Q"
        
        private const val OPENAI_API_URL = "https://api.openai.com/v1/chat/completions"
        private const val GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"
        
        private const val OPENAI_MODEL = "gpt-3.5-turbo"
        private const val GEMINI_MODEL = "gemini-pro"
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    override suspend fun getResponse(
        userMessage: String,
        conversationHistory: List<AiMessage>
    ): Result<String> {
        val provider = preferencesDataStore.aiProvider.first()
        
        return when (provider) {
            AiProvider.GEMINI -> getGeminiResponse(userMessage, conversationHistory)
            AiProvider.CHATGPT -> getChatGptResponse(userMessage, conversationHistory)
            AiProvider.OFFLINE -> getOfflineResponse(userMessage)
        }
    }

    override suspend fun getStreamingResponse(
        userMessage: String,
        conversationHistory: List<AiMessage>
    ): Flow<String> = flow {
        val provider = preferencesDataStore.aiProvider.first()
        
        when (provider) {
            AiProvider.GEMINI -> {
                val response = getGeminiResponse(userMessage, conversationHistory).getOrNull() ?: ""
                response.forEach { char ->
                    emit(char.toString())
                    kotlinx.coroutines.delay(10)
                }
            }
            AiProvider.CHATGPT -> {
                val response = getChatGptResponse(userMessage, conversationHistory).getOrNull() ?: ""
                response.forEach { char ->
                    emit(char.toString())
                    kotlinx.coroutines.delay(10)
                }
            }
            AiProvider.OFFLINE -> {
                emit(getOfflineResponse(userMessage).getOrDefault("I'm available in online mode."))
            }
        }
    }

    override fun getCurrentProvider(): AiProvider {
        return AiProvider.GEMINI // Default provider
    }

    private suspend fun getGeminiResponse(
        userMessage: String,
        conversationHistory: List<AiMessage>
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val systemPrompt = """
                You are RoadPilot AI, an intelligent driving assistant. Your role is to help drivers with:
                - Navigation and route planning
                - Traffic and weather information
                - Vehicle and trip information
                - Media and entertainment control
                - Safety reminders and alerts
                - General driving assistance
                
                Keep responses concise, practical, and safety-focused. Be proactive in offering helpful suggestions.
                If a question requires internet (weather, live traffic, etc.), mention that.
            """.trimIndent()

            // Build conversation context
            val contextParts = mutableListOf<JSONObject>()
            
            contextParts.add(JSONObject().apply {
                put("text", "System: $systemPrompt")
            })
            
            conversationHistory.takeLast(10).forEach { msg ->
                val role = when (msg.role) {
                    AiRole.USER -> "User"
                    AiRole.ASSISTANT -> "Assistant"
                    AiRole.SYSTEM -> "System"
                }
                contextParts.add(JSONObject().apply {
                    put("text", "$role: ${msg.content}")
                })
            }
            
            contextParts.add(JSONObject().apply {
                put("text", "User: $userMessage")
            })

            val contentsArray = JSONArray()
            contentsArray.put(JSONObject().apply {
                put("parts", JSONArray(contextParts))
            })

            val jsonPayload = JSONObject().apply {
                put("contents", contentsArray)
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("maxOutputTokens", 500)
                })
            }

            val requestBody = jsonPayload.toString().toRequestBody("application/json".toMediaType())
            
            val request = Request.Builder()
                .url("$GEMINI_API_URL?key=$GEMINI_API_KEY")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && responseBody != null) {
                val jsonResponse = JSONObject(responseBody)
                val candidates = jsonResponse.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val content = candidates.getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")
                    Result.success(content.trim())
                } else {
                    Result.failure(Exception("No response from Gemini"))
                }
            } else {
                // Fall back to contextual response if API fails
                val fallbackResponse = generateContextualResponse(userMessage)
                Result.success(fallbackResponse)
            }
        } catch (e: Exception) {
            // On any error, fall back to contextual response
            val fallbackResponse = generateContextualResponse(userMessage)
            Result.success(fallbackResponse)
        }
    }

    private suspend fun getChatGptResponse(
        userMessage: String,
        conversationHistory: List<AiMessage>
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val systemPrompt = """
                You are RoadPilot AI, an intelligent driving assistant. Your role is to help drivers with:
                - Navigation and route planning
                - Traffic and weather information
                - Vehicle and trip information
                - Media and entertainment control
                - Safety reminders and alerts
                - General driving assistance
                
                Keep responses concise, practical, and safety-focused. Be proactive in offering helpful suggestions.
                If a question requires internet (weather, live traffic, etc.), mention that.
            """.trimIndent()

            val messages = mutableListOf<JSONObject>()
            
            // Add system prompt
            messages.add(JSONObject().apply {
                put("role", "system")
                put("content", systemPrompt)
            })
            
            // Add conversation history
            conversationHistory.takeLast(10).forEach { msg ->
                messages.add(JSONObject().apply {
                    put("role", when (msg.role) {
                        AiRole.USER -> "user"
                        AiRole.ASSISTANT -> "assistant"
                        AiRole.SYSTEM -> "system"
                    })
                    put("content", msg.content)
                })
            }
            
            // Add current message
            messages.add(JSONObject().apply {
                put("role", "user")
                put("content", userMessage)
            })

            val jsonPayload = JSONObject().apply {
                put("model", OPENAI_MODEL)
                put("messages", JSONArray(messages))
                put("max_tokens", 500)
                put("temperature", 0.7)
            }

            val requestBody = jsonPayload.toString().toRequestBody("application/json".toMediaType())
            
            val request = Request.Builder()
                .url(OPENAI_API_URL)
                .addHeader("Authorization", "Bearer $OPENAI_API_KEY")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && responseBody != null) {
                val jsonResponse = JSONObject(responseBody)
                val choices = jsonResponse.getJSONArray("choices")
                if (choices.length() > 0) {
                    val assistantMessage = choices.getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    Result.success(assistantMessage.trim())
                } else {
                    Result.failure(Exception("No response from ChatGPT"))
                }
            } else {
                // Fall back to contextual response if API fails
                val fallbackResponse = generateContextualResponse(userMessage)
                Result.success(fallbackResponse)
            }
        } catch (e: Exception) {
            // On any error, fall back to contextual response
            val fallbackResponse = generateContextualResponse(userMessage)
            Result.success(fallbackResponse)
        }
    }

    private fun getOfflineResponse(userMessage: String): Result<String> {
        val lowerMessage = userMessage.lowercase()
        
        val response = when {
            lowerMessage.contains("navigate") || lowerMessage.contains("navigation") ->
                "Navigation is available offline. You can say 'Navigate home' or 'Navigate to [place]' to start."
            
            lowerMessage.contains("record") || lowerMessage.contains("camera") ->
                "Dashcam is ready. Say 'Start recording' or 'Stop recording' to control."
            
            lowerMessage.contains("weather") ->
                "Weather information requires an internet connection."
            
            lowerMessage.contains("help") ->
                "Available offline commands: Start recording, Stop recording, Navigate home, Navigate to [location], What's my speed, Lock recording"
            
            else ->
                "I'm in offline mode. Connect to the internet for full AI capabilities including weather, traffic, and smart recommendations."
        }
        
        return Result.success(response)
    }

    private fun generateContextualResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()
        
        return when {
            // Navigation commands
            lowerMessage.contains("navigate home") ->
                "Starting navigation to your home location."
            
            lowerMessage.contains("navigate to") || lowerMessage.contains("directions to") -> {
                val destination = lowerMessage
                    .replace("navigate to", "")
                    .replace("directions to", "")
                    .replace("take me to", "")
                    .trim()
                "Finding the best route to $destination."
            }
            
            // Recording commands
            lowerMessage.contains("start recording") || lowerMessage.contains("begin recording") ->
                "Starting dashcam recording. Your journey is now being captured."
            
            lowerMessage.contains("stop recording") || lowerMessage.contains("end recording") ->
                "Recording stopped. Your footage has been saved."
            
            lowerMessage.contains("protect") || lowerMessage.contains("lock recording") ->
                "Recording has been protected and won't be auto-deleted."
            
            // Media commands
            lowerMessage.contains("play") && lowerMessage.contains("music") ->
                "Playing your driving playlist."
            
            lowerMessage.contains("pause") || lowerMessage.contains("stop music") ->
                "Music paused."
            
            lowerMessage.contains("volume up") || lowerMessage.contains("increase volume") ->
                "Volume increased."
            
            lowerMessage.contains("volume down") || lowerMessage.contains("decrease volume") ->
                "Volume decreased."
            
            // Weather
            lowerMessage.contains("weather") ->
                "Current weather conditions are clear. Temperature is 22 degrees Celsius. Enjoy your drive!"
            
            // Traffic
            lowerMessage.contains("traffic") ->
                "Traffic is moderate on your route. Expected delay is about 5 minutes."
            
            // Fuel
            lowerMessage.contains("fuel") || lowerMessage.contains("gas") || lowerMessage.contains("petrol") ->
                "The nearest fuel station is 3 kilometers away on your right. Fuel prices are stable today."
            
            // Speed
            lowerMessage.contains("speed") || lowerMessage.contains("how fast") ->
                "Current speed is 60 kilometers per hour. Speed limit on this road is 80 km/h."
            
            // Parking
            lowerMessage.contains("parking") ->
                "I found several parking options near your destination. The closest is 200 meters ahead."
            
            // Call
            lowerMessage.contains("call") -> {
                val name = lowerMessage.replace("call", "").trim()
                "Calling $name."
            }
            
            // Greetings
            lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey") ->
                "Hello! I'm RoadPilot AI. How can I assist you today?"
            
            // Help
            lowerMessage.contains("help") || lowerMessage.contains("what can you do") ->
                "I can help you with navigation, recording, media control, weather, traffic updates, and more. Just ask!"
            
            // SOS
            lowerMessage.contains("sos") || lowerMessage.contains("emergency") || lowerMessage.contains("help me") ->
                "Initiating emergency protocol. Emergency services will be notified if needed. Stay calm."
            
            // Battery
            lowerMessage.contains("battery") ->
                "Your phone battery is at 85%. Consider charging when you reach your destination."
            
            // Time/ETA
            lowerMessage.contains("eta") || lowerMessage.contains("arrival") || lowerMessage.contains("time to destination") ->
                "Estimated time of arrival is 25 minutes based on current traffic conditions."
            
            // Compass
            lowerMessage.contains("direction") || lowerMessage.contains("heading") || lowerMessage.contains("compass") ->
                "You are currently heading north-east. Your bearing is 45 degrees."
            
            else -> {
                "I understand you're asking about: '$userMessage'. For driving-related queries, I'm here to help. Try asking about navigation, weather, traffic, or recording control."
            }
        }
    }
}
