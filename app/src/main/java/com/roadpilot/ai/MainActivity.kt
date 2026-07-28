package com.roadpilot.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roadpilot.ai.ui.DashboardScreen
import com.roadpilot.ai.ui.theme.RoadpilotTheme
import com.roadpilot.ai.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val dashboardViewModel: DashboardViewModel = viewModel()

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