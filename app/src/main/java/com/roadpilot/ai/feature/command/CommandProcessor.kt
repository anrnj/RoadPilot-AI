package com.roadpilot.ai.feature.command

data class CommandResult(
    val type: CommandType,
    val destination: String? = null
)

enum class CommandType {
    OPEN_MAPS,
    NAVIGATE,
    PLAY_MUSIC,
    OPEN_SETTINGS,
    UNKNOWN
}

class CommandProcessor {

    fun process(text: String): CommandResult {

        val command = text.lowercase().trim()

        return when {

            command.startsWith("navigate to ") -> {
                val destination = text.substringAfter("Navigate to ").trim()
                CommandResult(CommandType.NAVIGATE, destination)
            }

            command.contains("map") ->
                CommandResult(CommandType.OPEN_MAPS)

            command.contains("music") ||
                    command.contains("play") ->
                CommandResult(CommandType.PLAY_MUSIC)

            command.contains("settings") ->
                CommandResult(CommandType.OPEN_SETTINGS)

            else ->
                CommandResult(CommandType.UNKNOWN)
        }
    }
}