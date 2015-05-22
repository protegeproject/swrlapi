package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

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

  public SWRLBuiltInException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
