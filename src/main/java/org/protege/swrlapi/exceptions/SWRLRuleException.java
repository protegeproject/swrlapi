package org.protege.swrlapi.exceptions;

public class SWRLRuleException extends BuiltInException
{
	private static final long serialVersionUID = 5679262717731343239L;

	public SWRLRuleException(String message)
	{
		super(message);
	}

	public SWRLRuleException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
