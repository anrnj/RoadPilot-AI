package com.roadpilot.ai.domain.usecase;

import com.roadpilot.ai.domain.repository.LocationRepository;
import com.roadpilot.ai.domain.repository.TripRepository;
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
public final class StartTripUseCase_Factory implements Factory<StartTripUseCase> {
  private final Provider<TripRepository> tripRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  public StartTripUseCase_Factory(Provider<TripRepository> tripRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    this.tripRepositoryProvider = tripRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public StartTripUseCase get() {
    return newInstance(tripRepositoryProvider.get(), locationRepositoryProvider.get());
  }

  public static StartTripUseCase_Factory create(Provider<TripRepository> tripRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    return new StartTripUseCase_Factory(tripRepositoryProvider, locationRepositoryProvider);
  }

  public static StartTripUseCase newInstance(TripRepository tripRepository,
      LocationRepository locationRepository) {
    return new StartTripUseCase(tripRepository, locationRepository);
  }
}
