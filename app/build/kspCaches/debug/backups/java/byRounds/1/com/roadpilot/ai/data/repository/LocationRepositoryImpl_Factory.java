package com.roadpilot.ai.data.repository;

import android.content.Context;
import com.google.android.gms.location.FusedLocationProviderClient;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class LocationRepositoryImpl_Factory implements Factory<LocationRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  public LocationRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    this.contextProvider = contextProvider;
    this.fusedLocationClientProvider = fusedLocationClientProvider;
  }

  @Override
  public LocationRepositoryImpl get() {
    return newInstance(contextProvider.get(), fusedLocationClientProvider.get());
  }

  public static LocationRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    return new LocationRepositoryImpl_Factory(contextProvider, fusedLocationClientProvider);
  }

  public static LocationRepositoryImpl newInstance(Context context,
      FusedLocationProviderClient fusedLocationClient) {
    return new LocationRepositoryImpl(context, fusedLocationClient);
  }
}
