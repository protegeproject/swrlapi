package org.swrlapi.exceptions;

public class InvalidBuiltInLibraryNameException extends SWRLRuleEngineBridgeException
{
	private static final long serialVersionUID = 1L;

	public InvalidBuiltInLibraryNameException(String libraryNamespace)
	{
		super("Invalid built-in library name '" + libraryNamespace + "'");
	}
}
