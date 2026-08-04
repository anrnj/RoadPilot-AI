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
public final class DeleteRecordingUseCase_Factory implements Factory<DeleteRecordingUseCase> {
  private final Provider<RecordingRepository> recordingRepositoryProvider;

  public DeleteRecordingUseCase_Factory(Provider<RecordingRepository> recordingRepositoryProvider) {
    this.recordingRepositoryProvider = recordingRepositoryProvider;
  }

  @Override
  public DeleteRecordingUseCase get() {
    return newInstance(recordingRepositoryProvider.get());
  }

  public static DeleteRecordingUseCase_Factory create(
      Provider<RecordingRepository> recordingRepositoryProvider) {
    return new DeleteRecordingUseCase_Factory(recordingRepositoryProvider);
  }

  public static DeleteRecordingUseCase newInstance(RecordingRepository recordingRepository) {
    return new DeleteRecordingUseCase(recordingRepository);
  }
}
