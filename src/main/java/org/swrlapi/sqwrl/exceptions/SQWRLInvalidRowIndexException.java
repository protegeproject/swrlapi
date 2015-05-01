package org.swrlapi.sqwrl.exceptions;

public class SQWRLInvalidRowIndexException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidRowIndexException(String message)
  {
    super(message);
  }

  public SQWRLInvalidRowIndexException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
