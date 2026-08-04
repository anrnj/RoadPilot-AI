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
public final class EndTripUseCase_Factory implements Factory<EndTripUseCase> {
  private final Provider<TripRepository> tripRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  public EndTripUseCase_Factory(Provider<TripRepository> tripRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    this.tripRepositoryProvider = tripRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public EndTripUseCase get() {
    return newInstance(tripRepositoryProvider.get(), locationRepositoryProvider.get());
  }

  public static EndTripUseCase_Factory create(Provider<TripRepository> tripRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    return new EndTripUseCase_Factory(tripRepositoryProvider, locationRepositoryProvider);
  }

  public static EndTripUseCase newInstance(TripRepository tripRepository,
      LocationRepository locationRepository) {
    return new EndTripUseCase(tripRepository, locationRepository);
  }
}
