package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidOWLPropertyNameException extends SWRLFactoryException
{
  private static final long serialVersionUID = 1L;

  public InvalidOWLPropertyNameException(@NonNull String message)
  {
    super(message);
  }
}
