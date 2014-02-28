package org.protege.swrlapi.exceptions;

public class InfiniteBindingBuiltInException extends BuiltInException
{
	private static final long serialVersionUID = 8633346352864873441L;

	public InfiniteBindingBuiltInException()
	{
		super("infinite bindings would be required to satisfy this built-in predicate");
	}

	public InfiniteBindingBuiltInException(String builtInName, String message)
	{
		super("infinite bindings would be required to satisfy this built-in " + builtInName + ": " + message);
	}
}
