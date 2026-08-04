package com.roadpilot.ai.di;

import com.roadpilot.ai.data.local.database.AiConversationDao;
import com.roadpilot.ai.data.local.database.RoadPilotDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideAiConversationDaoFactory implements Factory<AiConversationDao> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public AppModule_ProvideAiConversationDaoFactory(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AiConversationDao get() {
    return provideAiConversationDao(databaseProvider.get());
  }

  public static AppModule_ProvideAiConversationDaoFactory create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new AppModule_ProvideAiConversationDaoFactory(databaseProvider);
  }

  public static AiConversationDao provideAiConversationDao(RoadPilotDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAiConversationDao(database));
  }
}
