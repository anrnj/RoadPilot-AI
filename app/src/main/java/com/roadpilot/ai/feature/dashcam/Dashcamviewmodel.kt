package com.roadpilot.ai.feature.dashcam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DashcamViewModel : ViewModel() {

    var isRecording by mutableStateOf(false)
        private set

    var status by mutableStateOf("Camera Ready")
        private set

    fun startRecording() {
        isRecording = true
        status = "Recording..."
    }

    fun stopRecording() {
        isRecording = false
        status = "Recording Stopped"
    }
}