package io.github.fp7.junit.snapshot.example;

import io.github.fp7.junit.snapshot.SnapshotExtension;
import io.github.fp7.junit.snapshot.SnapshotExtension.Snapshot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
class TestServiceTest {

  private final TestService sut = new TestService();

  @Test
  void checkAgainstFoo(Snapshot s) {

    s.matches(sut.foo());
  }
}
