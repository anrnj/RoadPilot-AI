package com.roadpilot.ai.data.repository;

import android.content.Context;
import com.roadpilot.ai.data.local.database.RecordingDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class RecordingRepositoryImpl_Factory implements Factory<RecordingRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<RecordingDao> recordingDaoProvider;

  public RecordingRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<RecordingDao> recordingDaoProvider) {
    this.contextProvider = contextProvider;
    this.recordingDaoProvider = recordingDaoProvider;
  }

  @Override
  public RecordingRepositoryImpl get() {
    return newInstance(contextProvider.get(), recordingDaoProvider.get());
  }

  public static RecordingRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<RecordingDao> recordingDaoProvider) {
    return new RecordingRepositoryImpl_Factory(contextProvider, recordingDaoProvider);
  }

  public static RecordingRepositoryImpl newInstance(Context context, RecordingDao recordingDao) {
    return new RecordingRepositoryImpl(context, recordingDao);
  }
}
