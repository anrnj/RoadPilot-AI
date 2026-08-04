package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.MaintenanceReminderDao;
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
public final class AppModule_ProvideMaintenanceReminderDaoFactory implements Factory<MaintenanceReminderDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideMaintenanceReminderDaoFactory(
      Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MaintenanceReminderDao get() {
    return provideMaintenanceReminderDao(databaseProvider.get());
  }

  public static AppModule_ProvideMaintenanceReminderDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideMaintenanceReminderDaoFactory(databaseProvider);
  }

  public static MaintenanceReminderDao provideMaintenanceReminderDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMaintenanceReminderDao(database));
  }
}
