package com.roadpilot.ai.feature.executor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.roadpilot.ai.feature.command.CommandResult
import com.roadpilot.ai.feature.command.CommandType
import com.roadpilot.ai.feature.music.MusicManager

class CommandExecutor(
    private val context: Context
) {

    private val musicManager = MusicManager(context)

    fun execute(
        result: CommandResult,
        onStatusChanged: (String) -> Unit
    ) {

        when (result.type) {

            CommandType.OPEN_SETTINGS -> {

                onStatusChanged("⚙ Opening Settings...")

                context.startActivity(
                    Intent(Settings.ACTION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }

            CommandType.OPEN_MAPS -> {

                onStatusChanged("🗺 Opening Google Maps...")

                val intent = context.packageManager
                    .getLaunchIntentForPackage("com.google.android.apps.maps")

                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    onStatusChanged("Google Maps not installed")
                }
            }

            CommandType.NAVIGATE -> {

                val destination = result.destination ?: return

                onStatusChanged("🧭 Navigating to $destination")

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=$destination")
                )

                intent.setPackage("com.google.android.apps.maps")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    onStatusChanged("Google Maps not installed")
                }
            }

            CommandType.PLAY_MUSIC -> {

                onStatusChanged("🎵 Opening Music...")

                val opened = musicManager.openMusicApp()

                if (!opened) {
                    onStatusChanged("No music app installed")
                }
            }

            CommandType.UNKNOWN -> {

                onStatusChanged("Command not recognized")
            }
        }
    }
}