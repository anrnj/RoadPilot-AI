package com.roadpilot.ai.navigation

sealed class Screen(val route: String) {

    data object Dashboard : Screen("dashboard")

    data object Dashcam : Screen("dashcam")

    data object Navigation : Screen("navigation")

    data object Assistant : Screen("assistant")

    data object Music : Screen("music")

    data object Vehicle : Screen("vehicle")

    data object Settings : Screen("settings")
}