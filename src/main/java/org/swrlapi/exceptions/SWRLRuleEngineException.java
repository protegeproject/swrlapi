package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

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

  public SWRLRuleEngineException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
