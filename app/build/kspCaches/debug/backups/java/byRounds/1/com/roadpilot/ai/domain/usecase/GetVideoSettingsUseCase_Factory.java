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
public final class GetVideoSettingsUseCase_Factory implements Factory<GetVideoSettingsUseCase> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public GetVideoSettingsUseCase_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public GetVideoSettingsUseCase get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static GetVideoSettingsUseCase_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new GetVideoSettingsUseCase_Factory(settingsRepositoryProvider);
  }

  public static GetVideoSettingsUseCase newInstance(SettingsRepository settingsRepository) {
    return new GetVideoSettingsUseCase(settingsRepository);
  }
}
