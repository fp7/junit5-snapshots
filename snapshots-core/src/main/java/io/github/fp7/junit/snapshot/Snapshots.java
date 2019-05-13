package io.github.fp7.junit.snapshot;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public final class Snapshots {

  private final Path snapshot;

  private final Map<String, List<String>> testSnapshots = new HashMap<>();

  Snapshots(Path snapshot) {
    this.snapshot = snapshot;
  }

  void loadSnapshots() {
    System.out.println("loading snapshot");
  }

  public void matches(Object target) {
    Assertions.assertEquals(snapshot, target);
  }

  public void finishTest() {
    System.out.println("Finishing snapshots");
  }
}
