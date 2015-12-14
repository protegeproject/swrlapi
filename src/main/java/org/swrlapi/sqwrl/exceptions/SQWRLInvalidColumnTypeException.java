package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SQWRLInvalidColumnTypeException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidColumnTypeException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLInvalidColumnTypeException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
