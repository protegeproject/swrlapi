package org.protege.swrlapi.exceptions;

public class InvalidIndividualNameException extends SWRLFactoryException
{
	private static final long serialVersionUID = 3422362408029163085L;

	public InvalidIndividualNameException(String name)
	{
		super("invalid individual name " + name);
	}
}
