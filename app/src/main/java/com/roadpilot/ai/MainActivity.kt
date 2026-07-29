package com.roadpilot.ai
import android.provider.Settings
import com.roadpilot.ai.feature.command.CommandProcessor
import com.roadpilot.ai.feature.command.CommandType

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.roadpilot.ai.ui.DashboardScreen
import com.roadpilot.ai.ui.theme.RoadpilotTheme
import com.roadpilot.ai.viewmodel.DashboardViewModel
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider


class MainActivity : ComponentActivity() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private val commandProcessor = CommandProcessor()

    private val speechLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()

            if (spokenText != null) {

                dashboardViewModel.updateStatus(spokenText)

                when (commandProcessor.process(spokenText)) {

                    CommandType.OPEN_SETTINGS -> {
                        startActivity(
                            Intent(Settings.ACTION_SETTINGS)
                        )
                    }

                    CommandType.OPEN_MAPS -> {

                        dashboardViewModel.updateStatus("🗺 Opening Maps...")

                        val intent = packageManager.getLaunchIntentForPackage(
                            "com.google.android.apps.maps"
                        )

                        if (intent != null) {
                            startActivity(intent)
                        } else {
                            dashboardViewModel.updateStatus("Google Maps not installed")
                        }
                    }

                    CommandType.PLAY_MUSIC -> {
                        dashboardViewModel.updateStatus("🎵 Opening Music...")
                    }

                    CommandType.UNKNOWN -> {
                        dashboardViewModel.updateStatus("❓ Unknown Command")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

            RoadpilotTheme {

                DashboardScreen(

                    status = dashboardViewModel.status,

                    onDashcamClick = {
                        dashboardViewModel.updateStatus("Dashcam Selected")
                    },

                    onNavigationClick = {
                        dashboardViewModel.updateStatus("Navigation Selected")
                    },

                    onAIAssistantClick = {

                        dashboardViewModel.updateStatus("🎤 Listening...")

                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE,
                                "en-IN"
                            )
                            putExtra(
                                RecognizerIntent.EXTRA_PROMPT,
                                "Speak now..."
                            )
                        }

                        speechLauncher.launch(intent)
                    },

                    onMusicClick = {
                        dashboardViewModel.updateStatus("Music Selected")
                    },

                    onVehicleClick = {
                        dashboardViewModel.updateStatus("Vehicle Selected")
                    },

                    onSettingsClick = {
                        dashboardViewModel.updateStatus("Settings Selected")
                    }

                )
            }
        }
    }
}