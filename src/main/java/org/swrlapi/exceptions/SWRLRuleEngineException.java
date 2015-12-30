package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SWRLRuleEngineException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public SWRLRuleEngineException()
  {
    super();
  }

  public SWRLRuleEngineException(@NonNull String message)
  {
    super(message);
  }

  public SWRLRuleEngineException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
