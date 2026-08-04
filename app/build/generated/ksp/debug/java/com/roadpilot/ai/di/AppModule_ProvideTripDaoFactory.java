package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.RoadPilotDatabase;
import com.roadpilot.ai.data.local.database.TripDao;
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
public final class AppModule_ProvideTripDaoFactory implements Factory<TripDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideTripDaoFactory(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TripDao get() {
    return provideTripDao(databaseProvider.get());
  }

  public static AppModule_ProvideTripDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideTripDaoFactory(databaseProvider);
  }

  public static TripDao provideTripDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTripDao(database));
  }
}
