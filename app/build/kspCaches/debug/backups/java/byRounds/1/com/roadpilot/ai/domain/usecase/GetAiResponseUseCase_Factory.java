package com.roadpilot.ai.domain.usecase;

import com.roadpilot.ai.domain.repository.AiRepository;
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
public final class GetAiResponseUseCase_Factory implements Factory<GetAiResponseUseCase> {
  private final Provider<AiRepository> aiRepositoryProvider;

  public GetAiResponseUseCase_Factory(Provider<AiRepository> aiRepositoryProvider) {
    this.aiRepositoryProvider = aiRepositoryProvider;
  }

  @Override
  public GetAiResponseUseCase get() {
    return newInstance(aiRepositoryProvider.get());
  }

  public static GetAiResponseUseCase_Factory create(Provider<AiRepository> aiRepositoryProvider) {
    return new GetAiResponseUseCase_Factory(aiRepositoryProvider);
  }

  public static GetAiResponseUseCase newInstance(AiRepository aiRepository) {
    return new GetAiResponseUseCase(aiRepository);
  }
}
