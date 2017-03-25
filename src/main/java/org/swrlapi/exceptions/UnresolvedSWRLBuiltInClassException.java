package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class UnresolvedSWRLBuiltInClassException extends SWRLBuiltInLibraryException
{
  private static final long serialVersionUID = 1L;

  public UnresolvedSWRLBuiltInClassException(@NonNull String prefix, @NonNull String message,
    @NonNull Throwable cause)
  {
    super("unresolved built-in class for prefix " + prefix + ": " + message, cause);
  }
}
