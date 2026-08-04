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
public final class GetSafetySettingsUseCase_Factory implements Factory<GetSafetySettingsUseCase> {
  private final Provider<SafetyRepository> safetyRepositoryProvider;

  public GetSafetySettingsUseCase_Factory(Provider<SafetyRepository> safetyRepositoryProvider) {
    this.safetyRepositoryProvider = safetyRepositoryProvider;
  }

  @Override
  public GetSafetySettingsUseCase get() {
    return newInstance(safetyRepositoryProvider.get());
  }

  public static GetSafetySettingsUseCase_Factory create(
      Provider<SafetyRepository> safetyRepositoryProvider) {
    return new GetSafetySettingsUseCase_Factory(safetyRepositoryProvider);
  }

  public static GetSafetySettingsUseCase newInstance(SafetyRepository safetyRepository) {
    return new GetSafetySettingsUseCase(safetyRepository);
  }
}
