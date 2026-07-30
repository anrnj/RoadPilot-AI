package com.roadpilot.ai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.roadpilot.ai.feature.command.CommandProcessor
import com.roadpilot.ai.feature.dashboard.DashboardScreen
import com.roadpilot.ai.feature.dashboard.DashboardViewModel
import com.roadpilot.ai.feature.executor.CommandExecutor
import com.roadpilot.ai.navigation.AppNavigation
import com.roadpilot.ai.navigation.AppScreen
import com.roadpilot.ai.ui.theme.RoadpilotTheme

class MainActivity : ComponentActivity() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private val commandProcessor = CommandProcessor()

    private lateinit var commandExecutor: CommandExecutor

    private val speechLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val spokenText = result.data
                    ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    ?.firstOrNull()

                if (spokenText != null) {

                    val commandResult = commandProcessor.process(spokenText)

                    commandExecutor.execute(commandResult) { status ->
                        dashboardViewModel.updateStatus(status)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        commandExecutor = CommandExecutor(this)

        setContent {

            RoadpilotTheme {

                val navController = rememberNavController()

                AppNavigation(

                    navController = navController,

                    dashboard = {

                        DashboardScreen(

                            status = dashboardViewModel.status,

                            onDashcamClick = {
                                navController.navigate(AppScreen.Dashcam.route)
                            },

                            onNavigationClick = {
                                navController.navigate(AppScreen.Navigation.route)
                            },

                            onAIAssistantClick = {

                                dashboardViewModel.updateStatus("🎤 Listening...")

                                val intent = Intent(
                                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                                ).apply {

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
                                navController.navigate(AppScreen.Music.route)
                            },

                            onVehicleClick = {
                                navController.navigate(AppScreen.Vehicle.route)
                            },

                            onSettingsClick = {
                                navController.navigate(AppScreen.Settings.route)
                            }
                        )
                    }
                )
            }
        }
    }
}