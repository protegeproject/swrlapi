package org.protege.swrlapi.exceptions;

public class IncompatibleBuiltInMethodException extends BuiltInException
{
	private static final long serialVersionUID = 3609759610672991949L;

	public IncompatibleBuiltInMethodException(String ruleName, String prefix, String builtInMethodName, String message)
	{
		super("incompatible Java method defined for built-in'" + prefix + ":" + builtInMethodName + "' (used in rule '"
				+ ruleName + "'): " + message);
	}
}
