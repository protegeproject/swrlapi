package org.swrlapi.builtins.temporal;

public class TemporalException extends Exception
{
  private static final long serialVersionUID = 2232127555684267179L;

  public TemporalException(String message)
  {
    super(message);
  }

  public TemporalException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
