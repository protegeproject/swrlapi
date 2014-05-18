package org.swrlapi.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SWRLTokenizer
{
	public final static char AND_CHAR = '\u2227'; // ^
	public final static char IMP_CHAR = '\u2192'; // >
	public final static char RING_CHAR = '\u02da'; // .

	private final StreamTokenizer tokenizer;

	private final Set<String> swrlVariables;
	private final boolean parseOnly;
	private final List<SWRLToken> tokens;
	private int tokenPosition;

	public SWRLTokenizer(String input, boolean parseOnly) throws SWRLParseException
	{
		this.tokenizer = new StreamTokenizer(new StringReader(input));
		this.tokenizer.parseNumbers();
		this.tokenizer.wordChars(':', ':');
		this.tokenizer.wordChars('_', '_');
		this.tokenizer.wordChars('/', '/');
		this.tokenizer.wordChars('#', '#');
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

		this.swrlVariables = new HashSet<String>();
		this.parseOnly = parseOnly;

		this.tokens = generateTokens();
		this.tokenPosition = 0;
	}

	public void reset()
	{
		this.tokenPosition = 0;
	}

	public SWRLToken getToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			return this.tokens.get(tokenPosition++);
		else
			throw new SWRLParseException("No more tokens!");
	}

	public SWRLToken getToken(SWRLToken.SWRLTokenType expectedTokenType, String unexpectedTokenMessage)
			throws SWRLParseException
	{
		if (hasMoreTokens()) {
			SWRLToken token = getToken();

			if (token.getTokenType() == expectedTokenType)
				return token;
			else
				throw new SWRLParseException(unexpectedTokenMessage);
		} else {
			if (!isParseOnly())
				throw new SWRLParseException(unexpectedTokenMessage + ", got end of rule");
			else
				throw new SWRLIncompleteRuleException(unexpectedTokenMessage + ", got end of rule");
		}
	}

	public SWRLToken getToken(String noTokenMessage) throws SWRLParseException
	{
		if (hasMoreTokens())
			return getToken();
		else {
			if (!isParseOnly())
				throw new SWRLParseException(noTokenMessage);
			else
				throw new SWRLIncompleteRuleException(noTokenMessage);
		}
	}

	public boolean hasMoreTokens()
	{
		return this.tokenPosition < this.tokens.size();
	}

	public SWRLToken peekToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			return this.tokens.get(tokenPosition);
		else {
			if (!isParseOnly())
				throw new SWRLParseException("Unexpectedly reached end of rule!");
			else
				throw new SWRLIncompleteRuleException("Rule incomplete!");
		}
	}

	public void skipToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			tokenPosition++;
		else {
			if (!isParseOnly())
				throw new SWRLParseException("Unexpectedly reached end of rule!");
			else
				throw new SWRLIncompleteRuleException("Rule incomplete!");
		}
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

	public void checkAndSkipToken(SWRLToken.SWRLTokenType tokenType, String unexpectedTokenMessage)
			throws SWRLParseException
	{
		if (hasMoreTokens()) {
			SWRLToken token = getToken();

			if (token.getTokenType() != tokenType)
				throw new SWRLParseException(unexpectedTokenMessage + ", got '" + token.getValue() + "'");
		} else {
			if (!isParseOnly())
				throw new SWRLParseException(unexpectedTokenMessage + ", got end of rule");
			else
				throw new SWRLIncompleteRuleException(unexpectedTokenMessage + ", got end of rule");
		}
	}

	public void checkAndSkipLParen(String unexpectedTokenMessage) throws SWRLParseException
	{
		checkAndSkipToken(SWRLToken.SWRLTokenType.LPAREN, unexpectedTokenMessage);
	}

	public void checkAndSkipRParen(String unexpectedTokenMessage) throws SWRLParseException
	{
		checkAndSkipToken(SWRLToken.SWRLTokenType.RPAREN, unexpectedTokenMessage);
	}

	public void checkAndSkipComma(String unexpectedTokenMessage) throws SWRLParseException
	{
		checkAndSkipToken(SWRLToken.SWRLTokenType.COMMA, unexpectedTokenMessage);
	}

	private List<SWRLToken> generateTokens() throws SWRLParseException
	{
		List<SWRLToken> tokens = new ArrayList<SWRLToken>();
		SWRLToken token = generateToken();
		while (token.getTokenType() != SWRLToken.SWRLTokenType.END_OF_INPUT) {
			tokens.add(token);
			token = generateToken();
		}
		return tokens;
	}

	private SWRLToken generateToken() throws SWRLParseException
	{
		try {
			return convertToken2SWRLToken(tokenizer.nextToken());
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	private SWRLToken convertToken2SWRLToken(int tokenType) throws SWRLParseException, IOException
	{
		switch (tokenType) {
		case StreamTokenizer.TT_EOF:
			return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
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
		case '^': {
			int nextTokenType = tokenizer.nextToken();
			if (nextTokenType == '^') {
				return new SWRLToken(SWRLToken.SWRLTokenType.TYPE_QUAL, "^^");
			} else { // Not ^^
				this.tokenizer.pushBack();
				return new SWRLToken(SWRLToken.SWRLTokenType.AND, "^");
			}
		}
		case AND_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.AND, "^");
		case IMP_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
		case '-': {
			int nextTokenType = tokenizer.nextToken();
			if (nextTokenType == '>')
				return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
			else if (nextTokenType == StreamTokenizer.TT_EOF)
				throw new SWRLIncompleteRuleException("Expecting '>' after '-', got end of rule");
			else
				throw new SWRLParseException("Expecting '>' after '-'");
		}
		default:
			throw new SWRLParseException("Error tokenizing - unexpected token '" + tokenizer.sval + "' with type "
					+ tokenType);
		}
	}

}
