package org.swrlapi.exceptions;

public class SWRLBuiltInException extends Exception
{
	private static final long serialVersionUID = 1L;

	public SWRLBuiltInException()
	{
		super();
	}

	public SWRLBuiltInException(String message)
	{
		super(message);
	}

	public SWRLBuiltInException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
