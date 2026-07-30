package com.roadpilot.ai.feature.music

import android.content.Context
import android.content.Intent
import android.widget.Toast

class MusicManager(
    private val context: Context
) {

    fun openMusicApp(): Boolean {

        // Try Spotify
        var intent = context.packageManager.getLaunchIntentForPackage(
            "com.spotify.music"
        )

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return true
        }

        // Try YouTube Music
        intent = context.packageManager.getLaunchIntentForPackage(
            "com.google.android.apps.youtube.music"
        )

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return true
        }

        // Try generic music player
        intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_MUSIC)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
            return true
        }

        Toast.makeText(
            context,
            "No music app installed",
            Toast.LENGTH_SHORT
        ).show()

        return false
    }
}