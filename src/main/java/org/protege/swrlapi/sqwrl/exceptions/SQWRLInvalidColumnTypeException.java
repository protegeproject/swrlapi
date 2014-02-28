package org.protege.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidColumnTypeException extends SQWRLException
{
	private static final long serialVersionUID = -9196049416748029436L;

	public SQWRLInvalidColumnTypeException(String message)
	{
		super(message);
	}

	public SQWRLInvalidColumnTypeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
