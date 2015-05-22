package org.swrlapi.sqwrl.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SQWRLResultStateException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLResultStateException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLResultStateException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
