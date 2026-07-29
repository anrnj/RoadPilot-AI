package com.roadpilot.ai.feature.command

enum class CommandType {
    OPEN_MAPS,
    PLAY_MUSIC,
    OPEN_SETTINGS,
    UNKNOWN
}

class CommandProcessor {

    fun process(text: String): CommandType {

        val command = text.lowercase()

        return when {
            command.contains("map") ||
                    command.contains("navigate") ->
                CommandType.OPEN_MAPS

            command.contains("music") ||
                    command.contains("song") ||
                    command.contains("play") ->
                CommandType.PLAY_MUSIC

            command.contains("settings") ->
                CommandType.OPEN_SETTINGS

            else ->
                CommandType.UNKNOWN
        }
    }
}