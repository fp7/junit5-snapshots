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

import com.google.gson.GsonBuilder;

public class SnapshotMatcher {

  private final String testName;

  private final Snapshots snapshots;

  public SnapshotMatcher(String testName, Snapshots snapshots) {
    this.testName = testName;
    this.snapshots = snapshots;
  }

  public void matchesSnapshot(Object target) {
    snapshots.matches(testName, new GsonBuilder().setPrettyPrinting().create().toJson(target));
  }
}
