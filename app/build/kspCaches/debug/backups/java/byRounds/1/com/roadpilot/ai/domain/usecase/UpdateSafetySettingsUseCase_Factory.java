package com.roadpilot.ai.domain.usecase;

import com.roadpilot.ai.domain.repository.SafetyRepository;
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
public final class UpdateSafetySettingsUseCase_Factory implements Factory<UpdateSafetySettingsUseCase> {
  private final Provider<SafetyRepository> safetyRepositoryProvider;

  public UpdateSafetySettingsUseCase_Factory(Provider<SafetyRepository> safetyRepositoryProvider) {
    this.safetyRepositoryProvider = safetyRepositoryProvider;
  }

  @Override
  public UpdateSafetySettingsUseCase get() {
    return newInstance(safetyRepositoryProvider.get());
  }

  public static UpdateSafetySettingsUseCase_Factory create(
      Provider<SafetyRepository> safetyRepositoryProvider) {
    return new UpdateSafetySettingsUseCase_Factory(safetyRepositoryProvider);
  }

  public static UpdateSafetySettingsUseCase newInstance(SafetyRepository safetyRepository) {
    return new UpdateSafetySettingsUseCase(safetyRepository);
  }
}
