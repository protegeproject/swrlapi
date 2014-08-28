package org.swrlapi.exceptions;

public class UnresolvedSWRLBuiltInMethodException extends SWRLBuiltInLibraryException
{
	private static final long serialVersionUID = 1L;

	public UnresolvedSWRLBuiltInMethodException(String ruleName, String prefix, String builtInName, String message,
			Throwable cause)
	{
		super("unresolved built-in method '" + prefix + ":" + builtInName + "' in rule '" + ruleName + "'. " + message,
				cause);
	}
}
