package org.swrlapi.sqwrl.exceptions;


public class SQWRLResultStateException extends SQWRLException
{
	private static final long serialVersionUID = 2728204279976896866L;

	public SQWRLResultStateException(String message)
	{
		super(message);
	}

	public SQWRLResultStateException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
