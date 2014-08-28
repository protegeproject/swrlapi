package org.swrlapi.exceptions;

public class SWRLBuiltInMethodRuntimeException extends SWRLBuiltInException
{
	private static final long serialVersionUID = 1L;

	public SWRLBuiltInMethodRuntimeException(String ruleName, String builtInName, String message, Throwable cause)
	{
		super("runtime exception in built-in " + builtInName + " in rule " + ruleName + ": " + message, cause);
	}
}
