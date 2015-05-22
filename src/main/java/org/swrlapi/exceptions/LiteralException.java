package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class LiteralException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public LiteralException(@NonNull String message)
  {
    super(message);
  }
}
