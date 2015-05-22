package org.swrlapi.sqwrl.exceptions;

import checkers.nullness.quals.NonNull;

public class SQWRLInvalidRowIndexException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidRowIndexException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLInvalidRowIndexException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
