package org.swrlapi.sqwrl.exceptions;

import checkers.nullness.quals.NonNull;

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
