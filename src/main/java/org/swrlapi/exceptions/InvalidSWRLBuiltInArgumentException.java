package org.swrlapi.exceptions;

public class InvalidSWRLBuiltInArgumentException extends SWRLBuiltInException
{
	private static final long serialVersionUID = 1L;

	public InvalidSWRLBuiltInArgumentException(int argumentNumber, String message)
	{
		super(message + " for (0-offset) argument #" + argumentNumber);
	}

	public InvalidSWRLBuiltInArgumentException(int argumentNumber, String message, Throwable cause)
	{
		super(message + " for (0-offset) argument #" + argumentNumber, cause);
	}

	public InvalidSWRLBuiltInArgumentException(String message)
	{
		super(message);
	}

	public InvalidSWRLBuiltInArgumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
