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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;

final class Deserializer {

  static Map<String, List<String>> read(Path path) {
    try {
      Map<String, List<String>> result = new HashMap<>();
      var strings = Files.readAllLines(path).stream().skip(2).collect(Collectors.toList());

      StringWriter stringHolder = null;
      PrintWriter w = null;
      String name = null;
      var started = false;

      for (var string : strings) {
        if (string.startsWith("exports[`") && string.endsWith("`] = `")) {
          stringHolder = new StringWriter();
          w = new PrintWriter(stringHolder);
          name = string.substring("exports[`".length(), string.length() - "`] = `".length());
          name = name.substring(0, name.lastIndexOf(" "));
          started = true;

        } else if (string.equals("`;")) {
          Assertions.assertNotNull(stringHolder);
          Assertions.assertNotNull(name);
          started = false;

          String snapshot = stringHolder.toString();
          snapshot = snapshot.substring(0, snapshot.length() - 1);
          result.merge(
              name,
              List.of(snapshot),
              (r1, r2) -> Stream.concat(r1.stream(), r2.stream()).collect(Collectors.toList()));

        } else if (started) {
          Assertions.assertNotNull(w);
          w.println(string.replace("\\`", "`"));
        }
      }

      return result;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
