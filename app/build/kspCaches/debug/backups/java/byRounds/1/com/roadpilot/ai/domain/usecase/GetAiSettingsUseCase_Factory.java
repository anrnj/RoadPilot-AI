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
public final class GetAiSettingsUseCase_Factory implements Factory<GetAiSettingsUseCase> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public GetAiSettingsUseCase_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public GetAiSettingsUseCase get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static GetAiSettingsUseCase_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new GetAiSettingsUseCase_Factory(settingsRepositoryProvider);
  }

  public static GetAiSettingsUseCase newInstance(SettingsRepository settingsRepository) {
    return new GetAiSettingsUseCase(settingsRepository);
  }
}
