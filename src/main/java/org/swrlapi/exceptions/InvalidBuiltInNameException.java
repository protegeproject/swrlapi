package org.swrlapi.exceptions;

public class InvalidBuiltInNameException extends BuiltInException
{
	private static final long serialVersionUID = 1L;

	public InvalidBuiltInNameException(String ruleName, String builtInName)
	{
		super("unknown built-in '" + builtInName + "' in rule '" + ruleName + "'.");
	}

	public InvalidBuiltInNameException(String builtInName)
	{
		super("unknown built-in '" + builtInName + "': ");
	}
}
