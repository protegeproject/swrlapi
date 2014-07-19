package org.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidColumnTypeException extends SQWRLException
{
	private static final long serialVersionUID = 1L;

	public SQWRLInvalidColumnTypeException(String message)
	{
		super(message);
	}

	public SQWRLInvalidColumnTypeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
