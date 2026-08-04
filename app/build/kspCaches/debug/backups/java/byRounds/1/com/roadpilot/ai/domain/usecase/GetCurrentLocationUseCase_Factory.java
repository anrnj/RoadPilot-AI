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
public final class GetCurrentLocationUseCase_Factory implements Factory<GetCurrentLocationUseCase> {
  private final Provider<LocationRepository> locationRepositoryProvider;

  public GetCurrentLocationUseCase_Factory(
      Provider<LocationRepository> locationRepositoryProvider) {
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public GetCurrentLocationUseCase get() {
    return newInstance(locationRepositoryProvider.get());
  }

  public static GetCurrentLocationUseCase_Factory create(
      Provider<LocationRepository> locationRepositoryProvider) {
    return new GetCurrentLocationUseCase_Factory(locationRepositoryProvider);
  }

  public static GetCurrentLocationUseCase newInstance(LocationRepository locationRepository) {
    return new GetCurrentLocationUseCase(locationRepository);
  }
}
