package com.roadpilot.ai

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.roadpilot.ai.ui.DashboardScreen
import com.roadpilot.ai.ui.theme.RoadpilotTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            var status by remember {
                mutableStateOf("Ready")
            }

            RoadpilotTheme {
                DashboardScreen(
                    status = status,

                    onDashcamClick = {
                        Toast.makeText(this, "Dashcam clicked", Toast.LENGTH_SHORT).show()
                    },

                    onNavigationClick = {
                        Toast.makeText(this, "Navigation clicked", Toast.LENGTH_SHORT).show()
                    },

                    onAIAssistantClick = {
                        status = "🎤 Listening..."
                        Toast.makeText(this, "Voice Assistant starting...", Toast.LENGTH_SHORT).show()
                    },

                    onMusicClick = {
                        Toast.makeText(this, "Music clicked", Toast.LENGTH_SHORT).show()
                    },

                    onVehicleClick = {
                        Toast.makeText(this, "Vehicle clicked", Toast.LENGTH_SHORT).show()
                    },

                    onSettingsClick = {
                        Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                    }

                )
            }
        }
    }
}