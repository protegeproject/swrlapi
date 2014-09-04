package org.swrlapi.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

/**
 * A basic SWRL and SQWRL parser. It provides in interactive parsing mode for incomplete rules and queries and
 * provides feedback on the next construct that it is expecting.
 * <p/>
 * This parser will throw a {@link org.swrlapi.parser.SWRLParseException} if there is an error in the rule or query.
 * In interactive parse mode, if the rule or query is correct but incomplete a
 * {@link org.swrlapi.parser.SWRLIncompleteRuleException} (which is a subclass of {@link org.swrlapi.parser.SWRLParseException})
 * will be thrown.
 * <p/>
 * The {@link #parseSWRLRule(String, boolean)} method parses a rule or query. If <code>interactiveParseOnly</code>
 * argument is <code>true</code>, only checking is performed - no SWRL rules are created; if it is false, a
 * {@link org.swrlapi.core.SWRLAPIRule} object is created.
 * <p/>
 * The parser does not yet parse OWL class expressions or data ranges.
 *
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

	private final SWRLParserSupport swrlParserSupport;

	private static final String SAME_AS_PREDICATE = "sameAs";
	private static final String DIFFERENT_FROM_PREDICATE = "differentFrom";

	public SWRLParser(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		this.swrlParserSupport = new SWRLParserSupport(swrlapiOWLOntology);
	}

	public SWRLRule parseSWRLRule(String ruleText, boolean interactiveParseOnly) throws SWRLParseException
	{
		SWRLTokenizer tokenizer = new SWRLTokenizer(ruleText.trim(), interactiveParseOnly);
		Set<SWRLAtom> head = !tokenizer.isInteractiveParseOnly() ? swrlParserSupport.getSWRLHeadAtomList() : null;
		Set<SWRLAtom> body = !tokenizer.isInteractiveParseOnly() ? swrlParserSupport.getSWRLBodyAtomList() : null;
		boolean atLeastOneAtom = false, justProcessedAtom = false, isInHead = false;
		String message;

		if (!tokenizer.isInteractiveParseOnly() && !tokenizer.hasMoreTokens())
			throw new SWRLParseException("Empty!");

		do {
			if (justProcessedAtom)
				message = isInHead ? "Expecting " + AND_CHAR: "Expecting " + IMP_CHAR + ", " + AND_CHAR + " or " + RING_CHAR;
			else
				message = isInHead ? "Expecting atom" : "Expecting atom," + IMP_CHAR + " or " + RING_CHAR;

			SWRLToken currentToken = tokenizer.getToken(message);

			if (currentToken.isImp()) { // An empty body is ok
				if (isInHead)
					throw new SWRLParseException("Second occurrence of ^");
				isInHead = true;
				justProcessedAtom = false;
			} else if (currentToken.isAnd()) {
				if (!justProcessedAtom)
					throw new SWRLParseException("^ may occur only after an atom");
				justProcessedAtom = false;
			} else if (currentToken.isRing()) {
				if (isInHead)
					throw new SWRLParseException(". may only occur in query body");
				justProcessedAtom = false;
			} else if (currentToken.isShortName() || currentToken.isString()) {
				String predicate = currentToken.getValue();
				SWRLAtom atom = parseSWRLAtom(predicate, tokenizer, isInHead);
				atLeastOneAtom = true;
				if (!tokenizer.isInteractiveParseOnly()) {
					if (isInHead)
						head.add(atom);
					else
						body.add(atom);
				}
			} else
				throw new SWRLParseException("Unexpected token '" + currentToken.getValue() + "'");
			justProcessedAtom = true;
		} while (tokenizer.hasMoreTokens());

		if (!tokenizer.isInteractiveParseOnly()) {
			if (!atLeastOneAtom)
				throw new SWRLParseException("Incomplete SWRL rule - no antecedent or consequent");
			return swrlParserSupport.getSWRLRule(head, body);
		} else
			return null;
	}

	/**
	 * If the rule is correct though possibly incomplete return <code>true</code>; if the rule has errors
	 * return <code>false</code>.
	 */
	public boolean isSWRLRuleCorrectButPossiblyIncomplete(String ruleText)
	{
		try {
			parseSWRLRule(ruleText, true);
			return true;
		} catch (SWRLIncompleteRuleException e) {
			return true;
		} catch (SWRLParseException e) {
			return false;
		}
	}

	/**
	 * If the rule is correct and complete return <code>true</code>; if the rule has errors or is incomplete
	 * return <code>false</code>.
	 */
	public boolean isSWRLRuleCorrectAndComplete(String ruleText)
	{
		try {
			parseSWRLRule(ruleText, false);
			return true;
		} catch (SWRLParseException e) {
			return false;
		}
	}

	private SWRLAtom parseSWRLAtom(String predicate, SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{
		if (predicate.equalsIgnoreCase(SAME_AS_PREDICATE)) {
			tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for same individual atom");
			return parseSWRLSameAsAtomArguments(tokenizer, isInHead);
		} else if (predicate.equalsIgnoreCase(DIFFERENT_FROM_PREDICATE)) {
			tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for different individuals atom");
			return parseSWRLDifferentFromAtomArguments(tokenizer, isInHead);
		} else if (swrlParserSupport.isOWLClass(predicate)) {
			tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for class atom");
			return parseSWRLClassAtomArguments(predicate, tokenizer, isInHead);
		} else if (swrlParserSupport.isOWLObjectProperty(predicate)) {
			tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for object property atom");
			return parseSWRLObjectPropertyAtomArguments(predicate, tokenizer, isInHead);
		} else if (swrlParserSupport.isOWLDataProperty(predicate)) {
			tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for data property atom");
			return parseSWRLDataPropertyAtomArguments(predicate, tokenizer, isInHead);
		} else if (swrlParserSupport.isSWRLBuiltIn(predicate)) {
			tokenizer.checkAndSkipLParen("Expecting parentheses-enclosed arguments for built-in atom");
			return parseSWRLBuiltinAtomArguments(predicate, tokenizer, isInHead);
		} else
			throw generateEndOfRuleException("Invalid SWRL atom predicate '" + predicate + "'", tokenizer);
	}

	private SWRLClassAtom parseSWRLClassAtomArguments(String predicate, SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument = parseIArgument(tokenizer, isInHead);

		tokenizer.checkAndSkipRParen("Expecting closing parenthesis for argument for class atom " + predicate);

		return !tokenizer.isInteractiveParseOnly() ? swrlParserSupport.getSWRLClassAtom(predicate, iArgument) : null;
	}

	private SWRLObjectPropertyAtom parseSWRLObjectPropertyAtomArguments(String predicate, SWRLTokenizer tokenizer,
			boolean isInHead) throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second argument for object property atom " + predicate);
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument of object property atom "
				+ predicate);

		return !tokenizer.isInteractiveParseOnly() ?
				swrlParserSupport.getSWRLObjectPropertyAtom(predicate, iArgument1, iArgument2)
				:
				null;
	}

	private SWRLDataPropertyAtom parseSWRLDataPropertyAtomArguments(String predicate, SWRLTokenizer tokenizer,
			boolean isInHead) throws SWRLParseException
	{
		SWRLIArgument iArgument = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second parameter for data property atom " + predicate);
		SWRLDArgument dArgument = parseDArgument(tokenizer, isInHead, false);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument of data property atom "
				+ predicate);

		return !tokenizer.isInteractiveParseOnly() ?
				swrlParserSupport.getSWRLDataPropertyAtom(predicate, iArgument, dArgument) :
				null;
	}

	private SWRLBuiltInAtom parseSWRLBuiltinAtomArguments(String predicate, SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		List<SWRLDArgument> dArgumentList = parseBuiltInArgumentList(tokenizer, isInHead); // Swallows ')'

		return !tokenizer.isInteractiveParseOnly() ? swrlParserSupport.getSWRLBuiltInAtom(predicate, dArgumentList) : null;
	}

	private SWRLSameIndividualAtom parseSWRLSameAsAtomArguments(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second argument for same individual atom");
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to same individual atom");

		return tokenizer.isInteractiveParseOnly() ?
				null :
				swrlParserSupport.getSWRLSameIndividualAtom(iArgument1, iArgument2);
	}

	private SWRLDifferentIndividualsAtom parseSWRLDifferentFromAtomArguments(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second argument for different individuals atom");
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to different individuals atom");

		return tokenizer.isInteractiveParseOnly() ?
				null :
				swrlParserSupport.getSWRLDifferentIndividualsAtom(iArgument1, iArgument2);
	}

	private SWRLVariable parseSWRLVariable(SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{
		SWRLToken token = tokenizer.getToken(SWRLToken.SWRLTokenType.SHORTNAME, "Expecting variable name after ?");
		String variableName = token.getValue();
		swrlParserSupport.checkThatSWRLVariableNameIsValid(variableName);

		if (tokenizer.hasMoreTokens()) {
			if (!isInHead)
				tokenizer.addVariable(variableName);
			else if (!tokenizer.hasVariable(variableName))
				throw new SWRLParseException("Variable ?" + variableName + " used in consequent is not present in antecedent");
		}
		return !tokenizer.isInteractiveParseOnly() ? swrlParserSupport.getSWRLVariable(variableName) : null;
	}

	private SWRLIArgument parseIArgument(SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{ // Parse a SWRL variable or an OWL named individual
		SWRLToken token = tokenizer.getToken("Expecting variable or OWL individual name");

		if (token.isQuestion())
			return parseSWRLVariable(tokenizer, isInHead);
		else if (token.isShortName()) {
			String identifier = token.getValue();
			if (swrlParserSupport.isOWLNamedIndividual(identifier)) {
				return !tokenizer.isInteractiveParseOnly() ? swrlParserSupport.getSWRLIndividualArgument(identifier) : null;
			} else
				throw generateEndOfRuleException("Invalid OWL individual name '" + token.getValue() + "'", tokenizer);
		} else
			throw new SWRLParseException("Expecting variable or OWL individual name, got '" + token.getValue() + "'");
	}

	private SWRLDArgument parseDArgument(SWRLTokenizer tokenizer, boolean isInHead, boolean isInBuiltIn)
			throws SWRLParseException
	{ // Parse a SWRL variable or an OWL literal; if we are processing built-in arguments we also allow OWL entity names
		String message = isInBuiltIn ? "Expecting variable, literal or OWL entity name for built-in atom argument"
				: "Expecting variable or literal for datatype atom argument";
		SWRLToken token = tokenizer.getToken(message);

		if (token.isQuestion())
			return parseSWRLVariable(tokenizer, isInHead);
		else if (token.isShortName()) {
			String identifier = token.getValue();
			// We allow the values "true" and "false" and interpret them as OWL literals of type xsd:boolean.
			if (identifier.equalsIgnoreCase("true") || identifier.equalsIgnoreCase("false")) {
				return !tokenizer.isInteractiveParseOnly() ?
						swrlParserSupport.getXSDBooleanSWRLLiteralArgument(identifier) :
						null;
			} else { // Not "true" or "false"
				if (isInBuiltIn) { // SWRL built-ins in the SWRLAPI allow OWL entity names as arguments
					if (swrlParserSupport.isOWLNamedIndividual(identifier)) {
						return tokenizer.isInteractiveParseOnly() ?
								null :
								swrlParserSupport.getSWRLNamedIndividualBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLClass(identifier)) {
						return tokenizer.isInteractiveParseOnly() ?
								null :
								swrlParserSupport.getSWRLClassBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLObjectProperty(identifier)) {
						return tokenizer.isInteractiveParseOnly() ?
								null :
								swrlParserSupport.getSWRLObjectPropertyBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLDataProperty(identifier)) {
						return tokenizer.isInteractiveParseOnly() ?
								null :
								swrlParserSupport.getSWRLDataPropertyBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLAnnotationProperty(identifier)) {
						return tokenizer.isInteractiveParseOnly() ? null : swrlParserSupport
								.getSWRLAnnotationPropertyBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLDatatype(identifier)) {
						return tokenizer.isInteractiveParseOnly() ?
								null :
								swrlParserSupport.getSWRLDatatypeBuiltInArgument(identifier);
					} else
						throw generateEndOfRuleException("Expecting boolean or OWL entity name, got '" + identifier + "'",
								tokenizer);
				} else
					// Not "true" or "false" and not a built-in argument
					throw generateEndOfRuleException("Expecting boolean, got '" + identifier + "'", tokenizer);
			}
		} else if (token.isString()) {
			String literalValue = token.getValue();
			if (tokenizer.peekToken().isTypeQualifier()) {
				tokenizer.skipToken(); // Skip the peeked token
				SWRLToken datatypeToken = tokenizer.getToken(SWRLToken.SWRLTokenType.STRING,
						"Expecting quotation-enclosed datatype after ^^");
				String datatype = datatypeToken.getValue();
				if (datatype.length() == 0)
					throw generateEndOfRuleException("Empty datatype qualifier - must supply a datatype", tokenizer);
				return !tokenizer.isInteractiveParseOnly() ?
						swrlParserSupport.getSWRLLiteralArgument(literalValue, datatype) : null;
			} else
				return !tokenizer.isInteractiveParseOnly() ?
						swrlParserSupport.getXSDStringSWRLLiteralArgument(literalValue) : null;
		} else if (token.isLong()) {
			return !tokenizer.isInteractiveParseOnly() ?
					swrlParserSupport.getXSDLongSWRLLiteralArgument(token.getValue()) : null;
		} else if (token.isDouble()) {
			return !tokenizer.isInteractiveParseOnly() ?
					swrlParserSupport.getXSDDoubleSWRLLiteralArgument(token.getValue()) : null;
		} else
			throw new SWRLParseException("Expecting variable or OWL literal, got '" + token.getValue() + "'");
	}

	private List<SWRLDArgument> parseBuiltInArgumentList(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{ // Parse an argument list that can contain variables, OWL named entities, and literals
		List<SWRLDArgument> dArguments = !tokenizer.isInteractiveParseOnly() ? new ArrayList<SWRLDArgument>() : null;

		SWRLDArgument dArgument = parseDArgument(tokenizer, isInHead, true);

		if (!tokenizer.isInteractiveParseOnly())
			dArguments.add(dArgument);

		SWRLToken token = tokenizer
				.getToken("Expecting additional comma-separated built-in arguments or closing parenthesis");
		while (token.isComma()) {
			dArgument = parseDArgument(tokenizer, isInHead, true);
			if (!tokenizer.isInteractiveParseOnly())
				dArguments.add(dArgument);
			token = tokenizer.getToken("Expecting ',' or ')'");
			if (!(token.isComma() || token.isRParen()))
				throw new SWRLParseException("Expecting ',' or ')', got '" + token.getValue() + "'");
		}
		return dArguments;
	}

	private SWRLParseException generateEndOfRuleException(String message, SWRLTokenizer tokenizer)
	{
		if (tokenizer.hasMoreTokens() || !tokenizer.isInteractiveParseOnly())
			return new SWRLParseException(message);
		else
			return new SWRLIncompleteRuleException(message);
	}
}
