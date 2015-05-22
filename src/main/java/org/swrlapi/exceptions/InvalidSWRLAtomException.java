package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidSWRLAtomException extends SWRLRuleEngineBridgeException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLAtomException(@NonNull String message)
  {
    super(message);
  }
}
