package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SQWRLInvalidAggregateFunctionNameException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidAggregateFunctionNameException(@NonNull String message)
  {
    super(message);
  }

  public SQWRLInvalidAggregateFunctionNameException(@NonNull String message, Throwable cause)
  {
    super(message, cause);
  }
}
