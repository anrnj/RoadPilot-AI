package com.roadpilot.ai.feature.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    var status by mutableStateOf("Ready")
        private set

    fun updateStatus(newStatus: String) {
        status = newStatus
    }
}