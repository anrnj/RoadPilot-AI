package com.roadpilot.ai.ui.screens.drive;

import com.roadpilot.ai.domain.repository.AiRepository;
import com.roadpilot.ai.domain.repository.LocationRepository;
import com.roadpilot.ai.domain.repository.SettingsRepository;
import com.roadpilot.ai.domain.repository.TripRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DriveViewModel_Factory implements Factory<DriveViewModel> {
  private final Provider<LocationRepository> locationRepositoryProvider;

  private final Provider<TripRepository> tripRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<AiRepository> aiRepositoryProvider;

  public DriveViewModel_Factory(Provider<LocationRepository> locationRepositoryProvider,
      Provider<TripRepository> tripRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<AiRepository> aiRepositoryProvider) {
    this.locationRepositoryProvider = locationRepositoryProvider;
    this.tripRepositoryProvider = tripRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.aiRepositoryProvider = aiRepositoryProvider;
  }

  @Override
  public DriveViewModel get() {
    return newInstance(locationRepositoryProvider.get(), tripRepositoryProvider.get(), settingsRepositoryProvider.get(), aiRepositoryProvider.get());
  }

  public static DriveViewModel_Factory create(
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<TripRepository> tripRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<AiRepository> aiRepositoryProvider) {
    return new DriveViewModel_Factory(locationRepositoryProvider, tripRepositoryProvider, settingsRepositoryProvider, aiRepositoryProvider);
  }

  public static DriveViewModel newInstance(LocationRepository locationRepository,
      TripRepository tripRepository, SettingsRepository settingsRepository,
      AiRepository aiRepository) {
    return new DriveViewModel(locationRepository, tripRepository, settingsRepository, aiRepository);
  }
}
