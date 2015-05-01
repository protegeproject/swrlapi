package org.swrlapi.exceptions;

public class InvalidSWRLRuleNameException extends SWRLRuleEngineException
{
  private static final long serialVersionUID = -1L;

  public InvalidSWRLRuleNameException(String ruleName)
  {
    super("invalid SWRL rule name '" + ruleName + "'");
  }
}
