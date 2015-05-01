package org.swrlapi.exceptions;

public class InvalidSWRLBuiltInMethodsImplementationClass extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInMethodsImplementationClass(String className)
  {
    super("Class " + className + " does not implement the interface SWRLBuiltInMethods");
  }
}
