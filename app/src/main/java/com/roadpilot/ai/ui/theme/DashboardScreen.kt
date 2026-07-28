package com.roadpilot.ai.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(
    status: String = "Ready",
    onDashcamClick: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    onAIAssistantClick: () -> Unit = {},
    onMusicClick: () -> Unit = {},
    onVehicleClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🚗 RoadPilot AI",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onDashcamClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📹 Dashcam")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onNavigationClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🗺 Navigation")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onAIAssistantClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🎤 AI Assistant")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onMusicClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🎵 Music")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onVehicleClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🚗 Vehicle")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⚙ Settings")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Status: $status",
            fontSize = 18.sp
        )
    }
}