package io.github.fp7.junit.snapshot;

public class SnapshotMatcher {

  private final String testName;

  private final Snapshots snapshots;

  public SnapshotMatcher(String testName, Snapshots snapshots) {
    this.testName = testName;
    this.snapshots = snapshots;
  }

  public void matchesSnapshot(Object target){
    snapshots.matches(testName, target.toString());
  }
}
