package org.swrlapi.parser;

/**
 * Defines a basic token class used by the {@link org.swrlapi.parser.SWRLTokenizer)} and
 * {@link org.swrlapi.parser.SWRLParser}.
 *
 * @see org.swrlapi.parser.SWRLTokenizer
 * @see org.swrlapi.parser.SWRLParser
 */
public class SWRLToken
{
	private final SWRLTokenType tokenType;
	private final String value;

	public SWRLToken(SWRLTokenType tokenType, String value)
	{
		this.tokenType = tokenType;
		this.value = value;
	}

	public SWRLTokenType getTokenType()
	{
		return this.tokenType;
	}

	public String getValue()
	{
		return this.value;
	}

	public boolean isImp()
	{
		return this.tokenType == SWRLTokenType.IMP;
	}

	public boolean isRing()
	{
		return this.tokenType == SWRLTokenType.RING;
	}

	public boolean isAnd()
	{
		return this.tokenType == SWRLTokenType.AND;
	}

	public boolean isString()
	{
		return this.tokenType == SWRLTokenType.STRING;
	}

	public boolean isShortName()
	{
		return this.tokenType == SWRLTokenType.SHORTNAME;
	}

	public boolean isIRI()
	{
		return this.tokenType == SWRLTokenType.IRI;
	}

	public boolean isInt()
	{
		return this.tokenType == SWRLTokenType.INT;
	}

	public boolean isFloat()
	{
		return this.tokenType == SWRLTokenType.FLOAT;
	}

	public boolean isTypeQualifier()
	{
		return this.tokenType == SWRLTokenType.TYPE_QUAL;
	}

	public boolean isLParen()
	{
		return this.tokenType == SWRLTokenType.LPAREN;
	}

	public boolean isRParen()
	{
		return this.tokenType == SWRLTokenType.RPAREN;
	}

	public boolean isComma()
	{
		return this.tokenType == SWRLTokenType.COMMA;
	}

	public boolean isQuestion()
	{
		return this.tokenType == SWRLTokenType.QUESTION;
	}

	public boolean isEndOfInput()
	{
		return this.tokenType == SWRLTokenType.END_OF_INPUT;
	}

	@Override
	public String toString()
	{
		return "[" + tokenType.getName() + " with value '" + value + "']";
	}

	public static enum SWRLTokenType {
		SHORTNAME("short name"), // A short name is a user-friendly name. Note: it can be a prefixed name or a full IRI.
		IRI("IRI"), STRING("quoted string"), FLOAT("float"), INT("int"), TYPE_QUAL("^^"), AND("^"), IMP("->"), RING("."), LPAREN(
				"("), RPAREN(")"), COMMA(","), QUESTION("?"), END_OF_INPUT("end");

		private final String name;

		private SWRLTokenType(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return this.name;
		}
	}
}
