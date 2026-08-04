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
public final class GetVehicleInfoUseCase_Factory implements Factory<GetVehicleInfoUseCase> {
  private final Provider<VehicleRepository> vehicleRepositoryProvider;

  public GetVehicleInfoUseCase_Factory(Provider<VehicleRepository> vehicleRepositoryProvider) {
    this.vehicleRepositoryProvider = vehicleRepositoryProvider;
  }

  @Override
  public GetVehicleInfoUseCase get() {
    return newInstance(vehicleRepositoryProvider.get());
  }

  public static GetVehicleInfoUseCase_Factory create(
      Provider<VehicleRepository> vehicleRepositoryProvider) {
    return new GetVehicleInfoUseCase_Factory(vehicleRepositoryProvider);
  }

  public static GetVehicleInfoUseCase newInstance(VehicleRepository vehicleRepository) {
    return new GetVehicleInfoUseCase(vehicleRepository);
  }
}
