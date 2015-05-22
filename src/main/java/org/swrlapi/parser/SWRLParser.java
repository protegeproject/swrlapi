package org.swrlapi.parser;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.core.SWRLAPIOWLOntology;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A basic SWRL and SQWRL parser. It provides in interactive parsing mode for incomplete rules and queries and provides
 * feedback on the next token that it is expecting.
 * <p>
 * This parser will throw a {@link org.swrlapi.parser.SWRLParseException} if there is an error in the rule or query. In
 * interactive parse mode, if the rule or query is correct but incomplete a
 * {@link org.swrlapi.parser.SWRLIncompleteRuleException} (which is a subclass of
 * {@link org.swrlapi.parser.SWRLParseException}) will be thrown.
 * <p>
 * The {@link #parseSWRLRule(String, boolean, String, String)} method parses a rule or query. If
 * <code>interactiveParseOnly</code> argument is <code>true</code>, only checking is performed - no SWRL rules are
 * created; if it is false, a {@link org.semanticweb.owlapi.model.SWRLRule} object is created.
 * <p>
 * The parser does not yet parse OWL class expressions or data ranges.
 *
 * @see org.semanticweb.owlapi.model.SWRLRule
 * @see org.swrlapi.parser.SWRLTokenizer
 * @see org.swrlapi.parser.SWRLParserSupport
 * @see org.swrlapi.parser.SWRLParseException
 * @see org.swrlapi.parser.SWRLIncompleteRuleException
 */
public class SWRLParser
{
  public final static char AND_CHAR = '\u2227'; // ^
  public final static char IMP_CHAR = '\u2192'; // >
  public final static char RING_CHAR = '\u02da'; // .

  private static final String SAME_AS_PREDICATE = "sameAs";
  private static final String DIFFERENT_FROM_PREDICATE = "differentFrom";

  @NonNull private final SWRLParserSupport swrlParserSupport;

  public SWRLParser(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    this.swrlParserSupport = new SWRLParserSupport(swrlapiOWLOntology);
  }

  /**
   * @param ruleText             The rule text
   * @param interactiveParseOnly If True simply parse
   * @param ruleName             The rule name
   * @param comment              A comment
   * @return The parsed rule
   * @throws SWRLParseException If an error occurs during parsing
   */
  public Optional<SWRLRule> parseSWRLRule(@NonNull String ruleText, boolean interactiveParseOnly,
    @NonNull String ruleName, @NonNull String comment) throws SWRLParseException
  {
    SWRLTokenizer tokenizer = new SWRLTokenizer(ruleText.trim(), interactiveParseOnly);
    Optional<Set<SWRLAtom>> head = !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLHeadAtomList()) :
      Optional.<Set<SWRLAtom>>empty();
    Optional<Set<SWRLAtom>> body = !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLBodyAtomList()) :
      Optional.<Set<SWRLAtom>>empty();
    boolean atLeastOneAtom = false, justProcessedAtom = false, isInHead = false;
    String message;

    if (!tokenizer.isInteractiveParseOnly() && !tokenizer.hasMoreTokens())
      throw new SWRLParseException("Empty!");

    do {
      if (justProcessedAtom)
        message = isInHead ? "Expecting " + AND_CHAR : "Expecting " + IMP_CHAR + ", " + AND_CHAR + " or " + RING_CHAR;
      else
        message = isInHead ? "Expecting atom" : "Expecting atom," + IMP_CHAR + " or " + RING_CHAR;

      SWRLToken currentToken = tokenizer.getToken(message);

      if (currentToken.isImp()) { // An empty body is ok
        if (isInHead)
          throw new SWRLParseException("Second occurrence of ^");
        isInHead = true;
      } else if (currentToken.isAnd()) {
        if (!justProcessedAtom)
          throw new SWRLParseException("^ may occur only after an atom");
      } else if (currentToken.isRing()) {
        if (isInHead)
          throw new SWRLParseException(". may only occur in query body");
      } else if (currentToken.isShortName()) {
        String shortName = currentToken.getValue();
        Optional<? extends SWRLAtom> atom = parseSWRLAtom(shortName, tokenizer, isInHead);
        atLeastOneAtom = true;
        if (!tokenizer.isInteractiveParseOnly()) {
          if (isInHead)
            head.get().add(atom.get());
          else
            body.get().add(atom.get());
        }
      } else if (currentToken.isIRI()) {
        String shortName = this.swrlParserSupport
          .getShortNameFromIRI(currentToken.getValue(), tokenizer.isInteractiveParseOnly());
        Optional<? extends SWRLAtom> atom = parseSWRLAtom(shortName, tokenizer, isInHead);
        atLeastOneAtom = true;
        if (!tokenizer.isInteractiveParseOnly()) {
          if (isInHead)
            head.get().add(atom.get());
          else
            body.get().add(atom.get());
        }
      } else
        throw new SWRLParseException("Unexpected token '" + currentToken.getValue() + "'");
      justProcessedAtom = true;
    } while (tokenizer.hasMoreTokens());

    if (!tokenizer.isInteractiveParseOnly()) {
      if (!atLeastOneAtom)
        throw new SWRLParseException("Incomplete SWRL rule - no antecedent or consequent");
      return Optional.of(this.swrlParserSupport.getSWRLRule(ruleName, head.get(), body.get(), comment, true));
    } else
      return Optional.empty();
  }

  /**
   * If the rule is correct though possibly incomplete return <code>true</code>; if the rule has errors return
   * <code>false</code>.
   *
   * @param ruleText The rule text
   * @return True if the rule is valid but possibly incomplete
   */
  public boolean isSWRLRuleCorrectButPossiblyIncomplete(@NonNull String ruleText)
  {
    try {
      parseSWRLRule(ruleText, true, "", "");
      return true;
    } catch (SWRLIncompleteRuleException e) {
      return true;
    } catch (SWRLParseException e) {
      return false;
    }
  }

  /**
   * If the rule is correct and complete return <code>true</code>; if the rule has errors or is incomplete return
   * <code>false</code>.
   *
   * @param ruleText The rule text
   * @return True is the rule is correct and complete
   */
  public boolean isSWRLRuleCorrectAndComplete(@NonNull String ruleText)
  {
    try {
      parseSWRLRule(ruleText, false, "", "");
      return true;
    } catch (SWRLParseException e) {
      return false;
    }
  }

  private Optional<? extends SWRLAtom> parseSWRLAtom(@NonNull String shortName, @NonNull SWRLTokenizer tokenizer,
    boolean isInHead) throws SWRLParseException
  {
    if (shortName.equalsIgnoreCase(SAME_AS_PREDICATE)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for same individual atom");
      return parseSWRLSameAsAtomArguments(tokenizer, isInHead);
    } else if (shortName.equalsIgnoreCase(DIFFERENT_FROM_PREDICATE)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for different individuals atom");
      return parseSWRLDifferentFromAtomArguments(tokenizer, isInHead);
    } else if (this.swrlParserSupport.isOWLClass(shortName)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for class atom");
      return parseSWRLClassAtomArguments(shortName, tokenizer, isInHead);
    } else if (this.swrlParserSupport.isOWLObjectProperty(shortName)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for object property atom");
      return parseSWRLObjectPropertyAtomArguments(shortName, tokenizer, isInHead);
    } else if (this.swrlParserSupport.isOWLDataProperty(shortName)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for data property atom");
      return parseSWRLDataPropertyAtomArguments(shortName, tokenizer, isInHead);
    } else if (this.swrlParserSupport.isSWRLBuiltIn(shortName)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for built-in atom");
      return parseSWRLBuiltinAtomArguments(shortName, tokenizer, isInHead);
    } else
      throw generateEndOfRuleException("Invalid SWRL atom predicate '" + shortName + "'", tokenizer);
  }

  private Optional<SWRLClassAtom> parseSWRLClassAtomArguments(@NonNull String shortName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends SWRLIArgument> iArgument = parseSWRLIArgument(tokenizer, isInHead);

    tokenizer.checkAndSkipRParen("Expecting closing parenthesis for argument for class atom " + shortName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLClassAtom(shortName, iArgument.get())) :
      Optional.<SWRLClassAtom>empty();
  }

  private Optional<SWRLObjectPropertyAtom> parseSWRLObjectPropertyAtomArguments(@NonNull String shortName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends SWRLIArgument> iArgument1 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipComma("Expecting comma-separated second argument for object property atom " + shortName);
    Optional<? extends SWRLIArgument> iArgument2 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer
      .checkAndSkipRParen("Expecting closing parenthesis after second argument of object property atom " + shortName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLObjectPropertyAtom(shortName, iArgument1.get(), iArgument2.get())) :
      Optional.<SWRLObjectPropertyAtom>empty();
  }

  private Optional<SWRLDataPropertyAtom> parseSWRLDataPropertyAtomArguments(@NonNull String shortName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends SWRLIArgument> iArgument = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipComma("Expecting comma-separated second parameter for data property atom " + shortName);
    Optional<? extends SWRLDArgument> dArgument = parseSWRLDArgument(tokenizer, isInHead, false);
    tokenizer
      .checkAndSkipRParen("Expecting closing parenthesis after second argument of data property atom " + shortName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLDataPropertyAtom(shortName, iArgument.get(), dArgument.get())) :
      Optional.<SWRLDataPropertyAtom>empty();
  }

  private Optional<SWRLBuiltInAtom> parseSWRLBuiltinAtomArguments(@NonNull String builtInPrefixedName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<List<SWRLDArgument>> dArgumentList = parseSWRLDArgumentList(tokenizer, isInHead); // Swallows ')'

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLBuiltInAtom(builtInPrefixedName, dArgumentList.get())) :
      Optional.<SWRLBuiltInAtom>empty();
  }

  private Optional<SWRLSameIndividualAtom> parseSWRLSameAsAtomArguments(@NonNull SWRLTokenizer tokenizer,
    boolean isInHead) throws SWRLParseException
  {
    Optional<? extends SWRLIArgument> iArgument1 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipComma("Expecting comma-separated second argument for same individual atom");
    Optional<? extends SWRLIArgument> iArgument2 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to same individual atom");

    return tokenizer.isInteractiveParseOnly() ?
      Optional.empty() :
      Optional.of(this.swrlParserSupport.getSWRLSameIndividualAtom(iArgument1.get(), iArgument2.get()));
  }

  private Optional<SWRLDifferentIndividualsAtom> parseSWRLDifferentFromAtomArguments(@NonNull SWRLTokenizer tokenizer,
    boolean isInHead) throws SWRLParseException
  {
    Optional<? extends SWRLIArgument> iArgument1 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipComma("Expecting comma-separated second argument for different individuals atom");
    Optional<? extends SWRLIArgument> iArgument2 = parseSWRLIArgument(tokenizer, isInHead);

    tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to different individuals atom");

    return tokenizer.isInteractiveParseOnly() ?
      Optional.empty() :
      Optional.of(this.swrlParserSupport.getSWRLDifferentIndividualsAtom(iArgument1.get(), iArgument2.get()));
  }

  private Optional<SWRLVariable> parseSWRLVariable(@NonNull SWRLTokenizer tokenizer, boolean isInHead)
    throws SWRLParseException
  {
    SWRLToken token = tokenizer.getToken(SWRLToken.SWRLTokenType.SHORTNAME, "Expecting variable name after ?");
    String variableName = token.getValue();

    this.swrlParserSupport.checkThatSWRLVariableNameIsValid(variableName);

    if (tokenizer.hasMoreTokens()) {
      if (!isInHead)
        tokenizer.addVariable(variableName);
      else if (!tokenizer.hasVariable(variableName))
        throw new SWRLParseException("Variable ?" + variableName + " used in consequent is not present in antecedent");
    }
    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.getSWRLVariable(variableName)) :
      Optional.empty();
  }

  private Optional<? extends SWRLIArgument> parseSWRLIArgument(@NonNull SWRLTokenizer tokenizer, boolean isInHead)
    throws SWRLParseException
  { // Parse a SWRL variable or an OWL named individual
    SWRLToken token = tokenizer.getToken("Expecting variable or OWL individual name");

    if (token.isQuestion())
      return parseSWRLVariable(tokenizer, isInHead);
    else if (token.isShortName()) {
      String identifier = token.getValue();
      if (this.swrlParserSupport.isOWLNamedIndividual(identifier)) {
        return !tokenizer.isInteractiveParseOnly() ?
          Optional.of(this.swrlParserSupport.getSWRLIndividualArgument(identifier)) :
          Optional.empty();
      } else
        throw generateEndOfRuleException("Invalid OWL individual name '" + token.getValue() + "'", tokenizer);
    } else
      throw new SWRLParseException("Expecting variable or OWL individual name, got '" + token.getValue() + "'");
  }

  private Optional<? extends SWRLDArgument> parseSWRLDArgument(@NonNull SWRLTokenizer tokenizer, boolean isInHead,
    boolean isInBuiltIn) throws SWRLParseException
  { // Parse a SWRL variable or an OWL literal; if we are processing built-in arguments we also allow OWL entity names
    String message = isInBuiltIn ?
      "Expecting variable, literal or OWL entity name for built-in atom argument" :
      "Expecting variable or literal for datatype atom argument";
    SWRLToken token = tokenizer.getToken(message);

    if (token.isQuestion())
      return parseSWRLVariable(tokenizer, isInHead);
    else if (token.isShortName()) {
      String shortName = token.getValue();
      return parseShortNameSWRLDArgument(tokenizer, isInBuiltIn, shortName);
    } else if (token.isString()) {
      String literalValue = token.getValue();
      return parseLiteralSWRLDArgument(tokenizer, literalValue);
    } else if (token.isInt()) {
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.getXSDIntSWRLLiteralArgument(token.getValue())) :
        Optional.empty();
    } else if (token.isFloat()) {
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.getXSDFloatSWRLLiteralArgument(token.getValue())) :
        Optional.empty();
    } else
      throw new SWRLParseException("Expecting variable or OWL literal, got '" + token.getValue() + "'");
  }

  private Optional<SWRLDArgument> parseLiteralSWRLDArgument(@NonNull SWRLTokenizer tokenizer, String literalValue)
    throws SWRLParseException
  {
    if (tokenizer.peekToken("String may be qualified with datatype").isAnd()) {
      tokenizer.skipToken(); // Skip the peeked token
      throw generateEndOfRuleException("Partial datatype qualifier - add '^' to complete", tokenizer);
    } else if (tokenizer.peekToken("String may be qualified with datatype").isTypeQualifier()) {
      tokenizer.skipToken(); // Skip the peeked token
      SWRLToken datatypeToken = tokenizer
        .getToken(SWRLToken.SWRLTokenType.SHORTNAME, "Expecting quotation-enclosed datatype after ^^");
      String datatype = datatypeToken.getValue();
      if (datatype.length() == 0)
        throw generateEndOfRuleException("Empty datatype qualifier - must supply a datatype", tokenizer);
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.getSWRLLiteralArgument(literalValue, datatype)) :
        Optional.empty();
    } else
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.getXSDStringSWRLLiteralArgument(literalValue)) :
        Optional.empty();
  }

  private Optional<SWRLDArgument> parseShortNameSWRLDArgument(@NonNull SWRLTokenizer tokenizer, boolean isInBuiltIn,
    @NonNull String shortName) throws SWRLParseException
  {
    // We allow the values "true" and "false" and interpret them as OWL literals of type xsd:boolean.
    if (shortName.equalsIgnoreCase("true") || shortName.equalsIgnoreCase("false")) {
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.getXSDBooleanSWRLLiteralArgument(shortName)) :
        Optional.empty();
    } else { // Not "true" or "false"
      if (isInBuiltIn) { // SWRL built-ins in the SWRLAPI allow OWL entity names as arguments
        if (this.swrlParserSupport.isOWLNamedIndividual(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.empty() :
            Optional.of(this.swrlParserSupport.getSWRLNamedIndividualBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLClass(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.empty() :
            Optional.of(this.swrlParserSupport.getSWRLClassBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLObjectProperty(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.empty() :
            Optional.of(this.swrlParserSupport.getSWRLObjectPropertyBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLDataProperty(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.empty() :
            Optional.of(this.swrlParserSupport.getSWRLDataPropertyBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLAnnotationProperty(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.empty() :
            Optional.of(this.swrlParserSupport.getSWRLAnnotationPropertyBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLDatatype(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.empty() :
            Optional.of(this.swrlParserSupport.getSWRLDatatypeBuiltInArgument(shortName));
        } else
          throw generateEndOfRuleException("Expecting boolean or OWL entity name, got '" + shortName + "'", tokenizer);
      } else
        // Not "true" or "false" and not a built-in argument
        throw generateEndOfRuleException("Expecting boolean, got '" + shortName + "'", tokenizer);
    }
  }

  private Optional<List<SWRLDArgument>> parseSWRLDArgumentList(@NonNull SWRLTokenizer tokenizer, boolean isInHead)
    throws SWRLParseException
  { // Parse an argument list that can contain variables, OWL named entities, and literals
    Optional<List<SWRLDArgument>> dArguments = !tokenizer.isInteractiveParseOnly() ?
      Optional.of(new ArrayList<>()) :
      Optional.empty();

    Optional<? extends SWRLDArgument> dArgument = parseSWRLDArgument(tokenizer, isInHead, true);

    if (!tokenizer.isInteractiveParseOnly())
      dArguments.get().add(dArgument.get());

    SWRLToken token = tokenizer
      .getToken("Expecting additional comma-separated built-in arguments or closing parenthesis");
    while (token.isComma()) {
      dArgument = parseSWRLDArgument(tokenizer, isInHead, true);
      if (!tokenizer.isInteractiveParseOnly())
        dArguments.get().add(dArgument.get());
      token = tokenizer.getToken("Expecting ',' or ')'");
      if (!(token.isComma() || token.isRParen()))
        throw new SWRLParseException("Expecting ',' or ')', got '" + token.getValue() + "'");
    }
    return dArguments;
  }

  @NonNull private SWRLParseException generateEndOfRuleException(@NonNull String message,
    @NonNull SWRLTokenizer tokenizer)
  {
    if (tokenizer.hasMoreTokens() || !tokenizer.isInteractiveParseOnly())
      return new SWRLParseException(message);
    else
      return new SWRLIncompleteRuleException(message);
  }
}
