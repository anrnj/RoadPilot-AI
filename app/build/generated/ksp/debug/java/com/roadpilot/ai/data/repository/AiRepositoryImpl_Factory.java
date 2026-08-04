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
public final class AiRepositoryImpl_Factory implements Factory<AiRepositoryImpl> {
  private final Provider<PreferencesDataStore> preferencesDataStoreProvider;

  public AiRepositoryImpl_Factory(Provider<PreferencesDataStore> preferencesDataStoreProvider) {
    this.preferencesDataStoreProvider = preferencesDataStoreProvider;
  }

  @Override
  public AiRepositoryImpl get() {
    return newInstance(preferencesDataStoreProvider.get());
  }

  public static AiRepositoryImpl_Factory create(
      Provider<PreferencesDataStore> preferencesDataStoreProvider) {
    return new AiRepositoryImpl_Factory(preferencesDataStoreProvider);
  }

  public static AiRepositoryImpl newInstance(PreferencesDataStore preferencesDataStore) {
    return new AiRepositoryImpl(preferencesDataStore);
  }
}
