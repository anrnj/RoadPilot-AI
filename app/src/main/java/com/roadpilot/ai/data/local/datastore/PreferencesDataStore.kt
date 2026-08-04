package com.roadpilot.ai.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.roadpilot.ai.domain.model.AiProvider
import com.roadpilot.ai.domain.model.LoopDuration
import com.roadpilot.ai.domain.model.OperatingMode
import com.roadpilot.ai.domain.model.VideoQuality
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "roadpilot_preferences")

@Singleton
class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val OPERATING_MODE = stringPreferencesKey("operating_mode")
        val VIDEO_QUALITY = stringPreferencesKey("video_quality")
        val LOOP_DURATION = stringPreferencesKey("loop_duration")
        val AI_PROVIDER = stringPreferencesKey("ai_provider")
        val USE_METRIC_UNITS = booleanPreferencesKey("use_metric_units")
        val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
        val WAKE_PHRASE_ENABLED = booleanPreferencesKey("wake_phrase_enabled")
        val AUTO_START_RECORDING = booleanPreferencesKey("auto_start_recording")
        val PARKING_MODE_ENABLED = booleanPreferencesKey("parking_mode_enabled")
        val HOME_LOCATION_LAT = doublePreferencesKey("home_location_lat")
        val HOME_LOCATION_LNG = doublePreferencesKey("home_location_lng")
        val OFFICE_LOCATION_LAT = doublePreferencesKey("office_location_lat")
        val OFFICE_LOCATION_LNG = doublePreferencesKey("office_location_lng")
        val IMPACT_DETECTION_ENABLED = booleanPreferencesKey("impact_detection_enabled")
        val IMPACT_SENSITIVITY = floatPreferencesKey("impact_sensitivity")
        val EMERGENCY_SOS_ENABLED = booleanPreferencesKey("emergency_sos_enabled")
        val DRIVER_FATIGUE_ALERT = booleanPreferencesKey("driver_fatigue_alert")
        val FATIGUE_ALERT_HOURS = floatPreferencesKey("fatigue_alert_hours")
    }

    val operatingMode: Flow<OperatingMode> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs ->
            prefs[PreferencesKeys.OPERATING_MODE]?.let { OperatingMode.valueOf(it) } ?: OperatingMode.HYBRID
        }

    val videoQuality: Flow<VideoQuality> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs ->
            prefs[PreferencesKeys.VIDEO_QUALITY]?.let { VideoQuality.valueOf(it) } ?: VideoQuality.HIGH
        }

    val loopDuration: Flow<LoopDuration> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs ->
            prefs[PreferencesKeys.LOOP_DURATION]?.let { LoopDuration.valueOf(it) } ?: LoopDuration.THREE
        }

    val aiProvider: Flow<AiProvider> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs ->
            prefs[PreferencesKeys.AI_PROVIDER]?.let { AiProvider.valueOf(it) } ?: AiProvider.GEMINI
        }

    val useMetricUnits: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.USE_METRIC_UNITS] ?: true }

    val darkModeEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.DARK_MODE_ENABLED] ?: true }

    val wakePhraseEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.WAKE_PHRASE_ENABLED] ?: true }

    val autoStartRecording: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.AUTO_START_RECORDING] ?: false }

    val parkingModeEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.PARKING_MODE_ENABLED] ?: false }

    val impactDetectionEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.IMPACT_DETECTION_ENABLED] ?: true }

    val impactSensitivity: Flow<Float> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.IMPACT_SENSITIVITY] ?: 0.5f }

    val emergencySosEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.EMERGENCY_SOS_ENABLED] ?: true }

    val driverFatigueAlert: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.DRIVER_FATIGUE_ALERT] ?: true }

    val fatigueAlertHours: Flow<Float> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[PreferencesKeys.FATIGUE_ALERT_HOURS] ?: 2f }

    suspend fun setOperatingMode(mode: OperatingMode) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.OPERATING_MODE] = mode.name
        }
    }

    suspend fun setVideoQuality(quality: VideoQuality) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.VIDEO_QUALITY] = quality.name
        }
    }

    suspend fun setLoopDuration(duration: LoopDuration) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.LOOP_DURATION] = duration.name
        }
    }

    suspend fun setAiProvider(provider: AiProvider) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.AI_PROVIDER] = provider.name
        }
    }

    suspend fun setUseMetricUnits(useMetric: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.USE_METRIC_UNITS] = useMetric
        }
    }

    suspend fun setDarkModeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.DARK_MODE_ENABLED] = enabled
        }
    }

    suspend fun setWakePhraseEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.WAKE_PHRASE_ENABLED] = enabled
        }
    }

    suspend fun setAutoStartRecording(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.AUTO_START_RECORDING] = enabled
        }
    }

    suspend fun setParkingModeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.PARKING_MODE_ENABLED] = enabled
        }
    }

    suspend fun setImpactDetectionEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.IMPACT_DETECTION_ENABLED] = enabled
        }
    }

    suspend fun setImpactSensitivity(sensitivity: Float) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.IMPACT_SENSITIVITY] = sensitivity
        }
    }

    suspend fun setEmergencySosEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.EMERGENCY_SOS_ENABLED] = enabled
        }
    }

    suspend fun setDriverFatigueAlert(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.DRIVER_FATIGUE_ALERT] = enabled
        }
    }

    suspend fun setFatigueAlertHours(hours: Float) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.FATIGUE_ALERT_HOURS] = hours
        }
    }
}
