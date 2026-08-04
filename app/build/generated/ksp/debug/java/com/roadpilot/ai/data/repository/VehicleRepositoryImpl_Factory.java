package com.roadpilot.ai.data.repository;

import com.roadpilot.ai.data.local.database.FuelLogDao;
import com.roadpilot.ai.data.local.database.MaintenanceReminderDao;
import com.roadpilot.ai.data.local.database.VehicleInfoDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class VehicleRepositoryImpl_Factory implements Factory<VehicleRepositoryImpl> {
  private final Provider<VehicleInfoDao> vehicleInfoDaoProvider;

  private final Provider<FuelLogDao> fuelLogDaoProvider;

  private final Provider<MaintenanceReminderDao> maintenanceReminderDaoProvider;

  public VehicleRepositoryImpl_Factory(Provider<VehicleInfoDao> vehicleInfoDaoProvider,
      Provider<FuelLogDao> fuelLogDaoProvider,
      Provider<MaintenanceReminderDao> maintenanceReminderDaoProvider) {
    this.vehicleInfoDaoProvider = vehicleInfoDaoProvider;
    this.fuelLogDaoProvider = fuelLogDaoProvider;
    this.maintenanceReminderDaoProvider = maintenanceReminderDaoProvider;
  }

  @Override
  public VehicleRepositoryImpl get() {
    return newInstance(vehicleInfoDaoProvider.get(), fuelLogDaoProvider.get(), maintenanceReminderDaoProvider.get());
  }

  public static VehicleRepositoryImpl_Factory create(
      Provider<VehicleInfoDao> vehicleInfoDaoProvider, Provider<FuelLogDao> fuelLogDaoProvider,
      Provider<MaintenanceReminderDao> maintenanceReminderDaoProvider) {
    return new VehicleRepositoryImpl_Factory(vehicleInfoDaoProvider, fuelLogDaoProvider, maintenanceReminderDaoProvider);
  }

  public static VehicleRepositoryImpl newInstance(VehicleInfoDao vehicleInfoDao,
      FuelLogDao fuelLogDao, MaintenanceReminderDao maintenanceReminderDao) {
    return new VehicleRepositoryImpl(vehicleInfoDao, fuelLogDao, maintenanceReminderDao);
  }
}
