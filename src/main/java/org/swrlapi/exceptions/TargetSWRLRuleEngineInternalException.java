package org.swrlapi.exceptions;

public class TargetSWRLRuleEngineInternalException extends TargetSWRLRuleEngineException
{
	private static final long serialVersionUID = 1L;

	public TargetSWRLRuleEngineInternalException(String message)
	{
		super(message);
	}

	public TargetSWRLRuleEngineInternalException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
