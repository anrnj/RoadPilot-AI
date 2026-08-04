package com.roadpilot.ai.data.repository;

import com.roadpilot.ai.data.local.database.EmergencyContactDao;
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
public final class SafetyRepositoryImpl_Factory implements Factory<SafetyRepositoryImpl> {
  private final Provider<EmergencyContactDao> emergencyContactDaoProvider;

  private final Provider<PreferencesDataStore> preferencesDataStoreProvider;

  public SafetyRepositoryImpl_Factory(Provider<EmergencyContactDao> emergencyContactDaoProvider,
      Provider<PreferencesDataStore> preferencesDataStoreProvider) {
    this.emergencyContactDaoProvider = emergencyContactDaoProvider;
    this.preferencesDataStoreProvider = preferencesDataStoreProvider;
  }

  @Override
  public SafetyRepositoryImpl get() {
    return newInstance(emergencyContactDaoProvider.get(), preferencesDataStoreProvider.get());
  }

  public static SafetyRepositoryImpl_Factory create(
      Provider<EmergencyContactDao> emergencyContactDaoProvider,
      Provider<PreferencesDataStore> preferencesDataStoreProvider) {
    return new SafetyRepositoryImpl_Factory(emergencyContactDaoProvider, preferencesDataStoreProvider);
  }

  public static SafetyRepositoryImpl newInstance(EmergencyContactDao emergencyContactDao,
      PreferencesDataStore preferencesDataStore) {
    return new SafetyRepositoryImpl(emergencyContactDao, preferencesDataStore);
  }
}
