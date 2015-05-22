package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class SWRLBuiltInNotImplementedException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInNotImplementedException()
  {
    super("built-in not yet implemented");
  }

  public SWRLBuiltInNotImplementedException(@NonNull String message)
  {
    super("built-in not yet implemented: " + message);
  }
}
