/*********************************************************************
* Copyright (c) 2019 Finn Petersen
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

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
