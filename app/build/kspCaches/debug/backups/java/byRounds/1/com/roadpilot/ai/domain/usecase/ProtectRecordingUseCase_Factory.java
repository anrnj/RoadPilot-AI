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
public final class ProtectRecordingUseCase_Factory implements Factory<ProtectRecordingUseCase> {
  private final Provider<RecordingRepository> recordingRepositoryProvider;

  public ProtectRecordingUseCase_Factory(
      Provider<RecordingRepository> recordingRepositoryProvider) {
    this.recordingRepositoryProvider = recordingRepositoryProvider;
  }

  @Override
  public ProtectRecordingUseCase get() {
    return newInstance(recordingRepositoryProvider.get());
  }

  public static ProtectRecordingUseCase_Factory create(
      Provider<RecordingRepository> recordingRepositoryProvider) {
    return new ProtectRecordingUseCase_Factory(recordingRepositoryProvider);
  }

  public static ProtectRecordingUseCase newInstance(RecordingRepository recordingRepository) {
    return new ProtectRecordingUseCase(recordingRepository);
  }
}
