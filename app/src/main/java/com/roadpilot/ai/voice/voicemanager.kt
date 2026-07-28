package com.roadpilot.ai.voice

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import java.util.Locale

object VoiceManager {

    fun createSpeechIntent(context: Context): Intent {

        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // Malayalam and English are both supported by Google's speech service.
            // We'll let the phone use the currently selected language.
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