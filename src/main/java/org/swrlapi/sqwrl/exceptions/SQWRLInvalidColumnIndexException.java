package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

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
