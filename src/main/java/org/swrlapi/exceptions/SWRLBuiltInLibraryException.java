package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public class SWRLBuiltInLibraryException extends SWRLBuiltInBridgeException
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInLibraryException(@NonNull String message)
  {
    super(message);
  }

  public SWRLBuiltInLibraryException(@NonNull String message, @Nullable Throwable cause)
  {
    super(message, cause);
  }
}
