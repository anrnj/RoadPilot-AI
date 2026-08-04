package com.roadpilot.ai.domain.usecase;

import com.roadpilot.ai.domain.repository.RecordingRepository;
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
public final class GetRecordingsUseCase_Factory implements Factory<GetRecordingsUseCase> {
  private final Provider<RecordingRepository> recordingRepositoryProvider;

  public GetRecordingsUseCase_Factory(Provider<RecordingRepository> recordingRepositoryProvider) {
    this.recordingRepositoryProvider = recordingRepositoryProvider;
  }

  @Override
  public GetRecordingsUseCase get() {
    return newInstance(recordingRepositoryProvider.get());
  }

  public static GetRecordingsUseCase_Factory create(
      Provider<RecordingRepository> recordingRepositoryProvider) {
    return new GetRecordingsUseCase_Factory(recordingRepositoryProvider);
  }

  public static GetRecordingsUseCase newInstance(RecordingRepository recordingRepository) {
    return new GetRecordingsUseCase(recordingRepository);
  }
}
