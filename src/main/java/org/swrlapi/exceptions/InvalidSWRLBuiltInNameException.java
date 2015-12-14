package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class InvalidSWRLBuiltInNameException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInNameException(@NonNull String ruleName, @NonNull String builtInName)
  {
    super("unknown built-in '" + builtInName + "' in rule '" + ruleName + "'.");
  }

  public InvalidSWRLBuiltInNameException(@NonNull String builtInName)
  {
    super("unknown built-in '" + builtInName + "': ");
  }
}
