package com.roadpilot.ai.ui.screens.ai;

import android.content.Context;
import com.roadpilot.ai.domain.repository.AiRepository;
import com.roadpilot.ai.domain.repository.LocationRepository;
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
public final class AiViewModel_Factory implements Factory<AiViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<AiRepository> aiRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  public AiViewModel_Factory(Provider<Context> contextProvider,
      Provider<AiRepository> aiRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.aiRepositoryProvider = aiRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public AiViewModel get() {
    return newInstance(contextProvider.get(), aiRepositoryProvider.get(), settingsRepositoryProvider.get(), locationRepositoryProvider.get());
  }

  public static AiViewModel_Factory create(Provider<Context> contextProvider,
      Provider<AiRepository> aiRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    return new AiViewModel_Factory(contextProvider, aiRepositoryProvider, settingsRepositoryProvider, locationRepositoryProvider);
  }

  public static AiViewModel newInstance(Context context, AiRepository aiRepository,
      SettingsRepository settingsRepository, LocationRepository locationRepository) {
    return new AiViewModel(context, aiRepository, settingsRepository, locationRepository);
  }
}
