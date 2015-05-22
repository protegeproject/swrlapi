package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InconsistentKnowledgeBaseException extends SWRLRuleEngineBridgeException
{
  private static final long serialVersionUID = 1L;

  public InconsistentKnowledgeBaseException(@NonNull String message)
  {
    super(message);
  }
}
