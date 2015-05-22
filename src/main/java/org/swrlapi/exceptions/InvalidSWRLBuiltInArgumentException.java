package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class InvalidSWRLBuiltInArgumentException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInArgumentException(int argumentNumber, @NonNull String message)
  {
    super(message + " for (0-offset) argument #" + argumentNumber);
  }

  public InvalidSWRLBuiltInArgumentException(int argumentNumber, @NonNull String message, @Nullable Throwable cause)
  {
    super(message + " for (0-offset) argument #" + argumentNumber, cause);
  }

  public InvalidSWRLBuiltInArgumentException(@NonNull String message)
  {
    super(message);
  }

  public InvalidSWRLBuiltInArgumentException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
