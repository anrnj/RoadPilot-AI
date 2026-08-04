package com.roadpilot.ai.domain.usecase;

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
public final class GetTripsUseCase_Factory implements Factory<GetTripsUseCase> {
  private final Provider<TripRepository> tripRepositoryProvider;

  public GetTripsUseCase_Factory(Provider<TripRepository> tripRepositoryProvider) {
    this.tripRepositoryProvider = tripRepositoryProvider;
  }

  @Override
  public GetTripsUseCase get() {
    return newInstance(tripRepositoryProvider.get());
  }

  public static GetTripsUseCase_Factory create(Provider<TripRepository> tripRepositoryProvider) {
    return new GetTripsUseCase_Factory(tripRepositoryProvider);
  }

  public static GetTripsUseCase newInstance(TripRepository tripRepository) {
    return new GetTripsUseCase(tripRepository);
  }
}
