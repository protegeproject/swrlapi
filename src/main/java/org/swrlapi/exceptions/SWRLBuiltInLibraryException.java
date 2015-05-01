package org.swrlapi.exceptions;

public class SWRLBuiltInLibraryException extends SWRLBuiltInBridgeException
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInLibraryException(String message)
  {
    super(message);
  }

  public SWRLBuiltInLibraryException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
