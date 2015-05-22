package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class UnresolvedSWRLBuiltInMethodException extends SWRLBuiltInLibraryException
{
  private static final long serialVersionUID = 1L;

  public UnresolvedSWRLBuiltInMethodException(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String builtInName, @NonNull String message, @Nullable Throwable cause)
  {
    super("unresolved built-in method '" + prefix + ":" + builtInName + "' in rule '" + ruleName + "'. " + message,
      cause);
  }
}
