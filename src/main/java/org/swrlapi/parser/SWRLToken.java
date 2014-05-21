package org.swrlapi.parser;

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

	@Override
	public String toString()
	{
		return "[" + tokenType.getName() + "with value '" + value + "']";
	}

	public static enum SWRLTokenType {
		SHORTNAME("short name"), IRI("IRI"), STRING("quoted string"), DOUBLE("double"), LONG("long"), TYPE_QUAL("^^"), AND(
				"^"), IMP("->"), RING("."), LPAREN("("), RPAREN(")"), COMMA(","), QUESTION("?"), END_OF_INPUT("end");

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
