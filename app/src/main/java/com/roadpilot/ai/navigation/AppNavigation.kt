package com.roadpilot.ai.navigation

import com.roadpilot.ai.feature.dashcam.DashcamScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(
    navController: NavHostController,
    dashboard: @Composable () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = AppScreen.Dashboard.route
    ) {

        composable(AppScreen.Dashboard.route) {
            dashboard()
        }

        composable(AppScreen.Dashcam.route) {
            DashcamScreen()
        }

        composable(AppScreen.Navigation.route) {
            NavigationScreen()
        }

        composable(AppScreen.Assistant.route) {
            AssistantScreen()
        }

        composable(AppScreen.Music.route) {
            MusicScreen()
        }

        composable(AppScreen.Vehicle.route) {
            VehicleScreen()
        }

        composable(AppScreen.Settings.route) {
            SettingsScreen()
        }
    }
}


@Composable
fun NavigationScreen() {
    Text("🗺 Navigation")
}

@Composable
fun AssistantScreen() {
    Text("🎤 AI Assistant")
}

@Composable
fun MusicScreen() {
    Text("🎵 Music")
}

@Composable
fun VehicleScreen() {
    Text("🚗 Vehicle")
}

@Composable
fun SettingsScreen() {
    Text("⚙ Settings")
}