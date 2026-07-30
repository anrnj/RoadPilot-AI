package com.roadpilot.ai.core.speech

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import java.util.Locale

class SpeechRecognizerManager(private val context: Context) {

    fun createRecognizerIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Speak..."
            )
        }
    }
}