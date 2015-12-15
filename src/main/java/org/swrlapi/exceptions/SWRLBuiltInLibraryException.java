package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SWRLBuiltInLibraryException extends SWRLBuiltInBridgeException
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInLibraryException(@NonNull String message)
  {
    super(message);
  }

  public SWRLBuiltInLibraryException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
