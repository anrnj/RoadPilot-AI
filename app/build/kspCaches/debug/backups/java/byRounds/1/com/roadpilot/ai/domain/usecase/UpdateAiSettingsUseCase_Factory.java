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
public final class UpdateAiSettingsUseCase_Factory implements Factory<UpdateAiSettingsUseCase> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public UpdateAiSettingsUseCase_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public UpdateAiSettingsUseCase get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static UpdateAiSettingsUseCase_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new UpdateAiSettingsUseCase_Factory(settingsRepositoryProvider);
  }

  public static UpdateAiSettingsUseCase newInstance(SettingsRepository settingsRepository) {
    return new UpdateAiSettingsUseCase(settingsRepository);
  }
}
