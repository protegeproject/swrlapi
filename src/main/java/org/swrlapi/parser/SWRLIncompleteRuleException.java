package org.swrlapi.parser;

import org.checkerframework.checker.nullness.qual.NonNull;

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
