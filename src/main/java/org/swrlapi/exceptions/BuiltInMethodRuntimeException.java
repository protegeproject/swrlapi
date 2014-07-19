package org.swrlapi.exceptions;

public class BuiltInMethodRuntimeException extends BuiltInException
{
	private static final long serialVersionUID = 1L;

	public BuiltInMethodRuntimeException(String ruleName, String builtInName, String message, Throwable cause)
	{
		super("runtime exception in built-in " + builtInName + " in rule " + ruleName + ": " + message, cause);
	}
}
