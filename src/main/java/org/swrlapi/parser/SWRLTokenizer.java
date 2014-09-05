package org.swrlapi.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tokenizer generates a {@link org.swrlapi.parser.SWRLParseException} for invalid input and
 * a {@link org.swrlapi.parser.SWRLIncompleteRuleException} (which is a subclass of
 * {@link org.swrlapi.parser.SWRLParseException}) for valid but incomplete input.
 *
 * @see org.swrlapi.parser.SWRLParser
 * @see org.swrlapi.parser.SWRLParseException
 * @see org.swrlapi.parser.SWRLIncompleteRuleException
 */
public class SWRLTokenizer
{
	private static final char wordChars[] = { ':', '_', '/', '#' };
	private static final char ordinaryChars[] = { '-', '.', '^', '<', '>', '(', ')', '?' };

	private final StreamTokenizer tokenizer;

	private final Set<String> swrlVariables;
	private final boolean interactiveParseOnly;
	private final List<SWRLToken> tokens;
	private int tokenPosition;

	public SWRLTokenizer(String input, boolean interactiveParseOnly) throws SWRLParseException
	{
		this.tokenizer = new StreamTokenizer(new StringReader(input));
		this.tokenizer.parseNumbers();

		this.swrlVariables = new HashSet<String>();
		this.interactiveParseOnly = interactiveParseOnly;

		for (int i = 0; i < wordChars.length; i++)
			this.tokenizer.wordChars(wordChars[i], wordChars[i]);

		for (int i = 0; i < ordinaryChars.length; i++)
			this.tokenizer.ordinaryChar(ordinaryChars[i]);

		this.tokens = generateTokens();
		this.tokenPosition = 0;
	}

	// If we want to list:
	// this.tokenizer.ordinaryChar('!');
	// // Skip double quote
	// this.tokenizer.ordinaryChars('$', '/');
	// // Skip colon
	// this.tokenizer.ordinaryChars(';', '@');
	// this.tokenizer.ordinaryChars('[', '^');
	// // Skip underscore
	// this.tokenizer.ordinaryChar('`');
	// this.tokenizer.ordinaryChars('{', '~');


	public void reset()
	{
		this.tokenPosition = 0;
	}

	public SWRLToken getToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			return this.tokens.get(tokenPosition++);
		else
			throw generateEndOfRuleException("Incomplete rule!");
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
		} else
			throw generateEndOfRuleException(unexpectedTokenMessage);
	}

	public SWRLToken getToken(String noTokenMessage) throws SWRLParseException
	{
		if (hasMoreTokens())
			return getToken();
		else
			throw generateEndOfRuleException(noTokenMessage);
	}

	public boolean hasMoreTokens()
	{
		return this.tokenPosition < this.tokens.size();
	}

	public SWRLToken peekToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			return this.tokens.get(tokenPosition);
		else
			throw generateEndOfRuleException("End of rule reached!");
	}

	public void skipToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			tokenPosition++;
		else
			throw generateEndOfRuleException("End of rule reached unexpectedly!");
	}

	public boolean isInteractiveParseOnly()
	{
		return this.interactiveParseOnly;
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
		} else
			throw generateEndOfRuleException(unexpectedTokenMessage);
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

	public static boolean isOrdinaryChar(char c)
	{
		for (int i = 0; i < ordinaryChars.length; i++)
			if (ordinaryChars[i] == c)
				return true;
		return false;
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
			return convertToken2SWRLToken(this.tokenizer.nextToken());
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
		case StreamTokenizer.TT_NUMBER: {
			double value = this.tokenizer.nval;
			if (value % 1 == 0.0) // Appears to be no way of determining whether double/float used originally
				return new SWRLToken(SWRLToken.SWRLTokenType.LONG, "" + this.tokenizer.nval);
			else
				return new SWRLToken(SWRLToken.SWRLTokenType.DOUBLE, "" + this.tokenizer.nval);
		}
		case StreamTokenizer.TT_WORD:
			return new SWRLToken(SWRLToken.SWRLTokenType.SHORTNAME, this.tokenizer.sval);
		case '"':
			return new SWRLToken(SWRLToken.SWRLTokenType.STRING, this.tokenizer.sval);
		case ',':
			return new SWRLToken(SWRLToken.SWRLTokenType.COMMA, ",");
		case '?':
			return new SWRLToken(SWRLToken.SWRLTokenType.QUESTION, "?");
		case '(':
			return new SWRLToken(SWRLToken.SWRLTokenType.LPAREN, "(");
		case ')':
			return new SWRLToken(SWRLToken.SWRLTokenType.RPAREN, ")");
		case '.':
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
		case '<': {
			int nextTokenType = this.tokenizer.nextToken();
			if (nextTokenType == StreamTokenizer.TT_WORD) {
				String iri = this.tokenizer.sval;
				nextTokenType = this.tokenizer.nextToken();
				if (nextTokenType == '>')
					return new SWRLToken(SWRLToken.SWRLTokenType.SHORTNAME, iri);
				else if (nextTokenType == StreamTokenizer.TT_EOF)
					throw generateEndOfRuleException("Expecting '>' after IRI");
				else
					throw new SWRLParseException("Expecting IRI after '<'");
			} else if (nextTokenType == StreamTokenizer.TT_EOF)
				throw generateEndOfRuleException("Expecting IRI after '<'");
			else
				throw new SWRLParseException("Expecting IRI after '<'"); // Some other token
		}
		case '-': {
			int nextTokenType = this.tokenizer.nextToken();
			if (nextTokenType == '>')
				return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
			else if (nextTokenType == StreamTokenizer.TT_EOF)
				throw generateEndOfRuleException("Expecting '>' after '-'");
			else
				throw new SWRLParseException("Expecting '>' after '-' for implication");
		}
		default:
			throw new SWRLParseException("Unexpected character '" + String.valueOf(Character.toChars(tokenType)) + "'");
		}

	}

	private SWRLParseException generateEndOfRuleException(String message)
	{
		if (!this.isInteractiveParseOnly())
			return new SWRLParseException(message);
		else
			return new SWRLIncompleteRuleException(message);
	}
}
