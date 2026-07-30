package com.roadpilot.ai.navigation

sealed class AppScreen(val route: String) {

    object Dashboard : AppScreen("dashboard")

    object Dashcam : AppScreen("dashcam")

    object Navigation : AppScreen("navigation")

    object Assistant : AppScreen("assistant")

    object Music : AppScreen("music")

    object Vehicle : AppScreen("vehicle")

    object Settings : AppScreen("settings")
}