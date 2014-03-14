package org.swrlapi.sqwrl.exceptions;


public class SQWRLInvalidRowIndexException extends SQWRLException
{
	private static final long serialVersionUID = 3523679345644248395L;

	public SQWRLInvalidRowIndexException(String message)
	{
		super(message);
	}

	public SQWRLInvalidRowIndexException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
