package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SQWRLResultStateException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLResultStateException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLResultStateException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
