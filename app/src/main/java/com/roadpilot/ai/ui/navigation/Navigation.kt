package com.roadpilot.ai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Drive : Screen(
        route = "drive",
        title = "Drive",
        selectedIcon = Icons.Filled.DirectionsCar,
        unselectedIcon = Icons.Outlined.DirectionsCar
    )

    object Camera : Screen(
        route = "camera",
        title = "Camera",
        selectedIcon = Icons.Filled.Videocam,
        unselectedIcon = Icons.Outlined.Videocam
    )

    object AI : Screen(
        route = "ai",
        title = "AI",
        selectedIcon = Icons.Filled.Psychology,
        unselectedIcon = Icons.Outlined.Psychology
    )

    object Settings : Screen(
        route = "settings",
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    object RecordingDetail : Screen(
        route = "recording/{recordingId}",
        title = "Recording",
        selectedIcon = Icons.Filled.PlayCircle,
        unselectedIcon = Icons.Outlined.PlayCircle
    ) {
        fun createRoute(recordingId: Long) = "recording/$recordingId"
    }

    object TripHistory : Screen(
        route = "trips",
        title = "Trip History",
        selectedIcon = Icons.Filled.Route,
        unselectedIcon = Icons.Outlined.Route
    )

    object Vehicle : Screen(
        route = "vehicle",
        title = "Vehicle",
        selectedIcon = Icons.Filled.LocalGasStation,
        unselectedIcon = Icons.Outlined.LocalGasStation
    )

    object Safety : Screen(
        route = "safety",
        title = "Safety",
        selectedIcon = Icons.Filled.Shield,
        unselectedIcon = Icons.Outlined.Shield
    )

    object RecordingSettings : Screen(
        route = "recording_settings",
        title = "Recording Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    object AISettings : Screen(
        route = "ai_settings",
        title = "AI Settings",
        selectedIcon = Icons.Filled.Psychology,
        unselectedIcon = Icons.Outlined.Psychology
    )

    object About : Screen(
        route = "about",
        title = "About",
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info
    )

    companion object {
        val bottomNavItems = listOf(Drive, Camera, AI, Settings)
    }
}
