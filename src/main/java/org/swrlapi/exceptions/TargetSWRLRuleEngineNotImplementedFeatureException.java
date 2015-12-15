package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TargetSWRLRuleEngineNotImplementedFeatureException extends TargetSWRLRuleEngineException
{
  private static final long serialVersionUID = 1L;

  public TargetSWRLRuleEngineNotImplementedFeatureException(@NonNull String message)
  {
    super(message);
  }

  public TargetSWRLRuleEngineNotImplementedFeatureException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
