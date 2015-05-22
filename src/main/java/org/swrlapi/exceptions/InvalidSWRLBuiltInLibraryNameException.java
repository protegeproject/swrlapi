package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidSWRLBuiltInLibraryNameException extends SWRLRuleEngineBridgeException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLBuiltInLibraryNameException(@NonNull String libraryNamespace)
  {
    super("Invalid built-in library name '" + libraryNamespace + "'");
  }
}
