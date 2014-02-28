package org.protege.swrlapi.exceptions;

public class SWRLAPIException extends Exception
{
	private static final long serialVersionUID = 7546875909720217803L;

	public SWRLAPIException()
	{
		super();
	}

	public SWRLAPIException(String message)
	{
		super(message);
	}

	public SWRLAPIException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
