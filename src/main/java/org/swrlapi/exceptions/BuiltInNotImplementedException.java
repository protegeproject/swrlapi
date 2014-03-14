package org.swrlapi.exceptions;

public class BuiltInNotImplementedException extends BuiltInException
{
	private static final long serialVersionUID = -7262158767998970466L;

	public BuiltInNotImplementedException()
	{
		super("built-in not yet implemented");
	}

	public BuiltInNotImplementedException(String message)
	{
		super("built-in not yet implemented: " + message);
	}
}
