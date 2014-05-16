package org.swrlapi.parser;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

public class SWRLTokenizer
{
	public final static char AND_CHAR = '\u2227'; // ^
	public final static char IMP_CHAR = '\u2192'; // >
	public final static char RING_CHAR = '\u02da'; // .
	public final static String delimiters = " ?\n\t()[],\"'" + AND_CHAR + IMP_CHAR + RING_CHAR; // Note space

	private final StringTokenizer internalTokenizer;

	private final Set<String> swrlVariables;
	private final boolean parseOnly;

	public SWRLTokenizer(String input, boolean parseOnly)
	{
		this.internalTokenizer = new StringTokenizer(input, delimiters, true);
		this.swrlVariables = new HashSet<String>();
		this.parseOnly = parseOnly;
	}

	public boolean isParseOnly()
	{
		return this.parseOnly;
	}

	public boolean hasVariable(String variableName)
	{
		return this.swrlVariables.contains(variableName);
	}

	public void addVariable(String variableName)
	{
		this.swrlVariables.add(variableName);
	}

	public boolean hasMoreTokens()
	{
		return this.internalTokenizer.hasMoreTokens();
	}

	public String nextToken(String myDelimiters)
	{
		return this.internalTokenizer.nextToken(myDelimiters);
	}

	public String nextToken() throws NoSuchElementException
	{
		String token = this.internalTokenizer.nextToken(delimiters);
		if (!token.equals("'"))
			return token;

		StringBuffer buffer = new StringBuffer();
		while (this.internalTokenizer.hasMoreTokens() && !(token = this.internalTokenizer.nextToken()).equals("'")) {
			buffer.append(token);
		}
		return buffer.toString();
	}

	public String getNextNonSpaceToken(String noTokenMessage) throws SWRLParseException
	{
		String token = "";
		String errorMessage = "Incomplete rule. " + noTokenMessage;

		if (!hasMoreTokens()) {
			if (parseOnly)
				throw new SWRLIncompleteRuleException(errorMessage);
			else
				throw new SWRLParseException(errorMessage);
		}

		while (hasMoreTokens()) {
			token = nextToken();
			if (!(token.equals(" ") || token.equals("\n") || token.equals("\t")))
				return token;
		}

		if (parseOnly)
			throw new SWRLIncompleteRuleException(errorMessage);
		else
			throw new SWRLParseException(errorMessage); // Should not get here
	}

	public void checkAndSkipToken(String skipToken, String unexpectedTokenMessage) throws SWRLParseException
	{
		String token = getNextNonSpaceToken(unexpectedTokenMessage);

		if (!token.equalsIgnoreCase(skipToken))
			throw new SWRLParseException("Expecting " + skipToken + ", got " + token + "; " + unexpectedTokenMessage);
	}

	public String getNextStringToken(String noTokenMessage) throws SWRLParseException
	{ // TODO Does not deal with escaped quotation characters
		String errorMessage = "Incomplete rule. " + noTokenMessage;

		if (!hasMoreTokens()) {
			if (parseOnly)
				throw new SWRLIncompleteRuleException(errorMessage);
			else
				throw new SWRLParseException(errorMessage);
		}

		while (hasMoreTokens()) {
			String token = nextToken("\"");
			if (token.equals("\""))
				token = ""; // Empty string
			else
				checkAndSkipToken("\"", "Expected \" to close string.");
			return token;
		}

		if (parseOnly)
			throw new SWRLIncompleteRuleException(errorMessage);
		else
			throw new SWRLParseException(errorMessage); // Should not get here
	}
}
