package org.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidAggregateFunctionNameException extends SQWRLException
{
	private static final long serialVersionUID = 3331304070843550524L;

	public SQWRLInvalidAggregateFunctionNameException(String message)
	{
		super(message);
	}

	public SQWRLInvalidAggregateFunctionNameException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
