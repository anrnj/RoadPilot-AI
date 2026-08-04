package com.roadpilot.ai.ui.screens.drive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roadpilot.ai.domain.model.OperatingMode
import com.roadpilot.ai.ui.components.*
import com.roadpilot.ai.ui.theme.*

@Composable
fun DriveScreen(
    onNavigateToCamera: () -> Unit,
    onNavigateToAi: () -> Unit,
    onStartNavigation: () -> Unit,
    viewModel: DriveViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startLocationTracking()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopLocationTracking()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DashboardBackground,
                        BackgroundDark
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left panel - Speed and Compass
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Speed and Compass Card
                DashboardCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SpeedGauge(
                            speed = uiState.speed,
                            useMetric = uiState.useMetricUnits
                        )
                        CompassView(bearing = uiState.bearing)
                    }
                }

                // Quick Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionButton(
                        icon = if (uiState.isRecording) Icons.Filled.Stop else Icons.Filled.Videocam,
                        label = if (uiState.isRecording) "Stop" else "Record",
                        onClick = {
                            if (uiState.isRecording) {
                                viewModel.setRecordingState(false)
                            } else {
                                onNavigateToCamera()
                            }
                        },
                        isActive = uiState.isRecording,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionButton(
                        icon = if (uiState.isNavigating) Icons.Filled.Navigation else Icons.Filled.AddLocation,
                        label = "Navigate",
                        onClick = {
                            if (uiState.isNavigating) {
                                viewModel.stopNavigation()
                            } else {
                                onStartNavigation()
                            }
                        },
                        isActive = uiState.isNavigating,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionButton(
                        icon = Icons.Filled.Mic,
                        label = "Voice",
                        onClick = onNavigateToAi,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionButton(
                        icon = Icons.Filled.Route,
                        label = "Trip",
                        onClick = { /* Navigate to trip details */ },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Recording Status
                RecordingIndicator(
                    isRecording = uiState.isRecording,
                    duration = uiState.recordingDuration,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Center panel - Navigation and AI
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // AI Status
                DashboardCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AiStatusChip(
                                isListening = uiState.isAiListening,
                                isProcessing = uiState.isAiProcessing,
                                mode = when (uiState.operatingMode) {
                                    OperatingMode.OFFLINE -> "Offline"
                                    OperatingMode.ONLINE -> uiState.aiProvider.name
                                    OperatingMode.HYBRID -> "Hybrid"
                                }
                            )
                            
                            uiState.weather?.let { weather ->
                                WeatherChip(
                                    temperature = weather.temperature,
                                    condition = weather.condition
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Voice Wave Animation
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VoiceWaveAnimation(
                                isActive = uiState.isAiListening || uiState.isAiProcessing
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Quick Voice Commands
                        Text(
                            text = "Say \"Hey RoadPilot\" to start",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

                // Navigation Preview
                if (uiState.isNavigating) {
                    NavigationPreview(
                        destination = uiState.navigationDestination,
                        eta = uiState.eta,
                        distance = uiState.navigationDistance,
                        onClick = onStartNavigation,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Trip Stats
                uiState.currentTrip?.let { trip ->
                    DashboardCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Current Trip",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TripStatCard(
                                icon = Icons.Filled.Route,
                                label = "Distance",
                                value = "${trip.distance.toInt()} km"
                            )
                            TripStatCard(
                                icon = Icons.Filled.Speed,
                                label = "Avg Speed",
                                value = "${trip.averageSpeed.toInt()} km/h"
                            )
                            TripStatCard(
                                icon = Icons.Filled.Timer,
                                label = "Duration",
                                value = formatDuration(trip.startTime)
                            )
                        }
                    }
                } ?: run {
                    // Start Trip Button
                    Button(
                        onClick = { viewModel.startTrip() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Start Trip")
                    }
                }

                // Location Info
                DashboardCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.currentLocation?.let { location ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "GPS Coordinates",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "%.6f, %.6f".format(location.latitude, location.longitude),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Altitude",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${location.altitude.toInt()} m",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    } ?: run {
                        Text(
                            text = "Acquiring GPS signal...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Right panel - Mini Map placeholder
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .background(
                        color = DashboardCard,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Map,
                        contentDescription = null,
                        tint = DashboardAccent,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (uiState.currentLocation != null) "Map View" else "Loading Map...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Google Maps SDK",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Error Snackbar
        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                action = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(error)
            }
        }
    }
}

private fun formatDuration(startTime: Long): String {
    val duration = System.currentTimeMillis() - startTime
    val minutes = (duration / 1000 / 60) % 60
    val hours = duration / 1000 / 60 / 60
    return if (hours > 0) {
        "${hours}h ${minutes}m"
    } else {
        "${minutes}m"
    }
}
