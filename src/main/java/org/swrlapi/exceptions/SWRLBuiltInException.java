package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SWRLBuiltInException extends Exception
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInException()
  {
    super();
  }

  public SWRLBuiltInException(@NonNull String message)
  {
    super(message);
  }

  public SWRLBuiltInException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
