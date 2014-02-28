package org.protege.swrlapi.sqwrl.exceptions;

public class SQWRLInvalidQueryNameException extends SQWRLException
{
	private static final long serialVersionUID = -1970080577535557651L;

	public SQWRLInvalidQueryNameException(String queryName)
	{
		super("invalid query name " + queryName);
	}
}
