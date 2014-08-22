package org.swrlapi.parser;

/**
 * @see org.swrlapi.parser.SWRLParser
 */
public class SWRLIncompleteRuleException extends SWRLParseException
{
	private static final long serialVersionUID = 1L;

	public SWRLIncompleteRuleException() {}

	public SWRLIncompleteRuleException(String message)
	{
		super(message);
	}
}
