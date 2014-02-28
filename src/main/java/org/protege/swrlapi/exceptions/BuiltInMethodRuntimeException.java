package org.protege.swrlapi.exceptions;

public class BuiltInMethodRuntimeException extends BuiltInException
{
	private static final long serialVersionUID = 3902827862772321983L;

	public BuiltInMethodRuntimeException(String ruleName, String builtInName, String message, Throwable cause)
	{
		super("runtime exception in built-in " + builtInName + " in rule " + ruleName + ": " + message, cause);
	}
}
