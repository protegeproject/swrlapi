package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class UnresolvedSWRLBuiltInMethodException extends SWRLBuiltInLibraryException
{
  private static final long serialVersionUID = 1L;

  public UnresolvedSWRLBuiltInMethodException(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String builtInName, @NonNull String message, @NonNull Throwable cause)
  {
    super("unresolved built-in method '" + prefix + ":" + builtInName + "' in rule '" + ruleName + "'. " + message,
      cause);
  }
}
