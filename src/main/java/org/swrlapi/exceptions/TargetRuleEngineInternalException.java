package org.swrlapi.exceptions;

public class TargetRuleEngineInternalException extends TargetRuleEngineException
{
	private static final long serialVersionUID = -8182109469976708219L;

	public TargetRuleEngineInternalException(String message)
	{
		super(message);
	}

	public TargetRuleEngineInternalException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
