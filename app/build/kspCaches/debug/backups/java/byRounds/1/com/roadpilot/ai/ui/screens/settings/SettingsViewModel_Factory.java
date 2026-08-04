package com.roadpilot.ai.ui.screens.settings;

import com.roadpilot.ai.domain.repository.SafetyRepository;
import com.roadpilot.ai.domain.repository.SettingsRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<SafetyRepository> safetyRepositoryProvider;

  public SettingsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SafetyRepository> safetyRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.safetyRepositoryProvider = safetyRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), safetyRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SafetyRepository> safetyRepositoryProvider) {
    return new SettingsViewModel_Factory(settingsRepositoryProvider, safetyRepositoryProvider);
  }

  public static SettingsViewModel newInstance(SettingsRepository settingsRepository,
      SafetyRepository safetyRepository) {
    return new SettingsViewModel(settingsRepository, safetyRepository);
  }
}
