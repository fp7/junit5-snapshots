package io.github.fp7.junit.snapshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
    implements BeforeAllCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback,
    AfterAllCallback, ParameterResolver {

  private static final Namespace SNAPSHOTS = Namespace.create("SNAPSHOTS-data");

  public SnapshotExtension() {
  }

  private String getName(ExtensionContext ctx) {
    var l = new ArrayList<String>();
    Optional<ExtensionContext> tmp = Optional.of(ctx);
    while (tmp.isPresent()) {
      l.add(tmp.get().getDisplayName());
      tmp = tmp.get().getParent();
    }
    Collections.reverse(l);

    return String.join(" ", l);
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {

//    context.getStore(ns).put("cur", getName(context));
  }

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
//    System.out.println("context.getStore(ns).get(\"cur\") = " + context.getStore(ns).get("cur"));
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return parameterContext.getParameter().getType().equals(Snapshot.class);
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {

    return new Snapshot("foo");
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    Path current = context.getStore(SNAPSHOTS).get("current", Path.class);
    Files.write(current, new byte[0]);
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Path file = snapshotFile(context);

    context.getStore(SNAPSHOTS).put("current", file);
  }

  private Path snapshotFile(ExtensionContext ctx) {
    return Path.of("src/test/java",
        String.format("%s.java.snap", ctx.getRequiredTestClass().getName().replace(".",
            File.separator)));
  }

  public static final class Snapshot {

    private final String snapshot;

    public Snapshot(String snapshot) {
      this.snapshot = snapshot;
    }

    public void matches(Object target) {
      Assertions.assertEquals(snapshot, target);
    }
  }
}
