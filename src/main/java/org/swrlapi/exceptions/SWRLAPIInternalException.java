package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SWRLAPIInternalException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public SWRLAPIInternalException(@NonNull String message)
  {
    super(message);
  }

  public SWRLAPIInternalException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}