package com.roadpilot.ai.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roadpilot.ai.domain.model.*
import com.roadpilot.ai.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DashboardBackground, BackgroundDark)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Operating Mode
            item {
                SettingsSection(
                    title = "Operating Mode",
                    icon = Icons.Filled.Cloud
                ) {
                    OperatingMode.entries.forEach { mode ->
                        RadioOption(
                            title = when (mode) {
                                OperatingMode.OFFLINE -> "Offline"
                                OperatingMode.ONLINE -> "Online"
                                OperatingMode.HYBRID -> "Hybrid (Recommended)"
                            },
                            description = when (mode) {
                                OperatingMode.OFFLINE -> "Core features without internet"
                                OperatingMode.ONLINE -> "Full AI capabilities"
                                OperatingMode.HYBRID -> "Automatic switching"
                            },
                            selected = uiState.operatingMode == mode,
                            onClick = { viewModel.setOperatingMode(mode) }
                        )
                    }
                }
            }

            // AI Settings
            item {
                SettingsSection(
                    title = "AI Assistant",
                    icon = Icons.Filled.Psychology
                ) {
                    if (uiState.operatingMode != OperatingMode.OFFLINE) {
                        AiProvider.entries.filter { it != AiProvider.OFFLINE }.forEach { provider ->
                            RadioOption(
                                title = provider.name,
                                description = when (provider) {
                                    AiProvider.GEMINI -> "Google's latest AI model"
                                    AiProvider.CHATGPT -> "OpenAI's ChatGPT"
                                    else -> ""
                                },
                                selected = uiState.aiProvider == provider,
                                onClick = { viewModel.setAiProvider(provider) }
                            )
                        }
                    }
                    
                    SwitchOption(
                        title = "Wake Phrase",
                        description = "\"Hey RoadPilot\" voice activation",
                        checked = uiState.wakePhraseEnabled,
                        onCheckedChange = { viewModel.setWakePhraseEnabled(it) }
                    )
                }
            }

            // Recording Settings
            item {
                SettingsSection(
                    title = "Dashcam Recording",
                    icon = Icons.Filled.Videocam
                ) {
                    Text(
                        text = "Video Quality",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VideoQuality.entries.forEach { quality ->
                            FilterChip(
                                selected = uiState.videoQuality == quality,
                                onClick = { viewModel.setVideoQuality(quality) },
                                label = {
                                    Text(
                                        when (quality) {
                                            VideoQuality.HIGH -> "1080p"
                                            VideoQuality.MEDIUM -> "720p"
                                            VideoQuality.LOW -> "480p"
                                        }
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Loop Duration",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LoopDuration.entries.forEach { duration ->
                            FilterChip(
                                selected = uiState.loopDuration == duration,
                                onClick = { viewModel.setLoopDuration(duration) },
                                label = { Text("${duration.minutes}m") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    SwitchOption(
                        title = "Auto Start Recording",
                        description = "Start recording when app launches",
                        checked = uiState.autoStartRecording,
                        onCheckedChange = { viewModel.setAutoStartRecording(it) }
                    )
                    
                    SwitchOption(
                        title = "Parking Mode",
                        description = "Continue recording while parked",
                        checked = uiState.parkingModeEnabled,
                        onCheckedChange = { viewModel.setParkingModeEnabled(it) }
                    )
                }
            }

            // Safety Settings
            item {
                SettingsSection(
                    title = "Safety",
                    icon = Icons.Filled.Shield
                ) {
                    SwitchOption(
                        title = "Impact Detection",
                        description = "Automatically protect recording on impact",
                        checked = uiState.impactDetectionEnabled,
                        onCheckedChange = { viewModel.setImpactDetectionEnabled(it) }
                    )
                    
                    SwitchOption(
                        title = "Emergency SOS",
                        description = "Send alerts to emergency contacts",
                        checked = uiState.emergencySosEnabled,
                        onCheckedChange = { viewModel.setEmergencySosEnabled(it) }
                    )
                    
                    SwitchOption(
                        title = "Driver Fatigue Alert",
                        description = "Remind to take breaks during long drives",
                        checked = uiState.driverFatigueAlert,
                        onCheckedChange = { viewModel.setDriverFatigueAlert(it) }
                    )
                }
            }

            // General Settings
            item {
                SettingsSection(
                    title = "General",
                    icon = Icons.Filled.Settings
                ) {
                    SwitchOption(
                        title = "Dark Mode",
                        description = "Use dark theme",
                        checked = uiState.darkModeEnabled,
                        onCheckedChange = { viewModel.setDarkModeEnabled(it) }
                    )
                    
                    SwitchOption(
                        title = "Metric Units",
                        description = if (uiState.useMetricUnits) "Using km/h, km" else "Using mph, mi",
                        checked = uiState.useMetricUnits,
                        onCheckedChange = { viewModel.setUseMetricUnits(it) }
                    )
                }
            }

            // About
            item {
                SettingsSection(
                    title = "About",
                    icon = Icons.Filled.Info
                ) {
                    ListItem(
                        headlineContent = { Text("RoadPilot AI") },
                        supportingContent = { Text("Version 1.0.0") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.DirectionsCar,
                                contentDescription = null,
                                tint = PrimaryBlue
                            )
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
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

@Composable
fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = DashboardCard
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            content()
        }
    }
}

@Composable
fun SwitchOption(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun RadioOption(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
