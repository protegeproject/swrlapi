package org.swrlapi.sqwrl.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SQWRLInvalidQueryNameException extends SQWRLException
{
  private static final long serialVersionUID = 1L;

  public SQWRLInvalidQueryNameException(@NonNull String queryName)
  {
    super("invalid query name " + queryName);
  }
}
