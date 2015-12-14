package org.swrlapi.parser;

import org.checkerframework.checker.initialization.qual.UnderInitialization;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tokenizer generates a {@link org.swrlapi.parser.SWRLParseException} for invalid input and a
 * {@link org.swrlapi.parser.SWRLIncompleteRuleException} (which is a subclass of
 * {@link org.swrlapi.parser.SWRLParseException}) for valid but incomplete input.
 *
 * @see org.swrlapi.parser.SWRLParser
 * @see org.swrlapi.parser.SWRLParseException
 * @see org.swrlapi.parser.SWRLIncompleteRuleException
 */
class SWRLTokenizer
{
  @NonNull private static final char wordChars[] = { ':', '_', '/', '#' };
  @NonNull private static final char ordinaryChars[] = { '-', '.', '^', '<', '>', '(', ')', '?' };

  @NonNull private final MyStreamTokenizer tokenizer;

  @NonNull private final Set<@NonNull String> swrlVariables;
  @NonNull private final List<@NonNull SWRLToken> tokens;
  private final boolean interactiveParseOnly;
  private int tokenPosition;

  public SWRLTokenizer(@NonNull String input, boolean interactiveParseOnly) throws SWRLParseException
  {
    this.tokenizer = new MyStreamTokenizer(new StringReader(input));

    this.swrlVariables = new HashSet<>();
    this.interactiveParseOnly = interactiveParseOnly;

    for (char wordChar : wordChars)
      this.tokenizer.wordChars(wordChar, wordChar);

    this.tokenizer.wordChars('0', '9');

    for (char ordinaryChar : ordinaryChars)
      this.tokenizer.ordinaryChar(ordinaryChar);

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

  @NonNull public SWRLToken getToken(SWRLToken.SWRLTokenType expectedTokenType, @NonNull String unexpectedTokenMessage)
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

  @NonNull public SWRLToken getToken(@NonNull String noTokenMessage) throws SWRLParseException
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

  @NonNull public SWRLToken peekToken(@NonNull String message) throws SWRLParseException
  {
    if (this.tokenPosition < this.tokens.size())
      return this.tokens.get(this.tokenPosition);
    else
      throw generateEndOfRuleException(message);
  }

  public void skipToken() throws SWRLParseException
  {
    if (this.tokenPosition < this.tokens.size())
      this.tokenPosition++;
    else
      throw generateEndOfRuleException("End of rule reached unexpectedly!");
  }

  public boolean isInteractiveParseOnly()
  {
    return this.interactiveParseOnly;
  }

  public boolean hasVariable(@NonNull String variableName)
  {
    return this.swrlVariables.contains(variableName);
  }

  public void addVariable(@NonNull String variableName)
  {
    this.swrlVariables.add(variableName);
  }

  public void checkAndSkipLParen(@NonNull String unexpectedTokenMessage) throws SWRLParseException
  {
    checkAndSkipToken(SWRLToken.SWRLTokenType.LPAREN, unexpectedTokenMessage);
  }

  public void checkAndSkipRParen(@NonNull String unexpectedTokenMessage) throws SWRLParseException
  {
    checkAndSkipToken(SWRLToken.SWRLTokenType.RPAREN, unexpectedTokenMessage);
  }

  public void checkAndSkipComma(@NonNull String unexpectedTokenMessage) throws SWRLParseException
  {
    checkAndSkipToken(SWRLToken.SWRLTokenType.COMMA, unexpectedTokenMessage);
  }

  public static boolean isOrdinaryChar(char c)
  {
    for (char ordinaryChar : ordinaryChars) {
      if (ordinaryChar == c)
        return true;
    }
    return false;
  }

  @NonNull private SWRLToken getToken() throws SWRLParseException
  {
    if (this.tokenPosition < this.tokens.size())
      return this.tokens.get(this.tokenPosition++);
    else
      throw generateEndOfRuleException("Incomplete rule!");
  }

  private void checkAndSkipToken(SWRLToken.SWRLTokenType tokenType, @NonNull String unexpectedTokenMessage)
    throws SWRLParseException
  {
    if (hasMoreTokens()) {
      SWRLToken token = getToken();

      if (token.getTokenType() != tokenType)
        throw new SWRLParseException(unexpectedTokenMessage + ", got '" + token.getValue() + "'");
    } else
      throw generateEndOfRuleException(unexpectedTokenMessage);
  }

  @UnderInitialization @NonNull private List<@NonNull SWRLToken> generateTokens() throws SWRLParseException
  {
    List<@NonNull SWRLToken> tokens = new ArrayList<>();
    SWRLToken token = generateToken();
    while (token.getTokenType() != SWRLToken.SWRLTokenType.END_OF_INPUT) {
      tokens.add(token);
      token = generateToken();
    }
    return tokens;
  }

  @NonNull private SWRLToken generateToken() throws SWRLParseException
  {
    try {
      return convertToken2SWRLToken(this.tokenizer.nextToken());
    } catch (IOException e) {
      throw new SWRLParseException("Error tokenizing " + (e.getMessage() != null ? e.getMessage() : ""));
    }
  }

  @NonNull private SWRLToken convertToken2SWRLToken(int tokenType) throws SWRLParseException, IOException
  {
    boolean negativeNumeric = false;

    switch (tokenType) {
    case StreamTokenizer.TT_EOF:
      return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
    case StreamTokenizer.TT_EOL:
      return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
    case StreamTokenizer.TT_NUMBER:
      throw new SWRLParseException("internal error - not expecting a StreamTokenizer.TT_NUMBER");
    case '-': {
      int nextTokenType = this.tokenizer.nextToken();
      if (nextTokenType == '>')
        return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
      else if (nextTokenType == StreamTokenizer.TT_EOF)
        throw generateEndOfRuleException("Expecting '>' or integer or float after '-'");
      else if (nextTokenType != StreamTokenizer.TT_WORD)
        throw new SWRLParseException("Expecting '>' or integer or float after '-'");
      else
        negativeNumeric = true;
      // Fall through to look for integer or float
    }
    case StreamTokenizer.TT_WORD: {
      String value = this.tokenizer.getValue();
      if (isInt(value)) {
        // See if it is followed by a '.', in which case it should be a float
        if (this.tokenizer.nextToken() == '.') { // Found a . so expecting rest of float
          int trailingTokenType = this.tokenizer.nextToken();
          String trailingValue = this.tokenizer.getValue();
          if (trailingTokenType == StreamTokenizer.TT_WORD && isInt(trailingValue)) {
            String floatValue = value + "." + trailingValue;
            floatValue = negativeNumeric ? "-" + floatValue : floatValue;
            return new SWRLToken(SWRLToken.SWRLTokenType.FLOAT, floatValue);
          } else if (trailingTokenType == StreamTokenizer.TT_EOF)
            throw generateEndOfRuleException("Expecting float fraction part after '.'");
          else
            throw new SWRLParseException("Expecting float fraction part after '.'");
        } else { // No following '.' so it is an integer
          this.tokenizer.pushBack();
          String intValue = negativeNumeric ? "-" + value : value;
          return new SWRLToken(SWRLToken.SWRLTokenType.INT, intValue);
        }
      } else { // Value is not an integer
        if (negativeNumeric) // If negative, value should be an integer
          throw new SWRLParseException("Expecting integer or float");
        else
          return new SWRLToken(SWRLToken.SWRLTokenType.SHORTNAME, value);
      }
    }
    case '"':
      return new SWRLToken(SWRLToken.SWRLTokenType.STRING, this.tokenizer.getValue());
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
      int nextTokenType = this.tokenizer.nextToken();
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
        String iri = this.tokenizer.getValue();
        nextTokenType = this.tokenizer.nextToken();
        if (nextTokenType == '>')
          return new SWRLToken(SWRLToken.SWRLTokenType.IRI, iri);
        else if (nextTokenType == StreamTokenizer.TT_EOF)
          throw generateEndOfRuleException("Expecting '>' after IRI");
        else
          throw new SWRLParseException("Expecting IRI after '<'");
      } else if (nextTokenType == StreamTokenizer.TT_EOF)
        throw generateEndOfRuleException("Expecting IRI after '<'");
      else
        throw new SWRLParseException("Expecting IRI after '<'"); // Some other token
    }
    default:
      throw new SWRLParseException("Unexpected character '" + String.valueOf(Character.toChars(tokenType)) + "'");
    }

  }

  @NonNull private SWRLParseException generateEndOfRuleException(@NonNull String message)
  {
    if (!this.isInteractiveParseOnly())
      return new SWRLParseException(message);
    else
      return new SWRLIncompleteRuleException(message);
  }

  private boolean isInt(@NonNull String s)
  {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private class MyStreamTokenizer extends StreamTokenizer
  {
    public MyStreamTokenizer(@NonNull Reader r)
    {
      super(r);
    }

    @Override public void parseNumbers()
    {
    }

    @NonNull String getValue()
    {
      if (super.sval != null)
        return super.sval;
      else
        return "";
    }
  }
}
