package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SWRLRuleException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public SWRLRuleException(@NonNull String message)
  {
    super(message);
  }

  public SWRLRuleException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
