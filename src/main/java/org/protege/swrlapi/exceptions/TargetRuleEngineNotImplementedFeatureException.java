package org.protege.swrlapi.exceptions;

public class TargetRuleEngineNotImplementedFeatureException extends TargetRuleEngineException
{
	private static final long serialVersionUID = -83157273121976289L;

	public TargetRuleEngineNotImplementedFeatureException(String message)
	{
		super(message);
	}

	public TargetRuleEngineNotImplementedFeatureException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
