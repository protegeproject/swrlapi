package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SWRLBuiltInMethodRuntimeException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInMethodRuntimeException(@NonNull String ruleName, @NonNull String builtInName,
    @NonNull String message, @Nullable Throwable cause)
  {
    super("runtime exception in built-in " + builtInName + " in rule " + ruleName + ": " + message, cause);
  }
}
