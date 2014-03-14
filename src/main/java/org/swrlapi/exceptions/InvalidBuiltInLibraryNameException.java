package org.swrlapi.exceptions;

public class InvalidBuiltInLibraryNameException extends SWRLRuleEngineBridgeException
{
	private static final long serialVersionUID = 4802864763432733818L;

	public InvalidBuiltInLibraryNameException(String libraryNamespace)
	{
		super("Invalid built-in library name '" + libraryNamespace + "'");
	}
}
