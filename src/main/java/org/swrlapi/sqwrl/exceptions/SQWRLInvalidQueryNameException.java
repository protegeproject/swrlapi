package org.swrlapi.sqwrl.exceptions;

public class SQWRLInvalidQueryNameException extends SQWRLException
{
	private static final long serialVersionUID = 1L;

	public SQWRLInvalidQueryNameException(String queryName)
	{
		super("invalid query name " + queryName);
	}
}
