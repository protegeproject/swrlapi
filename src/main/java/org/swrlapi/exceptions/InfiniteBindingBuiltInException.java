package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InfiniteBindingBuiltInException extends SWRLBuiltInException
{
  private static final long serialVersionUID = 1L;

  public InfiniteBindingBuiltInException()
  {
    super("infinite bindings would be required to satisfy this built-in predicate");
  }

  public InfiniteBindingBuiltInException(@NonNull String builtInName, @NonNull String message)
  {
    super("infinite bindings would be required to satisfy this built-in " + builtInName + ": " + message);
  }
}
