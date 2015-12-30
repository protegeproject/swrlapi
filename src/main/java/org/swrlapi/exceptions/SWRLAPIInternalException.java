package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SWRLAPIInternalException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public SWRLAPIInternalException(@NonNull String message)
  {
    super(message);
  }

  public SWRLAPIInternalException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}