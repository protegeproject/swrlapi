package org.swrlapi.parser;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;

/**
 * Defines a basic token class used by the {@link org.swrlapi.parser.SWRLTokenizer} and
 * {@link org.swrlapi.parser.SWRLParser}.
 *
 * @see org.swrlapi.parser.SWRLTokenizer
 * @see org.swrlapi.parser.SWRLParser
 */
class SWRLToken
{
  @NonNull private final SWRLTokenType tokenType;
  @NonNull private final String value;

  public SWRLToken(@NonNull SWRLTokenType tokenType, @NonNull String value)
  {
    this.tokenType = tokenType;
    this.value = value;
  }

  @NonNull public SWRLTokenType getTokenType()
  {
    return this.tokenType;
  }

  @NonNull public String getValue()
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

  public boolean isConjunction()
  {
    return this.tokenType == SWRLTokenType.CONJUNCTION;
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

  public boolean isInteger()
  {
    return this.tokenType == SWRLTokenType.INTEGER;
  }

  public boolean isDecimal()
  {
    return this.tokenType == SWRLTokenType.DECIMAL;
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

  public boolean isAnd()
  {
    return this.tokenType == SWRLTokenType.AND;
  }

  public boolean isOr()
  {
    return this.tokenType == SWRLTokenType.OR;
  }

  public boolean isNot()
  {
    return this.tokenType == SWRLTokenType.NOT;
  }

  public boolean isEndOfInput()
  {
    return this.tokenType == SWRLTokenType.END_OF_INPUT;
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "[" + this.tokenType.getName() + " with value '" + this.value + "']";
  }

  public enum SWRLTokenType
  {
    SHORTNAME("short name"), // A short name is a user-friendly name. Note: it can be a prefixed name or a full IRI.
    IRI("IRI"), STRING("quoted string"), DECIMAL("decimal"), INTEGER("integer"), TYPE_QUAL("^^"), CONJUNCTION("^"), IMP(
    "->"), RING("."), LPAREN("("), RPAREN(")"), COMMA(","), QUESTION("?"), AND("AND"), OR("OR"), NOT(
    "NOT"), END_OF_INPUT("end");

    @NonNull private final String name;

    SWRLTokenType(@NonNull String name)
    {
      this.name = name;
    }

    @NonNull public String getName()
    {
      return this.name;
    }
  }
}
