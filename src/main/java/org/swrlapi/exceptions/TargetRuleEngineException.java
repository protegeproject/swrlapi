package org.swrlapi.exceptions;

public class TargetRuleEngineException extends SWRLAPIException
{
	private static final long serialVersionUID = 1L;

	public TargetRuleEngineException()
	{
		super();
	}

	public TargetRuleEngineException(String message)
	{
		super(message);
	}

	public TargetRuleEngineException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
