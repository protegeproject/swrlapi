package org.swrlapi.exceptions;

public class SWRLBuiltInBridgeException extends BuiltInException
{
	private static final long serialVersionUID = -7206948884432408214L;

	public SWRLBuiltInBridgeException(String message)
	{
		super(message);
	}

	public SWRLBuiltInBridgeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
