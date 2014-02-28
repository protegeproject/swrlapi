package org.protege.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidQueryException extends SQWRLException
{
	private static final long serialVersionUID = 7164479181999770966L;

	public SQWRLInvalidQueryException(String message)
	{
		super(message);
	}

	public SQWRLInvalidQueryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
