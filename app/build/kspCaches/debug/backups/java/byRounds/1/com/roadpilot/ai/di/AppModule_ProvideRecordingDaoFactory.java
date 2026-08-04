package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.RecordingDao;
import com.roadpilot.ai.data.local.database.RoadPilotDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideRecordingDaoFactory implements Factory<RecordingDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideRecordingDaoFactory(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RecordingDao get() {
    return provideRecordingDao(databaseProvider.get());
  }

  public static AppModule_ProvideRecordingDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideRecordingDaoFactory(databaseProvider);
  }

  public static RecordingDao provideRecordingDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideRecordingDao(database));
  }
}
