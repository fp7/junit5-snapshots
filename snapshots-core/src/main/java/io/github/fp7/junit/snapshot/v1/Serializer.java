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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Serializer {

  static void write(Path path, Map<String, List<String>> snapshots) {

    try (var pw = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {

      pw.println("// Jest Snapshot v1, https://goo.gl/fbAQLP");

      snapshots.forEach(
          (k, v) -> {
            var counter = new AtomicInteger(1);
            v.forEach(
                s -> {
                  pw.println();
                  pw.println(String.format("exports[`%s %d`] = `", k, counter.getAndIncrement()));
                  pw.println(s.replace("`", "\\`"));
                  pw.println("`;");
                });
          });

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
