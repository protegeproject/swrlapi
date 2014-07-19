package org.swrlapi.exceptions;

public class InvalidBuiltInArgumentNumberException extends BuiltInException
{
	private static final long serialVersionUID = 1L;

	public InvalidBuiltInArgumentNumberException(int expecting, int actual)
	{
		super("invalid number of arguments - expecting " + expecting + " argument(s), got " + actual);
	}

	public InvalidBuiltInArgumentNumberException(int expecting, int actual, String message)
	{
		super("invalid number of arguments - expecting " + message + " " + expecting + " argument(s), got " + actual);
	}
}
