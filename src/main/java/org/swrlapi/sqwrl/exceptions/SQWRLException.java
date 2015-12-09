package org.swrlapi.sqwrl.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.swrlapi.exceptions.SWRLBuiltInException;

public class SQWRLException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public SQWRLException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
