package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

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
