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

import io.github.fp7.junit.snapshot.v1.Snapshots.SnapshotRecorder;
import io.github.fp7.junit.snapshot.v1.Snapshots.SnapshotVerifier;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class SnapshotExtension
    implements BeforeAllCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        AfterAllCallback,
        ParameterResolver {

  private static final Namespace SNAPSHOTS = Namespace.create("SNAPSHOTS-data");

  public SnapshotExtension() {}

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {}

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {}

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return parameterContext.getParameter().getType().equals(SnapshotMatcher.class);
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return new SnapshotMatcher(
        testName(extensionContext),
        extensionContext.getStore(SNAPSHOTS).get("current", Snapshots.class));
  }

  private String testName(ExtensionContext extensionContext) {
    return String.format(
        "%s#%s",
        extensionContext.getRequiredTestClass().getName(), extensionContext.getDisplayName());
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    if (context.getExecutionException().isPresent()) {
      return;
    }
    context.getStore(SNAPSHOTS).get("current", Snapshots.class).finishTest();
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Path file = snapshotFile(context);
    Snapshots snapshots;
    if (Files.exists(file)) {
      snapshots = new SnapshotVerifier(file);
      snapshots.loadSnapshots();
    } else {
      snapshots = new SnapshotRecorder(file);
    }

    context.getStore(SNAPSHOTS).put("current", snapshots);
  }

  private Path snapshotFile(ExtensionContext ctx) {
    return Path.of(
        "src/test/java",
        String.format(
            "%s.java.snap", ctx.getRequiredTestClass().getName().replace(".", File.separator)));
  }
}
