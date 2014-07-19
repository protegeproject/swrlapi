package org.swrlapi.exceptions;

public class TargetRuleEngineNotImplementedFeatureException extends TargetRuleEngineException
{
	private static final long serialVersionUID = 1L;

	public TargetRuleEngineNotImplementedFeatureException(String message)
	{
		super(message);
	}

	public TargetRuleEngineNotImplementedFeatureException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
