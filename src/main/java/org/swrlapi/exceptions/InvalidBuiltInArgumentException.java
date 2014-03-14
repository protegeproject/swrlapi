package org.swrlapi.exceptions;

public class InvalidBuiltInArgumentException extends BuiltInException
{
	private static final long serialVersionUID = -6350000189524699264L;

	public InvalidBuiltInArgumentException(int argumentNumber, String message)
	{
		super(message + " for (0-offset) argument #" + argumentNumber);
	}

	public InvalidBuiltInArgumentException(int argumentNumber, String message, Throwable cause)
	{
		super(message + " for (0-offset) argument #" + argumentNumber, cause);
	}

	public InvalidBuiltInArgumentException(String message)
	{
		super(message);
	}

	public InvalidBuiltInArgumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
