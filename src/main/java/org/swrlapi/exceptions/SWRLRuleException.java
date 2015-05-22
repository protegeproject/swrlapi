package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SWRLRuleException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public SWRLRuleException(@NonNull String message)
  {
    super(message);
  }

  public SWRLRuleException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
