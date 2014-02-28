package org.protege.swrlapi.exceptions;

public class UnresolvedBuiltInClassException extends SWRLBuiltInLibraryException
{
	private static final long serialVersionUID = 5290135603892715574L;

	public UnresolvedBuiltInClassException(String ruleName, String prefix, String message, Throwable cause)
	{
		super("unresolved built-in class for prefix '" + prefix + "' in rule '" + ruleName + "': " + message, cause);
	}
}
