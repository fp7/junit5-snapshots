package io.github.fp7.junit.snapshot;

import java.io.File;
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
    return parameterContext.getParameter().getType().equals(Snapshots.class);
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {

    return extensionContext.getStore(SNAPSHOTS).get("current", Snapshots.class);
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    context.getStore(SNAPSHOTS).get("current", Snapshots.class).finishTest();
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Path file = snapshotFile(context);

    Snapshots snapshots = new Snapshots(file);
    snapshots.loadSnapshots();

    context.getStore(SNAPSHOTS).put("current", snapshots);
  }

  private Path snapshotFile(ExtensionContext ctx) {
    return Path.of(
        "src/test/java",
        String.format(
            "%s.java.snap", ctx.getRequiredTestClass().getName().replace(".", File.separator)));
  }
}
