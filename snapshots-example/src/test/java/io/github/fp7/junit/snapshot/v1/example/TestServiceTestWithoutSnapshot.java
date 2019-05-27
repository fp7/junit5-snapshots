package io.github.fp7.junit.snapshot.v1.example;

import io.github.fp7.junit.snapshot.v1.SnapshotExtension;
import io.github.fp7.junit.snapshot.v1.SnapshotMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// TODO: Find a way to run this test twice
@ExtendWith(SnapshotExtension.class)
class TestServiceTestWithoutSnapshot {

  @Test
  void checkAgainstFoo(SnapshotMatcher s) {
    s.matchesSnapshot("foo");
  }

  @Test
  void checkBacktickSemicolon(SnapshotMatcher s) {
    s.matchesSnapshot("`;");
  }

  @Test
  void checkEmptyLines(SnapshotMatcher s) {
    s.matchesSnapshot("hallo \n\nworld!");
  }

  @Test
  void checkBean(SnapshotMatcher s) {
    s.matchesSnapshot(new TestBean("foo", 3));
  }
}
