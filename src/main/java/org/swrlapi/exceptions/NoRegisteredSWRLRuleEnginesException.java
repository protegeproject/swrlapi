package org.swrlapi.exceptions;

public class NoRegisteredSWRLRuleEnginesException extends SWRLRuleEngineBridgeException
{
	private static final long serialVersionUID = 1L;

	public NoRegisteredSWRLRuleEnginesException()
	{
		super("no registered SWRL rule engines");
	}
}
