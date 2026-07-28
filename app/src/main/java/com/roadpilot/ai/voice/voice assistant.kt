package com.roadpilot.ai.voice

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import java.util.*

class VoiceAssistant(private val activity: Activity) {

    companion object {
        const val SPEECH_REQUEST_CODE = 100
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
        }

        activity.startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }
}