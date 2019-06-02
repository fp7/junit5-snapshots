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
