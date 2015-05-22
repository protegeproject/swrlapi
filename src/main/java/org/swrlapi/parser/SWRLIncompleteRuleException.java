package org.swrlapi.parser;

import checkers.nullness.quals.NonNull;

/**
 * @see org.swrlapi.parser.SWRLParser
 */
public class SWRLIncompleteRuleException extends SWRLParseException
{
  private static final long serialVersionUID = 1L;

  public SWRLIncompleteRuleException(@NonNull String message)
  {
    super(message);
  }
}
