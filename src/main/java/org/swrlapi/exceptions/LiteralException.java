package org.swrlapi.exceptions;

import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class LiteralException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public LiteralException(String message)
  {
    super(message);
  }
}
