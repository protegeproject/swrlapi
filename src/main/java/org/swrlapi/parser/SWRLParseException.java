package org.swrlapi.parser;

import org.checkerframework.checker.nullness.qual.NonNull;

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
