package org.swrlapi.exceptions;

public class SWRLFactoryException extends SWRLAPIException
{
	private static final long serialVersionUID = 1703046918948676710L;

	public SWRLFactoryException(String message)
	{
		super(message);
	}

	public SWRLFactoryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
