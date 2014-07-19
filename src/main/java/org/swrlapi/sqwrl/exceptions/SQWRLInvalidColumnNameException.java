package org.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidColumnNameException extends SQWRLException
{
	private static final long serialVersionUID = 1L;

	public SQWRLInvalidColumnNameException(String message)
	{
		super(message);
	}

	public SQWRLInvalidColumnNameException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
