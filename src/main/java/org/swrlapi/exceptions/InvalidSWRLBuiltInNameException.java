package org.swrlapi.exceptions;

public class InvalidSWRLBuiltInNameException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInNameException(String ruleName, String builtInName)
  {
    super("unknown built-in '" + builtInName + "' in rule '" + ruleName + "'.");
  }

  public InvalidSWRLBuiltInNameException(String builtInName)
  {
    super("unknown built-in '" + builtInName + "': ");
  }
}
