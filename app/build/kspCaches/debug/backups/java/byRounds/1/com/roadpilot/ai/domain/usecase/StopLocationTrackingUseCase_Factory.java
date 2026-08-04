package com.roadpilot.ai.domain.usecase;

import com.roadpilot.ai.domain.repository.LocationRepository;
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
public final class StopLocationTrackingUseCase_Factory implements Factory<StopLocationTrackingUseCase> {
  private final Provider<LocationRepository> locationRepositoryProvider;

  public StopLocationTrackingUseCase_Factory(
      Provider<LocationRepository> locationRepositoryProvider) {
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public StopLocationTrackingUseCase get() {
    return newInstance(locationRepositoryProvider.get());
  }

  public static StopLocationTrackingUseCase_Factory create(
      Provider<LocationRepository> locationRepositoryProvider) {
    return new StopLocationTrackingUseCase_Factory(locationRepositoryProvider);
  }

  public static StopLocationTrackingUseCase newInstance(LocationRepository locationRepository) {
    return new StopLocationTrackingUseCase(locationRepository);
  }
}
