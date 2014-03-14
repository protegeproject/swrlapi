package org.swrlapi.sqwrl.exceptions;

import org.swrlapi.exceptions.BuiltInException;

public class SQWRLException extends BuiltInException
{
	private static final long serialVersionUID = -7828511121047813348L;

	public SQWRLException(String message)
	{
		super(message);
	}

	public SQWRLException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
