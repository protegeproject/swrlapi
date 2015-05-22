package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class UnresolvedSWRLBuiltInClassException extends SWRLBuiltInLibraryException
{
  private static final long serialVersionUID = 1L;

  public UnresolvedSWRLBuiltInClassException(@NonNull String ruleName, @NonNull String prefix, @NonNull String message,
    @Nullable Throwable cause)
  {
    super("unresolved built-in class for prefix '" + prefix + "' in rule '" + ruleName + "': " + message, cause);
  }
}
