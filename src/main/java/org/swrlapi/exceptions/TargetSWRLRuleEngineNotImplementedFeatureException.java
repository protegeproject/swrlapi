package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class TargetSWRLRuleEngineNotImplementedFeatureException extends TargetSWRLRuleEngineException
{
  private static final long serialVersionUID = 1L;

  public TargetSWRLRuleEngineNotImplementedFeatureException(@NonNull String message)
  {
    super(message);
  }

  public TargetSWRLRuleEngineNotImplementedFeatureException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
