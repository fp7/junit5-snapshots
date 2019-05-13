package io.github.fp7.junit.snapshot;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeserializerTest {

  @Test
  void readTestSnapshot() {

    Map<String, List<String>> read = Deserializer.read(Path.of("src/test/resources/test.snap"));

    Map<String, List<String>> expected = new HashMap<>();

    expected.put(
        "changes the class when hovered",
        List.of(
            Stream.of(
                    "<a",
                    "  className=\"normal\"",
                    "  href=\"http://www.facebook.com\"",
                    "  onMouseEnter={[Function]}",
                    "  onMouseLeave={[Function]}",
                    ">",
                    "  Facebook",
                    "</a>")
                .collect(Collectors.joining(System.lineSeparator())),
            Stream.of(
                    "<a",
                    "  className=\"hovered\"",
                    "  href=\"http://www.facebook.com\"",
                    "  onMouseEnter={[Function]}",
                    "  onMouseLeave={[Function]}",
                    ">",
                    "  Facebook",
                    "</a>")
                .collect(Collectors.joining(System.lineSeparator()))));

    Assertions.assertEquals(expected, read);
  }
}
