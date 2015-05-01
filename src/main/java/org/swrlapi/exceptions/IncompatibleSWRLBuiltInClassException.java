package org.swrlapi.exceptions;

public class IncompatibleSWRLBuiltInClassException extends SWRLBuiltInLibraryException
{
  private static final long serialVersionUID = 1L;

  public IncompatibleSWRLBuiltInClassException(String ruleName, String prefix, String className, String message,
      Throwable cause)
  {
    super("incompatible Java built-in class " + className + " defined for library prefix " + prefix + " (used in rule "
        + ruleName + "): " + message, cause);
  }
}
