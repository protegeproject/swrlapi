package org.swrlapi.exceptions;

public class IncompatibleBuiltInMethodException extends SWRLBuiltInException
{
	private static final long serialVersionUID = 1L;

	public IncompatibleBuiltInMethodException(String ruleName, String prefix, String builtInMethodName, String message)
	{
		super("incompatible Java method defined for built-in'" + prefix + ":" + builtInMethodName + "' (used in rule '"
				+ ruleName + "'): " + message);
	}
}
