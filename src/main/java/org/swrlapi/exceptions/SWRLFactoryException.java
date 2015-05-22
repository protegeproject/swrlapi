package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SWRLFactoryException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public SWRLFactoryException(@NonNull String message)
  {
    super(message);
  }

  public SWRLFactoryException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
