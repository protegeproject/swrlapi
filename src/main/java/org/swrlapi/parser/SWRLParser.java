package org.swrlapi.parser;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
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
 * The parser does not yet parse OWL class expressions and only supports a basic form of data range atoms.
 *
 * @see org.semanticweb.owlapi.model.SWRLRule
 * @see org.swrlapi.parser.SWRLTokenizer
 * @see org.swrlapi.parser.SWRLParserSupport
 * @see org.swrlapi.parser.SWRLParseException
 * @see org.swrlapi.parser.SWRLIncompleteRuleException
 */
public class SWRLParser
{
  public final static char AND_CHAR = '^';
  public final static String IMP_COMBINATION = "->";
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
  public Optional<@NonNull SWRLRule> parseSWRLRule(@NonNull String ruleText, boolean interactiveParseOnly,
    @NonNull String ruleName, @NonNull String comment) throws SWRLParseException
  {
    SWRLTokenizer tokenizer = new SWRLTokenizer(ruleText.trim(), interactiveParseOnly);
    Optional<Set<SWRLAtom>> head = !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.createSWRLHeadAtomList()) :
      Optional.<Set<SWRLAtom>>empty();
    Optional<Set<SWRLAtom>> body = !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.createSWRLBodyAtomList()) :
      Optional.<Set<SWRLAtom>>empty();
    boolean atLeastOneAtom = false, justProcessedAtom = false, isInHead = false;
    String message;

    if (!tokenizer.isInteractiveParseOnly() && !tokenizer.hasMoreTokens())
      throw new SWRLParseException("Empty!");

    do {
      if (justProcessedAtom)
        message = isInHead ?
          "Expecting " + AND_CHAR :
          "Expecting " + IMP_COMBINATION + ", " + AND_CHAR + " or " + RING_CHAR;
      else
        message = isInHead ? "Expecting atom" : "Expecting atom," + IMP_COMBINATION + " or " + RING_CHAR;

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
        Optional<? extends @NonNull SWRLAtom> atom = parseSWRLAtom(shortName, tokenizer, isInHead);
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
        Optional<? extends @NonNull SWRLAtom> atom = parseSWRLAtom(shortName, tokenizer, isInHead);
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
        throw new SWRLParseException("Incomplete - no antecedent or consequent");
      return Optional.of(this.swrlParserSupport.createSWRLRule(ruleName, head.get(), body.get(), comment, true));
    } else
      return Optional.<@NonNull SWRLRule>empty();
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

  public static int findSplittingPoint(@NonNull String ruleText)
  {
    int i = ruleText.length() - 1;

    while (i >= 0 && !(SWRLTokenizer.isOrdinaryChar(ruleText.charAt(i)) || ruleText.charAt(i) == '"'
      || ruleText.charAt(i) == ' '))
      i--;

    return i + 1;
  }

  private Optional<? extends @NonNull SWRLAtom> parseSWRLAtom(@NonNull String shortName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
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
    } else if (this.swrlParserSupport.isOWLDatatype(shortName)) {
      tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for data range atom");
      return parseSWRLDataRangeAtomArguments(shortName, tokenizer, isInHead);
    } else
      throw generateEndOfRuleException("Invalid SWRL atom predicate '" + shortName + "'", tokenizer);
  }

  private Optional<SWRLClassAtom> parseSWRLClassAtomArguments(@NonNull String classShortName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends @NonNull SWRLIArgument> iArgument = parseSWRLIArgument(tokenizer, isInHead);

    tokenizer.checkAndSkipRParen("Expecting closing parenthesis after argument for class atom " + classShortName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.createSWRLClassAtom(classShortName, iArgument.get())) :
      Optional.<SWRLClassAtom>empty();
  }

  private Optional<@NonNull SWRLObjectPropertyAtom> parseSWRLObjectPropertyAtomArguments(
    @NonNull String propertyShortName, @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends @NonNull SWRLIArgument> iArgument1 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer
      .checkAndSkipComma("Expecting comma-separated second argument for object property atom " + propertyShortName);
    Optional<? extends @NonNull SWRLIArgument> iArgument2 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipRParen(
      "Expecting closing parenthesis after second argument of object property atom " + propertyShortName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(
        this.swrlParserSupport.createSWRLObjectPropertyAtom(propertyShortName, iArgument1.get(), iArgument2.get())) :
      Optional.<@NonNull SWRLObjectPropertyAtom>empty();
  }

  private Optional<@NonNull SWRLDataPropertyAtom> parseSWRLDataPropertyAtomArguments(@NonNull String propertyShortName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends @NonNull SWRLIArgument> iArgument = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer
      .checkAndSkipComma("Expecting comma-separated second parameter for data property atom " + propertyShortName);
    Optional<? extends @NonNull SWRLDArgument> dArgument = parseSWRLDArgument(tokenizer, isInHead, false);
    tokenizer.checkAndSkipRParen(
      "Expecting closing parenthesis after second argument of data property atom " + propertyShortName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional
        .of(this.swrlParserSupport.createSWRLDataPropertyAtom(propertyShortName, iArgument.get(), dArgument.get())) :
      Optional.<@NonNull SWRLDataPropertyAtom>empty();
  }

  private Optional<@NonNull SWRLBuiltInAtom> parseSWRLBuiltinAtomArguments(@NonNull String builtInPrefixedName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<@NonNull List<@NonNull SWRLDArgument>> dArgumentList = parseSWRLDArgumentList(tokenizer,
      isInHead); // Swallows ')'

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.createSWRLBuiltInAtom(builtInPrefixedName, dArgumentList.get())) :
      Optional.<@NonNull SWRLBuiltInAtom>empty();
  }

  private Optional<@NonNull SWRLDataRangeAtom> parseSWRLDataRangeAtomArguments(@NonNull String datatypePrefixedName,
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends @NonNull SWRLDArgument> dArgument = parseSWRLDArgument(tokenizer, isInHead, false);

    tokenizer
      .checkAndSkipRParen("Expecting closing parenthesis after argument for data range atom " + datatypePrefixedName);

    return !tokenizer.isInteractiveParseOnly() ?
      Optional.of(this.swrlParserSupport.createSWRLDataRangeAtom(datatypePrefixedName, dArgument.get())) :
      Optional.empty();
  }

  private Optional<@NonNull SWRLSameIndividualAtom> parseSWRLSameAsAtomArguments(@NonNull SWRLTokenizer tokenizer,
    boolean isInHead) throws SWRLParseException
  {
    Optional<? extends @NonNull SWRLIArgument> iArgument1 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipComma("Expecting comma-separated second argument for same individual atom");
    Optional<? extends @NonNull SWRLIArgument> iArgument2 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to same individual atom");

    return tokenizer.isInteractiveParseOnly() ?
      Optional.<@NonNull SWRLSameIndividualAtom>empty() :
      Optional.of(this.swrlParserSupport.createSWRLSameIndividualAtom(iArgument1.get(), iArgument2.get()));
  }

  private Optional<@NonNull SWRLDifferentIndividualsAtom> parseSWRLDifferentFromAtomArguments(
    @NonNull SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
  {
    Optional<? extends @NonNull SWRLIArgument> iArgument1 = parseSWRLIArgument(tokenizer, isInHead);
    tokenizer.checkAndSkipComma("Expecting comma-separated second argument for different individuals atom");
    Optional<? extends @NonNull SWRLIArgument> iArgument2 = parseSWRLIArgument(tokenizer, isInHead);

    tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to different individuals atom");

    return tokenizer.isInteractiveParseOnly() ?
      Optional.<@NonNull SWRLDifferentIndividualsAtom>empty() :
      Optional.of(this.swrlParserSupport.createSWRLDifferentIndividualsAtom(iArgument1.get(), iArgument2.get()));
  }

  private Optional<@NonNull SWRLVariable> parseSWRLVariable(@NonNull SWRLTokenizer tokenizer, boolean isInHead)
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
      Optional.of(this.swrlParserSupport.createSWRLVariable(variableName)) :
      Optional.<@NonNull SWRLVariable>empty();
  }

  private Optional<? extends @NonNull SWRLIArgument> parseSWRLIArgument(@NonNull SWRLTokenizer tokenizer,
    boolean isInHead) throws SWRLParseException
  { // Parse a SWRL variable or an OWL named individual
    SWRLToken token = tokenizer.getToken("Expecting variable or OWL individual name");

    if (token.isQuestion())
      return parseSWRLVariable(tokenizer, isInHead);
    else if (token.isShortName()) {
      String identifier = token.getValue();
      if (this.swrlParserSupport.isOWLNamedIndividual(identifier)) {
        return !tokenizer.isInteractiveParseOnly() ?
          Optional.of(this.swrlParserSupport.createSWRLIndividualArgument(identifier)) :
          Optional.<@NonNull SWRLIArgument>empty();
      } else
        throw generateEndOfRuleException("Invalid OWL individual name '" + token.getValue() + "'", tokenizer);
    } else
      throw new SWRLParseException("Expecting variable or OWL individual name, got '" + token.getValue() + "'");
  }

  private Optional<? extends @NonNull SWRLDArgument> parseSWRLDArgument(@NonNull SWRLTokenizer tokenizer,
    boolean isInHead, boolean isInBuiltIn) throws SWRLParseException
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
        Optional.of(this.swrlParserSupport.createXSDIntSWRLLiteralArgument(token.getValue())) :
        Optional.<@NonNull SWRLDArgument>empty();
    } else if (token.isFloat()) {
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.createXSDFloatSWRLLiteralArgument(token.getValue())) :
        Optional.<@NonNull SWRLDArgument>empty();
    } else
      throw new SWRLParseException("Expecting variable, literal or OWL entity name, got '" + token.getValue() + "'");
  }

  private Optional<@NonNull SWRLDArgument> parseLiteralSWRLDArgument(@NonNull SWRLTokenizer tokenizer,
    @NonNull String literalValue) throws SWRLParseException
  {
    if (tokenizer.peekToken("String may be qualified with datatype").isAnd()) {
      tokenizer.skipToken(); // Skip the peeked token
      throw generateEndOfRuleException("Partial datatype qualifier - add '^' to complete", tokenizer);
    } else if (tokenizer.peekToken("String may be qualified with datatype").isTypeQualifier()) {
      tokenizer.skipToken(); // Skip the peeked token
      SWRLToken datatypeToken = tokenizer.getToken(SWRLToken.SWRLTokenType.SHORTNAME, "Expecting datatype after ^^");
      String datatype = datatypeToken.getValue();
      if (datatype.length() == 0)
        throw generateEndOfRuleException("Empty datatype qualifier - must supply a datatype after ^^", tokenizer);
      else if (!this.swrlParserSupport.isOWLDatatype(datatype))
        throw generateEndOfRuleException("invalid datatype name '" + datatype + "'", tokenizer);
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.createSWRLLiteralArgument(literalValue, datatype)) :
        Optional.<@NonNull SWRLDArgument>empty();
    } else
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.createXSDStringSWRLLiteralArgument(literalValue)) :
        Optional.<@NonNull SWRLDArgument>empty();
  }

  /**
   * The OWLAPI follows the OWL Specification and does not explicitly allow named OWL entities as arguments to
   * built-ins. However, if OWLAPI parsers encounter OWL entities as parameters they appear to represent them as SWRL
   * variables - with the variable IRI set to the IRI of the entity ({@link org.semanticweb.owlapi.model.OWLEntity}
   * classes represent named OWL concepts so have an IRI). So if we are processing built-in parameters and encounter
   * variables with an IRI referring to OWL entities in the active ontology we can transform them to the
   * appropriate SWRLAPI built-in argument for the named entity.
   *
   * @see org.swrlapi.parser.SWRLParser#parseShortNameSWRLDArgument(SWRLTokenizer, boolean, String)
   * @see org.swrlapi.factory.DefaultSWRLRuleAndQueryRenderer#visit(SWRLVariable)
   */
  private Optional<@NonNull SWRLDArgument> parseShortNameSWRLDArgument(@NonNull SWRLTokenizer tokenizer,
    boolean isInBuiltIn, @NonNull String shortName) throws SWRLParseException
  {
    // We allow the values "true" and "false" and interpret them as OWL literals of type xsd:boolean.
    if (shortName.equalsIgnoreCase("true") || shortName.equalsIgnoreCase("false")) {
      return !tokenizer.isInteractiveParseOnly() ?
        Optional.of(this.swrlParserSupport.createXSDBooleanSWRLLiteralArgument(shortName)) :
        Optional.<@NonNull SWRLDArgument>empty();
    } else { // Not "true" or "false"
      if (isInBuiltIn) { // SWRL built-ins in the SWRLAPI allow OWL entity names as arguments
        if (this.swrlParserSupport.isOWLNamedIndividual(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.<@NonNull SWRLDArgument>empty() :
            Optional.of(this.swrlParserSupport.createSWRLNamedIndividualBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLClass(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.<@NonNull SWRLDArgument>empty() :
            Optional.of(this.swrlParserSupport.createSWRLClassBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLObjectProperty(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.<@NonNull SWRLDArgument>empty() :
            Optional.of(this.swrlParserSupport.createSWRLObjectPropertyBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLDataProperty(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.<@NonNull SWRLDArgument>empty() :
            Optional.of(this.swrlParserSupport.createSWRLDataPropertyBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLAnnotationProperty(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.<@NonNull SWRLDArgument>empty() :
            Optional.of(this.swrlParserSupport.createSWRLAnnotationPropertyBuiltInArgument(shortName));
        } else if (this.swrlParserSupport.isOWLDatatype(shortName)) {
          return tokenizer.isInteractiveParseOnly() ?
            Optional.<@NonNull SWRLDArgument>empty() :
            Optional.of(this.swrlParserSupport.createSWRLDatatypeBuiltInArgument(shortName));
        } else
          throw generateEndOfRuleException("Expecting boolean or OWL entity name, got '" + shortName + "'", tokenizer);
      } else
        // Not "true" or "false" and not a built-in argument
        throw generateEndOfRuleException("Expecting boolean, got '" + shortName + "'", tokenizer);
    }
  }

  private Optional<@NonNull List<@NonNull SWRLDArgument>> parseSWRLDArgumentList(@NonNull SWRLTokenizer tokenizer,
    boolean isInHead) throws SWRLParseException
  { // Parse an argument list that can contain variables, OWL named entities, and literals
    Optional<@NonNull List<@NonNull SWRLDArgument>> dArguments = !tokenizer.isInteractiveParseOnly() ?
      Optional.of(new ArrayList<>()) :
      Optional.<@NonNull List<@NonNull SWRLDArgument>>empty();

    Optional<? extends @NonNull SWRLDArgument> dArgument = parseSWRLDArgument(tokenizer, isInHead, true);

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
