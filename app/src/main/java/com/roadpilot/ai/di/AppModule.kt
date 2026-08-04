package com.roadpilot.ai.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.roadpilot.ai.data.local.database.*
import com.roadpilot.ai.data.repository.*
import com.roadpilot.ai.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoadPilotDatabase {
        return Room.databaseBuilder(
            context,
            RoadPilotDatabase::class.java,
            RoadPilotDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecordingDao(database: RoadPilotDatabase): RecordingDao {
        return database.recordingDao()
    }

    @Provides
    @Singleton
    fun provideTripDao(database: RoadPilotDatabase): TripDao {
        return database.tripDao()
    }

    @Provides
    @Singleton
    fun provideEmergencyContactDao(database: RoadPilotDatabase): EmergencyContactDao {
        return database.emergencyContactDao()
    }

    @Provides
    @Singleton
    fun provideFuelLogDao(database: RoadPilotDatabase): FuelLogDao {
        return database.fuelLogDao()
    }

    @Provides
    @Singleton
    fun provideMaintenanceReminderDao(database: RoadPilotDatabase): MaintenanceReminderDao {
        return database.maintenanceReminderDao()
    }

    @Provides
    @Singleton
    fun provideVehicleInfoDao(database: RoadPilotDatabase): VehicleInfoDao {
        return database.vehicleInfoDao()
    }

    @Provides
    @Singleton
    fun provideAiConversationDao(database: RoadPilotDatabase): AiConversationDao {
        return database.aiConversationDao()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindRecordingRepository(impl: RecordingRepositoryImpl): RecordingRepository

    @Binds
    @Singleton
    abstract fun bindTripRepository(impl: TripRepositoryImpl): TripRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindVehicleRepository(impl: VehicleRepositoryImpl): VehicleRepository

    @Binds
    @Singleton
    abstract fun bindSafetyRepository(impl: SafetyRepositoryImpl): SafetyRepository

    @Binds
    @Singleton
    abstract fun bindAiRepository(impl: AiRepositoryImpl): AiRepository
}
