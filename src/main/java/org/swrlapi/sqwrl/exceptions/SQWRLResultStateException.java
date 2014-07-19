package org.swrlapi.sqwrl.exceptions;


public class SQWRLResultStateException extends SQWRLException
{
	private static final long serialVersionUID = 1L;

	public SQWRLResultStateException(String message)
	{
		super(message);
	}

	public SQWRLResultStateException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
