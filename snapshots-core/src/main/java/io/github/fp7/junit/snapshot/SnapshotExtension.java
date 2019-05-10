package io.github.fp7.junit.snapshot;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class SnapshotExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback,
    ParameterResolver {

  private static final Namespace ns = Namespace.create("snapshots-data");

  private static Path snapshotPath;

  public static Path getSnapshotPath() {
    return Objects.requireNonNull(snapshotPath);
  }

  public SnapshotExtension(String foo) {
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

    context.getStore(ns).put("cur", getName(context));

  }

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    System.out.println("context.getStore(ns).get(\"cur\") = " + context.getStore(ns).get("cur"));

    snapshotPath = null;
  }



  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType().equals(Snapshot.class);
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {

    return new Snapshot("foo");
  }

  public static final class Snapshot{
    private final String snapshot;

    public Snapshot(String snapshot) {
      this.snapshot = snapshot;
    }

    public void matches(Object target){
      Assertions.assertEquals(snapshot, target);

    }
  }
}
