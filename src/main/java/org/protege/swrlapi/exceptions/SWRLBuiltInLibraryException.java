package org.protege.swrlapi.exceptions;

public class SWRLBuiltInLibraryException extends SWRLBuiltInBridgeException
{
	private static final long serialVersionUID = -8776632645188063525L;

	public SWRLBuiltInLibraryException(String message)
	{
		super(message);
	}

	public SWRLBuiltInLibraryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
