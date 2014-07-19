package org.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidColumnIndexException extends SQWRLException
{
	private static final long serialVersionUID = 1L;

	public SQWRLInvalidColumnIndexException(String message)
	{
		super(message);
	}

	public SQWRLInvalidColumnIndexException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
