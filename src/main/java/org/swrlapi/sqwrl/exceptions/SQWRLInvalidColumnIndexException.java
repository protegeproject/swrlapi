package org.swrlapi.sqwrl.exceptions;

import checkers.nullness.quals.NonNull;

public class SQWRLInvalidColumnIndexException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidColumnIndexException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLInvalidColumnIndexException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
