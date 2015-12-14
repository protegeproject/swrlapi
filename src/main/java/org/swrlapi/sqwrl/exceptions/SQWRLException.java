package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
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
