package com.roadpilot.ai.feature.dashcam

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.roadpilot.ai.core.camera.CameraManager
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraManager: CameraManager,
    speed: Int
)
 {

    val context = LocalContext.current

    val previewView = remember {
        PreviewView(context)
    }

    var dateTime by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        val formatter = SimpleDateFormat(
            "dd/MM/yyyy  HH:mm:ss",
            Locale.getDefault()
        )

        while (true) {
            dateTime = formatter.format(Date())
            delay(1000)
        }
    }

    DisposableEffect(Unit) {

        cameraManager.startCamera(previewView)

        onDispose { }
    }

    Box(modifier = modifier) {

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        // Date & Time
        Text(
            text = dateTime,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            style = MaterialTheme.typography.titleMedium
        )

        // Speed
        Text(
            text = "🚗 $speed km/h",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}