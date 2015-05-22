package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidSWRLBuiltInMethodsImplementationClass extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInMethodsImplementationClass(@NonNull String className)
  {
    super("Class " + className + " does not implement the interface SWRLBuiltInMethods");
  }
}
