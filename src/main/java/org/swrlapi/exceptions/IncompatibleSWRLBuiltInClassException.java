package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class IncompatibleSWRLBuiltInClassException extends SWRLBuiltInLibraryException
{
  private static final long serialVersionUID = 1L;

  public IncompatibleSWRLBuiltInClassException(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String className, @NonNull String message, @NonNull Throwable cause)
  {
    super("incompatible Java built-in class " + className + " defined for library prefix " + prefix + " (used in rule "
      + ruleName + "): " + message, cause);
  }

  public IncompatibleSWRLBuiltInClassException(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String className, @NonNull String message)
  {
    super("incompatible Java built-in class " + className + " defined for library prefix " + prefix + " (used in rule "
      + ruleName + "): " + message);
  }
}
