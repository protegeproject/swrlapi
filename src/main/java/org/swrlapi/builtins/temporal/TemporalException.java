package org.swrlapi.builtins.temporal;

import org.checkerframework.checker.nullness.qual.NonNull;

class TemporalException extends Exception
{
  private static final long serialVersionUID = 1L;

  public TemporalException(@NonNull String message)
  {
    super(message);
  }

  public TemporalException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
