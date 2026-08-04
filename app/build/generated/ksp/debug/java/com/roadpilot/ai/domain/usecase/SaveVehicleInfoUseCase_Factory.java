package com.roadpilot.ai.domain.usecase;

import com.roadpilot.ai.domain.repository.VehicleRepository;
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
public final class SaveVehicleInfoUseCase_Factory implements Factory<SaveVehicleInfoUseCase> {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  public SaveVehicleInfoUseCase_Factory(Provider<VehicleRepository> vehicleRepositoryProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
  }

  @Override
  public SaveVehicleInfoUseCase get() {
    return newInstance(vehicleRepositoryProvider.get());
  }

  public static SaveVehicleInfoUseCase_Factory create(
      Provider<VehicleRepository> vehicleRepositoryProvider) {
    return new SaveVehicleInfoUseCase_Factory(vehicleRepositoryProvider);
  }

  public static SaveVehicleInfoUseCase newInstance(VehicleRepository vehicleRepository) {
    return new SaveVehicleInfoUseCase(vehicleRepository);
  }
}
