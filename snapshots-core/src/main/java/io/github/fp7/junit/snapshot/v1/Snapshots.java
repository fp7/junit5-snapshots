/*********************************************************************
* Copyright (c) 2019 Finn Petersen
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package io.github.fp7.junit.snapshot.v1;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;

interface Snapshots {

  void loadSnapshots();

  void matches(String testName, String snapshot);

  void finishTest();

  final class SnapshotVerifier implements Snapshots {

    private final Path snapshot;

    private Map<String, Integer> counters = new HashMap<>();

    private Map<String, List<String>> testSnapshots;

    SnapshotVerifier(Path snapshot) {
      this.snapshot = snapshot;
    }

    @Override
    public void loadSnapshots() {
      testSnapshots = Map.copyOf(Deserializer.read(this.snapshot));
    }

    @Override
    public void matches(String testName, String snapshot) {
      Integer counter = counters.merge(testName, 0, (old, val) -> old + 1);

      try {
        Assertions.assertEquals(
            testSnapshots.getOrDefault(testName, List.of()).get(counter), snapshot);
      } catch (ArrayIndexOutOfBoundsException e) {
        Assertions.fail(String.format("No snapshot for test %s with index %d", testName, counter));
      }
    }

    @Override
    public void finishTest() {}
  }

  final class SnapshotRecorder implements Snapshots {

    private final Path snapshotPath;

    private final Map<String, List<String>> testSnapshots = new HashMap<>();

    SnapshotRecorder(Path snapshotPath) {
      this.snapshotPath = snapshotPath;
    }

    @Override
    public void loadSnapshots() {}

    @Override
    public void matches(String testName, String snapshot) {
      if (testSnapshots.get(testName) == null) {
        testSnapshots.put(testName, List.of(snapshot));
      } else {
        testSnapshots.put(
            testName,
            List.copyOf(
                Stream.concat(testSnapshots.get(testName).stream(), Stream.of(snapshot))
                    .collect(Collectors.toList())));
      }
    }

    @Override
    public void finishTest() {
      Serializer.write(snapshotPath, testSnapshots);
    }
  }
}
