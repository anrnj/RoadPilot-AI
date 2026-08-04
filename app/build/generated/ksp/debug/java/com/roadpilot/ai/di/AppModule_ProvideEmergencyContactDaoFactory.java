package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.EmergencyContactDao;
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
public final class AppModule_ProvideEmergencyContactDaoFactory implements Factory<EmergencyContactDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideEmergencyContactDaoFactory(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public EmergencyContactDao get() {
    return provideEmergencyContactDao(databaseProvider.get());
  }

  public static AppModule_ProvideEmergencyContactDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideEmergencyContactDaoFactory(databaseProvider);
  }

  public static EmergencyContactDao provideEmergencyContactDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEmergencyContactDao(database));
  }
}
