package org.swrlapi.exceptions;

public class InvalidSWRLBuiltInLibraryNameException extends SWRLRuleEngineBridgeException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInLibraryNameException(String libraryNamespace)
  {
    super("Invalid built-in library name '" + libraryNamespace + "'");
  }
}
