package org.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidAggregateFunctionNameException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidAggregateFunctionNameException(String message)
  {
    super(message);
  }

  public SQWRLInvalidAggregateFunctionNameException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
