package org.swrlapi.exceptions;

public class IncompatibleBuiltInClassException extends SWRLBuiltInLibraryException
{
	private static final long serialVersionUID = 767063480450933165L;

	public IncompatibleBuiltInClassException(String ruleName, String prefix, String className, String message,
			Throwable cause)
	{
		super("incompatible Java built-in class " + className + " defined for library prefix " + prefix + " (used in rule "
				+ ruleName + "): " + message, cause);
	}
}
