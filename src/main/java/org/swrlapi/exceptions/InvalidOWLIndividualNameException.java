package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidOWLIndividualNameException extends SWRLFactoryException
{
  private static final long serialVersionUID = 1L;

  public InvalidOWLIndividualNameException(@NonNull String name)
  {
    super("invalid individual name " + name);
  }
}
