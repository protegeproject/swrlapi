package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class InvalidSWRLBuiltInArgumentException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInArgumentException(int argumentNumber, @NonNull String message)
  {
    super(message + " for (0-offset) argument #" + argumentNumber);
  }

  public InvalidSWRLBuiltInArgumentException(int argumentNumber, @NonNull String message, @NonNull Throwable cause)
  {
    super(message + " for (0-offset) argument #" + argumentNumber, cause);
  }

  public InvalidSWRLBuiltInArgumentException(@NonNull String message)
  {
    super(message);
  }

  public InvalidSWRLBuiltInArgumentException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
