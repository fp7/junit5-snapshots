package io.github.fp7.junit.snapshot.example;

import io.github.fp7.junit.snapshot.SnapshotExtension;
import io.github.fp7.junit.snapshot.SnapshotMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
class TestServiceTest {

  @Test
  void checkAgainstFoo(SnapshotMatcher s) {
    s.matchesSnapshot("foo");
  }

  @Test
  void checkBacktickSemicolon(SnapshotMatcher s){
    s.matchesSnapshot("`;");
  }

  @Test
  void checkEmptyLines(SnapshotMatcher s){
    s.matchesSnapshot("hallo \n\nworld!");
  }
}
