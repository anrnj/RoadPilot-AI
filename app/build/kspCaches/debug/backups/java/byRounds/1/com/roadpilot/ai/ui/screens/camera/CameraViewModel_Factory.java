package com.roadpilot.ai.ui.screens.camera;

import android.content.Context;
import com.roadpilot.ai.domain.repository.LocationRepository;
import com.roadpilot.ai.domain.repository.RecordingRepository;
import com.roadpilot.ai.domain.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class CameraViewModel_Factory implements Factory<CameraViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<RecordingRepository> recordingRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  public CameraViewModel_Factory(Provider<Context> contextProvider,
      Provider<RecordingRepository> recordingRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.recordingRepositoryProvider = recordingRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public CameraViewModel get() {
    return newInstance(contextProvider.get(), recordingRepositoryProvider.get(), settingsRepositoryProvider.get(), locationRepositoryProvider.get());
  }

  public static CameraViewModel_Factory create(Provider<Context> contextProvider,
      Provider<RecordingRepository> recordingRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    return new CameraViewModel_Factory(contextProvider, recordingRepositoryProvider, settingsRepositoryProvider, locationRepositoryProvider);
  }

  public static CameraViewModel newInstance(Context context,
      RecordingRepository recordingRepository, SettingsRepository settingsRepository,
      LocationRepository locationRepository) {
    return new CameraViewModel(context, recordingRepository, settingsRepository, locationRepository);
  }
}
