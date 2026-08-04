package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.FuelLogDao;
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
public final class AppModule_ProvideFuelLogDaoFactory implements Factory<FuelLogDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideFuelLogDaoFactory(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public FuelLogDao get() {
    return provideFuelLogDao(databaseProvider.get());
  }

  public static AppModule_ProvideFuelLogDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideFuelLogDaoFactory(databaseProvider);
  }

  public static FuelLogDao provideFuelLogDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFuelLogDao(database));
  }
}
