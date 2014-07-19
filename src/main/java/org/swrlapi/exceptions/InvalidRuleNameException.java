package org.swrlapi.exceptions;

public class InvalidRuleNameException extends SWRLRuleEngineException
{
	private static final long serialVersionUID = -1L;

	public InvalidRuleNameException(String ruleName)
	{
		super("invalid rule name '" + ruleName + "'");
	}
}
