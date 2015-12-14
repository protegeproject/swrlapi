package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
