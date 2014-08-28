package org.swrlapi.exceptions;

public class SWRLBuiltInNotImplementedException extends SWRLBuiltInException
{
	private static final long serialVersionUID = 1L;

	public SWRLBuiltInNotImplementedException()
	{
		super("built-in not yet implemented");
	}

	public SWRLBuiltInNotImplementedException(String message)
	{
		super("built-in not yet implemented: " + message);
	}
}
