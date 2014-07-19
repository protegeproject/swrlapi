package org.swrlapi.exceptions;

public class InvalidIndividualNameException extends SWRLFactoryException
{
	private static final long serialVersionUID = 1L;

	public InvalidIndividualNameException(String name)
	{
		super("invalid individual name " + name);
	}
}
