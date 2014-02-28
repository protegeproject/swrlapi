package org.protege.swrlapi.exceptions;

public class NoRegisteredRuleEnginesException extends SWRLRuleEngineBridgeException
{
	private static final long serialVersionUID = -7466254522301889416L;

	public NoRegisteredRuleEnginesException()
	{
		super("no registered rule engines");
	}
}
