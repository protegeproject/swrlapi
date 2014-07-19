package org.swrlapi.exceptions;

public class NoRegisteredRuleEnginesException extends SWRLRuleEngineBridgeException
{
	private static final long serialVersionUID = 1L;

	public NoRegisteredRuleEnginesException()
	{
		super("no registered rule engines");
	}
}
