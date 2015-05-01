package org.swrlapi.exceptions;

public class SWRLRuleEngineBridgeException extends SWRLRuleEngineException
{
  private static final long serialVersionUID = 1L;

  public SWRLRuleEngineBridgeException()
  {
    super();
  }

  public SWRLRuleEngineBridgeException(String message)
  {
    super(message);
  }

  public SWRLRuleEngineBridgeException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
