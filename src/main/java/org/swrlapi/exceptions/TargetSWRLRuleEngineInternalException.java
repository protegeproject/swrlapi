package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
