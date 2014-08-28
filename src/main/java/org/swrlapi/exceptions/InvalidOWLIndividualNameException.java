package org.swrlapi.exceptions;

public class InvalidOWLIndividualNameException extends SWRLFactoryException
{
	private static final long serialVersionUID = 1L;

	public InvalidOWLIndividualNameException(String name)
	{
		super("invalid individual name " + name);
	}
}
