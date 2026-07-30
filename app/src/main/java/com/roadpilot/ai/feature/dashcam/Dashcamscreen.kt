package com.roadpilot.ai.feature.dashcam

import com.roadpilot.ai.core.location.CompassManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.roadpilot.ai.core.camera.CameraManager
import com.roadpilot.ai.core.location.LocationManager
import com.roadpilot.ai.core.permissions.RequestCameraPermission
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun DashcamScreen() {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraManager = remember {
        CameraManager(
            context = context,
            lifecycleOwner = lifecycleOwner
        )
    }


    val locationManager = remember {
        LocationManager(context)
    }

    val compassManager = remember {
        CompassManager(context)
    }

    var speed by remember { mutableStateOf(0) }
    var direction by remember { mutableStateOf("N") }

    var isRecording by remember { mutableStateOf(false) }
    var seconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(cameraManager) {
        cameraManager.onRecordingStateChanged = {
            isRecording = it
            if (!it) seconds = 0
        }
    }

    DisposableEffect(Unit) {

        locationManager.onLocationChanged = { location ->

            val kmh = (location.speed * 3.6f).roundToInt()

            speed = if (kmh < 0) 0 else kmh
        }

        locationManager.startLocationUpdates()
        compassManager.onDirectionChanged = {
            direction = it
        }

        compassManager.start()

        onDispose {
            locationManager.stopLocationUpdates()
            compassManager.stop()
        }
    }

    LaunchedEffect(isRecording) {
        if (!isRecording) {
            delay(1000)
            cameraManager.startRecording()
        }
    }

    LaunchedEffect(isRecording) {
        while (isRecording) {
            delay(1000)
            seconds++
        }
    }

    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Dashcam",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "🚗 $speed km/h",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "🧭 $direction",
            style = MaterialTheme.typography.titleLarge
        )

        if (isRecording) {

            Text(
                text = "🔴 REC",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = String.format("%02d:%02d", minutes, remainingSeconds),
                style = MaterialTheme.typography.titleLarge
            )
        }

        RequestCameraPermission(

            onPermissionGranted = {

                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    cameraManager = cameraManager,
                    speed = speed
                )

            },

            onPermissionDenied = {

                Text("Camera/Microphone/Location permission required.")

            }

        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                if (isRecording) {
                    cameraManager.stopRecording()
                } else {
                    cameraManager.startRecording()
                }
            }
        ) {

            Text(
                if (isRecording)
                    "Stop Recording"
                else
                    "Start Recording"
            )
        }
    }
}