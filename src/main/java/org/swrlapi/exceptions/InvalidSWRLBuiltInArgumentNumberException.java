package org.swrlapi.exceptions;

public class InvalidSWRLBuiltInArgumentNumberException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInArgumentNumberException(int expecting, int actual)
  {
    super("invalid number of arguments - expecting " + expecting + " argument(s), got " + actual);
  }

  public InvalidSWRLBuiltInArgumentNumberException(int expecting, int actual, String message)
  {
    super("invalid number of arguments - expecting " + message + " " + expecting + " argument(s), got " + actual);
  }
}
