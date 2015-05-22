package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class TargetSWRLRuleEngineInternalException extends TargetSWRLRuleEngineException
{
  private static final long serialVersionUID = 1L;

  public TargetSWRLRuleEngineInternalException(@NonNull String message)
  {
    super(message);
  }

  public TargetSWRLRuleEngineInternalException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
