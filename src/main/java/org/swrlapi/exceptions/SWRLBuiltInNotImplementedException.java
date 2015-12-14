package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

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
