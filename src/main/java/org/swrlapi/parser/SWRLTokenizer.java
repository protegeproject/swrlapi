package org.swrlapi.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.swrlapi.parser.SWRLToken.SWRLTokenType;

public class SWRLTokenizer
{
	public final static char AND_CHAR = '\u2227'; // ^
	public final static char IMP_CHAR = '\u2192'; // >
	public final static char RING_CHAR = '\u02da'; // .

	private final StreamTokenizer tokenizer;

	private final Set<String> swrlVariables;
	private final boolean parseOnly;

	public SWRLTokenizer(String input, boolean parseOnly)
	{
		this.tokenizer = new StreamTokenizer(new StringReader(input));
		this.swrlVariables = new HashSet<String>();
		this.parseOnly = parseOnly;

		this.tokenizer.parseNumbers();
		this.tokenizer.wordChars(':', ':');
		this.tokenizer.ordinaryChar(AND_CHAR);
		this.tokenizer.ordinaryChar(IMP_CHAR);
		this.tokenizer.ordinaryChar(RING_CHAR);
		this.tokenizer.ordinaryChar('-');
		this.tokenizer.ordinaryChar('.');
		this.tokenizer.ordinaryChar('^');
		this.tokenizer.ordinaryChar('>');
		this.tokenizer.ordinaryChar('(');
		this.tokenizer.ordinaryChar(')');
		this.tokenizer.ordinaryChar('?');
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

	public SWRLToken getNextToken() throws SWRLParseException
	{
		try {
			return convertToken2SWRLToken(tokenizer.nextToken());
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	public SWRLToken getNextToken(SWRLToken.SWRLTokenType expectedTokenType, String unexpectedTokenMessage)
			throws SWRLParseException
	{
		try {
			int tokenType = tokenizer.nextToken();
			SWRLToken token = convertToken2SWRLToken(tokenType);
			if (token.getTokenType() == expectedTokenType)
				return token;
			else
				throw new SWRLParseException(unexpectedTokenMessage + ", found " + tokenizer.sval);
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	public SWRLToken getNextToken(String noTokenMessage) throws SWRLParseException
	{
		try {
			if (hasMoreTokens())
				return convertToken2SWRLToken(tokenizer.nextToken());
			else
				throw new SWRLParseException(noTokenMessage);
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	public SWRLToken peekNextToken(String noTokenMessage) throws SWRLParseException
	{
		try {
			int nextToken = this.tokenizer.nextToken();
			this.tokenizer.pushBack();
			if (nextToken != StreamTokenizer.TT_EOF)
				return convertToken2SWRLToken(nextToken);
			else
				throw new SWRLParseException(noTokenMessage);
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	public SWRLToken peekNextToken() throws SWRLParseException
	{
		try {
			int nextToken = this.tokenizer.nextToken();
			this.tokenizer.pushBack();
			return convertToken2SWRLToken(nextToken);
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	public boolean peekNextToken(SWRLTokenType tokenType) throws SWRLParseException
	{
		return peekNextToken().getTokenType() == tokenType;
	}

	private SWRLToken convertToken2SWRLToken(int tokenType) throws SWRLParseException, IOException
	{
		switch (tokenType) {
		case StreamTokenizer.TT_EOF:
		case StreamTokenizer.TT_EOL:
			return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
		case StreamTokenizer.TT_NUMBER:
			return new SWRLToken(SWRLToken.SWRLTokenType.NUMBER, "" + tokenizer.nval);
		case StreamTokenizer.TT_WORD:
			return new SWRLToken(SWRLToken.SWRLTokenType.IDENTIFIER, tokenizer.sval);
		case '"':
			return new SWRLToken(SWRLToken.SWRLTokenType.STRING, tokenizer.sval);
		case ',':
			return new SWRLToken(SWRLToken.SWRLTokenType.COMMA, ",");
		case '?':
			return new SWRLToken(SWRLToken.SWRLTokenType.QUESTION, ",");
		case '(':
			return new SWRLToken(SWRLToken.SWRLTokenType.LPAREN, "(");
		case ')':
			return new SWRLToken(SWRLToken.SWRLTokenType.RPAREN, ")");
		case '.':
		case RING_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.RING, ".");
		case '^':
			if (hasMoreTokens()) {
				int nextTokenType = tokenizer.nextToken();
				if (nextTokenType == '^') {
					return new SWRLToken(SWRLToken.SWRLTokenType.TYPE_QUAL, "^^");
				} else {
					this.tokenizer.pushBack();
					return new SWRLToken(SWRLToken.SWRLTokenType.AND, "^");
				}
			} else
				return new SWRLToken(SWRLToken.SWRLTokenType.AND, "^");
		case AND_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.AND, "^");
		case IMP_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
		case '-':
			if (!hasMoreTokens())
				return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
			else {
				int nextTokenType = tokenizer.nextToken();
				if (nextTokenType == '>')
					return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
				else
					throw new SWRLParseException("Expecting '>' after '-', got '" + nextTokenType + "'");
			}
		default:
			throw new SWRLParseException("Error tokenizing - unexpected token type " + tokenType);
		}
	}

	public void checkAndSkipToken(SWRLToken.SWRLTokenType tokenType, String unexpectedTokenMessage)
			throws SWRLParseException
	{
		SWRLToken token = getNextToken(unexpectedTokenMessage);

		if (token.getTokenType() != tokenType)
			throw new SWRLParseException("Expecting " + tokenType.getName() + ", got " + token + "; "
					+ unexpectedTokenMessage);
	}

	public boolean hasMoreTokens() throws SWRLParseException
	{
		try {
			int nextToken = this.tokenizer.nextToken();
			this.tokenizer.pushBack();
			return nextToken != StreamTokenizer.TT_EOF;
		} catch (IOException e) {
			throw new SWRLParseException("Error parsing rule " + e.getMessage());
		}
	}
}
