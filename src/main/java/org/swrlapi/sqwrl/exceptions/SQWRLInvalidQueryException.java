package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SQWRLInvalidQueryException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidQueryException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLInvalidQueryException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
