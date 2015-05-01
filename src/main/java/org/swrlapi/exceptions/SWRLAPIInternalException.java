package org.swrlapi.exceptions;

public class SWRLAPIInternalException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public SWRLAPIInternalException(String message)
  {
    super(message);
  }

  public SWRLAPIInternalException(String message, Throwable cause)
  {
    super(message, cause);
  }
}