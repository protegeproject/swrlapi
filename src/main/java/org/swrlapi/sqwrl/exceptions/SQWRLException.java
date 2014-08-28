package org.swrlapi.sqwrl.exceptions;

import org.swrlapi.exceptions.SWRLBuiltInException;

public class SQWRLException extends SWRLBuiltInException
{
	private static final long serialVersionUID = 1L;

	public SQWRLException(String message)
	{
		super(message);
	}

	public SQWRLException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
