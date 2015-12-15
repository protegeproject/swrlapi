package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

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

  public SWRLAPIException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
