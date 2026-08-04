package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.RoadPilotDatabase;
import com.roadpilot.ai.data.local.database.VehicleInfoDao;
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
public final class AppModule_ProvideVehicleInfoDaoFactory implements Factory<VehicleInfoDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideVehicleInfoDaoFactory(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VehicleInfoDao get() {
    return provideVehicleInfoDao(databaseProvider.get());
  }

  public static AppModule_ProvideVehicleInfoDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideVehicleInfoDaoFactory(databaseProvider);
  }

  public static VehicleInfoDao provideVehicleInfoDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVehicleInfoDao(database));
  }
}
