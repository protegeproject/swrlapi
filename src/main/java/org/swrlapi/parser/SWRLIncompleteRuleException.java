package org.swrlapi.parser;

public class SWRLIncompleteRuleException extends SWRLParseException
{
	private static final long serialVersionUID = 1L;

	public SWRLIncompleteRuleException()
	{
	}

	public SWRLIncompleteRuleException(String message)
	{
		super(message);
	}
}
