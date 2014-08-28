package org.swrlapi.exceptions;

public class TargetSWRLRuleEngineNotImplementedFeatureException extends TargetSWRLRuleEngineException
{
	private static final long serialVersionUID = 1L;

	public TargetSWRLRuleEngineNotImplementedFeatureException(String message)
	{
		super(message);
	}

	public TargetSWRLRuleEngineNotImplementedFeatureException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
