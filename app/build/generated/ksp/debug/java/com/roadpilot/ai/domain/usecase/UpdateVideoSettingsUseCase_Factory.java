package com.roadpilot.ai.domain.usecase;

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
public final class UpdateVideoSettingsUseCase_Factory implements Factory<UpdateVideoSettingsUseCase> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public UpdateVideoSettingsUseCase_Factory(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public UpdateVideoSettingsUseCase get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static UpdateVideoSettingsUseCase_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new UpdateVideoSettingsUseCase_Factory(settingsRepositoryProvider);
  }

  public static UpdateVideoSettingsUseCase newInstance(SettingsRepository settingsRepository) {
    return new UpdateVideoSettingsUseCase(settingsRepository);
  }
}
