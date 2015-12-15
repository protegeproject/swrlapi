package org.swrlapi.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SWRLBuiltInBridgeException extends SWRLAPIException
{
  private static final long serialVersionUID = 1L;

  public SWRLBuiltInBridgeException(@NonNull String message)
  {
    super(message);
  }

  public SWRLBuiltInBridgeException(@NonNull String message, @NonNull Throwable cause)
  {
    super(message, cause);
  }
}
