package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class IncompatibleBuiltInMethodException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public IncompatibleBuiltInMethodException(@NonNull String ruleName, @NonNull String prefix,
    @NonNull String builtInMethodName, @NonNull String message)
  {
    super("incompatible Java method defined for built-in'" + prefix + ":" + builtInMethodName + "' (used in rule '"
      + ruleName + "'): " + message);
  }
}
