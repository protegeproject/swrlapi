package org.swrlapi.exceptions;

public class UnresolvedBuiltInMethodException extends SWRLBuiltInLibraryException
{
	private static final long serialVersionUID = -7687942251602634373L;

	public UnresolvedBuiltInMethodException(String ruleName, String prefix, String builtInName, String message,
			Throwable cause)
	{
		super("unresolved built-in method '" + prefix + ":" + builtInName + "' in rule '" + ruleName + "'. " + message,
				cause);
	}
}
