package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class TargetSWRLRuleEngineException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public TargetSWRLRuleEngineException()
  {
    super();
  }

  public TargetSWRLRuleEngineException(@NonNull String message)
  {
    super(message);
  }

  public TargetSWRLRuleEngineException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
