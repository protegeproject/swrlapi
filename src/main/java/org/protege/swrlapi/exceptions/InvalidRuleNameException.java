package org.protege.swrlapi.exceptions;

public class InvalidRuleNameException extends SWRLRuleEngineException
{
	private static final long serialVersionUID = -5027542614288847266L;

	public InvalidRuleNameException(String ruleName)
	{
		super("invalid rule name '" + ruleName + "'");
	}
}
