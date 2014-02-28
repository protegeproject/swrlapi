package org.protege.swrlapi.exceptions;

public class InvalidBuiltInNameException extends BuiltInException
{
	private static final long serialVersionUID = -4455559655709542627L;

	public InvalidBuiltInNameException(String ruleName, String builtInName)
	{
		super("unknown built-in '" + builtInName + "' in rule '" + ruleName + "'.");
	}

	public InvalidBuiltInNameException(String builtInName)
	{
		super("unknown built-in '" + builtInName + "': ");
	}
}
