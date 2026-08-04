package com.roadpilot.ai.service;

import com.google.android.gms.location.FusedLocationProviderClient;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class LocationService_MembersInjector implements MembersInjector<LocationService> {
  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  public LocationService_MembersInjector(
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    this.fusedLocationClientProvider = fusedLocationClientProvider;
  }

  public static MembersInjector<LocationService> create(
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    return new LocationService_MembersInjector(fusedLocationClientProvider);
  }

  @Override
  public void injectMembers(LocationService instance) {
    injectFusedLocationClient(instance, fusedLocationClientProvider.get());
  }

  @InjectedFieldSignature("com.roadpilot.ai.service.LocationService.fusedLocationClient")
  public static void injectFusedLocationClient(LocationService instance,
      FusedLocationProviderClient fusedLocationClient) {
    instance.fusedLocationClient = fusedLocationClient;
  }
}
