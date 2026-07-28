package com.roadpilot.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.roadpilot.ai.ui.DashboardScreen
import com.roadpilot.ai.ui.theme.RoadpilotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RoadpilotTheme {
                DashboardScreen()
            }
        }
    }
}