package org.swrlapi.sqwrl.exceptions;

import checkers.nullness.quals.NonNull;

public class SQWRLInvalidQueryNameException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidQueryNameException(@NonNull String queryName)
  {
    super("invalid query name " + queryName);
  }
}
