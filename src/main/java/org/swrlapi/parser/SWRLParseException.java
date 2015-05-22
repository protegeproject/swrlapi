package org.swrlapi.parser;

import checkers.nullness.quals.NonNull;

/**
 * @see org.swrlapi.parser.SWRLParser
 */
public class SWRLParseException extends Exception
{
  private static final long serialVersionUID = 1L;

  public SWRLParseException()
  {
    super();
  }

  public SWRLParseException(@NonNull String s)
  {
    super(s);
  }
}
