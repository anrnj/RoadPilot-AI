package com.roadpilot.ai.receiver;

import com.roadpilot.ai.data.local.database.RoadPilotDatabase;
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
public final class ImpactDetectionReceiver_MembersInjector implements MembersInjector<ImpactDetectionReceiver> {
  private final Provider<RoadPilotDatabase> databaseProvider;

  public ImpactDetectionReceiver_MembersInjector(Provider<RoadPilotDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  public static MembersInjector<ImpactDetectionReceiver> create(
      Provider<RoadPilotDatabase> databaseProvider) {
    return new ImpactDetectionReceiver_MembersInjector(databaseProvider);
  }

  @Override
  public void injectMembers(ImpactDetectionReceiver instance) {
    injectDatabase(instance, databaseProvider.get());
  }

  @InjectedFieldSignature("com.roadpilot.ai.receiver.ImpactDetectionReceiver.database")
  public static void injectDatabase(ImpactDetectionReceiver instance, RoadPilotDatabase database) {
    instance.database = database;
  }
}
