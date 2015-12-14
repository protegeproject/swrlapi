package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
