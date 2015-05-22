package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SWRLRuleEngineBridgeException extends SWRLRuleEngineException
{
  private static final long serialVersionUID = 1L;

  public SWRLRuleEngineBridgeException()
  {
    super();
  }

  public SWRLRuleEngineBridgeException(@NonNull String message)
  {
    super(message);
  }

  SWRLRuleEngineBridgeException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
