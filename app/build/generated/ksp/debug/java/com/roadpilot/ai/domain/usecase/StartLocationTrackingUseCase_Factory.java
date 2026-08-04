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
public final class StartLocationTrackingUseCase_Factory implements Factory<StartLocationTrackingUseCase> {
  private final Provider<LocationRepository> locationRepositoryProvider;

  public StartLocationTrackingUseCase_Factory(
      Provider<LocationRepository> locationRepositoryProvider) {
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public StartLocationTrackingUseCase get() {
    return newInstance(locationRepositoryProvider.get());
  }

  public static StartLocationTrackingUseCase_Factory create(
      Provider<LocationRepository> locationRepositoryProvider) {
    return new StartLocationTrackingUseCase_Factory(locationRepositoryProvider);
  }

  public static StartLocationTrackingUseCase newInstance(LocationRepository locationRepository) {
    return new StartLocationTrackingUseCase(locationRepository);
  }
}
