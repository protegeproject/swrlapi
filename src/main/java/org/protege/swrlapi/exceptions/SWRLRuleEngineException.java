package org.protege.swrlapi.exceptions;

public class SWRLRuleEngineException extends SWRLAPIException
{
	private static final long serialVersionUID = -7536191673961100220L;

	public SWRLRuleEngineException()
	{
		super();
	}

	public SWRLRuleEngineException(String message)
	{
		super(message);
	}

	public SWRLRuleEngineException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
