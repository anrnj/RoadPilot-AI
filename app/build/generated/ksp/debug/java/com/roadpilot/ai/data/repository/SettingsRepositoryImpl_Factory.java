package com.roadpilot.ai.data.repository;

import com.roadpilot.ai.data.local.datastore.PreferencesDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SettingsRepositoryImpl_Factory implements Factory<SettingsRepositoryImpl> {
  private final Provider<PreferencesDataStore> preferencesDataStoreProvider;

  public SettingsRepositoryImpl_Factory(
      Provider<PreferencesDataStore> preferencesDataStoreProvider) {
    this.preferencesDataStoreProvider = preferencesDataStoreProvider;
  }

  @Override
  public SettingsRepositoryImpl get() {
    return newInstance(preferencesDataStoreProvider.get());
  }

  public static SettingsRepositoryImpl_Factory create(
      Provider<PreferencesDataStore> preferencesDataStoreProvider) {
    return new SettingsRepositoryImpl_Factory(preferencesDataStoreProvider);
  }

  public static SettingsRepositoryImpl newInstance(PreferencesDataStore preferencesDataStore) {
    return new SettingsRepositoryImpl(preferencesDataStore);
  }
}
