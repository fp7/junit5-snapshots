import io.github.fp7.junit.snapshot.SnapshotExtension;
import io.github.fp7.junit.snapshot.SnapshotExtension.Snapshot;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

class SnapshotExtensionTest {

  @RegisterExtension
  public static SnapshotExtension se = new SnapshotExtension("");

  @Test
  void foobar(Snapshot sn) {

    sn.matches("bar");

    Assertions.assertEquals(5, 5);
  }

  @Test
  void blabla() {
    Assertions.assertEquals(1, 1);
  }
}