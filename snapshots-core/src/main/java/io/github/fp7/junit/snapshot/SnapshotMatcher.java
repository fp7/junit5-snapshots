package io.github.fp7.junit.snapshot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SnapshotMatcher {

  private final String testName;

  private final Snapshots snapshots;

  public SnapshotMatcher(String testName, Snapshots snapshots) {
    this.testName = testName;
    this.snapshots = snapshots;
  }

  public void matchesSnapshot(Object target){
    snapshots.matches(testName, new GsonBuilder().setPrettyPrinting().create().toJson(target));
  }
}
