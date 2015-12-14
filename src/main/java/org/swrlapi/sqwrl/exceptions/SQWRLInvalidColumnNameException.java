package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SQWRLInvalidColumnNameException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidColumnNameException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLInvalidColumnNameException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
