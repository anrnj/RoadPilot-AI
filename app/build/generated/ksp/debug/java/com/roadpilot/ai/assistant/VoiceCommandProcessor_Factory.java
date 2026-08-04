package com.roadpilot.ai.assistant;

import android.content.Context;
import com.roadpilot.ai.domain.repository.AiRepository;
import com.roadpilot.ai.domain.repository.LocationRepository;
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
public final class VoiceCommandProcessor_Factory implements Factory<VoiceCommandProcessor> {
  private final Provider<Context> contextProvider;

  private final Provider<AiRepository> aiRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  public VoiceCommandProcessor_Factory(Provider<Context> contextProvider,
      Provider<AiRepository> aiRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.aiRepositoryProvider = aiRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public VoiceCommandProcessor get() {
    return newInstance(contextProvider.get(), aiRepositoryProvider.get(), locationRepositoryProvider.get());
  }

  public static VoiceCommandProcessor_Factory create(Provider<Context> contextProvider,
      Provider<AiRepository> aiRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    return new VoiceCommandProcessor_Factory(contextProvider, aiRepositoryProvider, locationRepositoryProvider);
  }

  public static VoiceCommandProcessor newInstance(Context context, AiRepository aiRepository,
      LocationRepository locationRepository) {
    return new VoiceCommandProcessor(context, aiRepository, locationRepository);
  }
}
