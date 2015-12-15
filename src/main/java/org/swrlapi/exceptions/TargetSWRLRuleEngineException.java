package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

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

  public TargetSWRLRuleEngineException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
