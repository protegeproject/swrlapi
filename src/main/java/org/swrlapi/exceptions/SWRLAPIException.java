package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SWRLAPIException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public SWRLAPIException()
  {
    super();
  }

  public SWRLAPIException(@NonNull String message)
  {
    super(message);
  }

  public SWRLAPIException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
