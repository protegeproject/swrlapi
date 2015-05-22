package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidSWRLRuleNameException extends SWRLRuleEngineException
{
  private static final long serialVersionUID = -1L;

  public InvalidSWRLRuleNameException(@NonNull String ruleName)
  {
    super("invalid SWRL rule name '" + ruleName + "'");
  }
}
