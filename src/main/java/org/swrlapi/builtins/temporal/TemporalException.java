package org.swrlapi.builtins.temporal;

import checkers.nullness.quals.NonNull;

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
