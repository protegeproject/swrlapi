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
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.parser.SWRLToken.SWRLTokenType;

/**
 * A basic SWRL and SQWRL parser. It provides in interactive parsing mode for incomplete rules and queries and
 * provides feedback on the next construct that it is expecting.
 * <p/>
 * This parser will throw a {@link org.swrlapi.parser.SWRLParseException} if there is an error in the rule or query.
 * In interactive model, if the rule or query is correct but incomplete a {@link org.swrlapi.parser.SWRLIncompleteRuleException}
 * (which is a subclass of {@link org.swrlapi.parser.SWRLParseException}) will be thrown.
 * <p/>
 * The {@link #parseSWRLRule(String, boolean)} method parses a rule or query. If <code>parseOnly</code> argument is
 * true, only checking is performed - no SWRL rules are created; if it is false, a {@link org.swrlapi.core.SWRLAPIRule}
 * object is created.
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
	private final SWRLParserSupport swrlParserSupport;

	private static final String SAME_AS_PREDICATE = "sameAs";
	private static final String DIFFERENT_FROM_PREDICATE = "differentFrom";

	public SWRLParser(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		this.swrlParserSupport = new SWRLParserSupport(swrlapiOWLOntology);
	}

	/**
	 * If the rule is correct and incomplete return 'true'; if the rule has errors or is correct and complete, return
	 * 'false'.
	 */
	public boolean isSWRLRuleCorrectAndIncomplete(String ruleText)
	{
		boolean result = false;

		try {
			parseSWRLRule(ruleText, true);
		} catch (SWRLParseException e) {
			if (e instanceof SWRLIncompleteRuleException)
				result = true;
		}

		return result;
	}

	public SWRLRule parseSWRLRule(String ruleText, boolean parseOnly) throws SWRLParseException
	{
		SWRLTokenizer tokenizer = new SWRLTokenizer(ruleText.trim(), parseOnly);
		Set<SWRLAtom> head = !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLHeadAtomList() : null;
		Set<SWRLAtom> body = !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLBodyAtomList() : null;
		boolean atLeastOneAtom = false, justProcessedAtom = false, isInHead = false;
		String message;

		if (!tokenizer.isParseOnly() && !tokenizer.hasMoreTokens())
			throw new SWRLParseException("Empty!");

		do {
			if (justProcessedAtom)
				message = isInHead ? "Expecting " + SWRLTokenizer.AND_CHAR : "Expecting " + SWRLTokenizer.IMP_CHAR + ", "
						+ SWRLTokenizer.AND_CHAR + " or " + SWRLTokenizer.RING_CHAR;
			else
				message = isInHead ? "Expecting atom" : "Expecting atom or " + SWRLTokenizer.IMP_CHAR + " or "
						+ SWRLTokenizer.RING_CHAR;

			SWRLToken currentToken = tokenizer.getToken(message);

			if (currentToken.getTokenType() == SWRLToken.SWRLTokenType.IMP) { // An empty body is ok
				if (isInHead)
					throw new SWRLParseException("Second occurrence of " + SWRLTokenizer.IMP_CHAR);
				isInHead = true;
				justProcessedAtom = false;
			} else if (currentToken.getTokenType() == SWRLToken.SWRLTokenType.AND) {
				if (!justProcessedAtom)
					throw new SWRLParseException(SWRLTokenizer.AND_CHAR + " may occur only after an atom");
				justProcessedAtom = false;
			} else if (currentToken.getTokenType() == SWRLToken.SWRLTokenType.RING) {
				if (isInHead)
					throw new SWRLParseException(SWRLTokenizer.RING_CHAR + " may only occur in query body");
				justProcessedAtom = false;
			} else if (currentToken.getTokenType() == SWRLToken.SWRLTokenType.SHORTNAME
					|| currentToken.getTokenType() == SWRLToken.SWRLTokenType.STRING) {
				String predicate = currentToken.getValue();
				SWRLAtom atom = parseSWRLAtom(predicate, tokenizer, isInHead);
				atLeastOneAtom = true;
				if (!tokenizer.isParseOnly()) {
					if (isInHead)
						head.add(atom);
					else
						body.add(atom);
				}
			} else
				throw new SWRLParseException("Unexpected token '" + currentToken.getValue() + "'");
			justProcessedAtom = true;
		} while (tokenizer.hasMoreTokens());

		if (!tokenizer.isParseOnly()) {
			if (!atLeastOneAtom)
				throw new SWRLParseException("Incomplete SWRL rule - no antecedent or consequent");
			return swrlParserSupport.getSWRLRule(head, body);
		} else
			return null;
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

		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLClassAtom(predicate, iArgument) : null;
	}

	private SWRLObjectPropertyAtom parseSWRLObjectPropertyAtomArguments(String predicate, SWRLTokenizer tokenizer,
			boolean isInHead) throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second argument for object property atom " + predicate);
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument of object property atom "
				+ predicate);

		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLObjectPropertyAtom(predicate, iArgument1, iArgument2)
				: null;
	}

	private SWRLDataPropertyAtom parseSWRLDataPropertyAtomArguments(String predicate, SWRLTokenizer tokenizer,
			boolean isInHead) throws SWRLParseException
	{
		SWRLIArgument iArgument = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second parameter for data property atom " + predicate);
		SWRLDArgument dArgument = parseDArgument(tokenizer, isInHead, false);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument of data property atom "
				+ predicate);

		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLDataPropertyAtom(predicate, iArgument, dArgument) : null;
	}

	private SWRLBuiltInAtom parseSWRLBuiltinAtomArguments(String predicate, SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		List<SWRLDArgument> dArgumentList = parseBuiltInArgumentList(tokenizer, isInHead); // Swallows ')'

		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLBuiltInAtom(predicate, dArgumentList) : null;
	}

	private SWRLSameIndividualAtom parseSWRLSameAsAtomArguments(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second argument for same individual atom");
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to same individual atom");

		return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLSameIndividualAtom(iArgument1, iArgument2);
	}

	private SWRLDifferentIndividualsAtom parseSWRLDifferentFromAtomArguments(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipComma("Expecting comma-separated second argument for different individuals atom");
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipRParen("Expecting closing parenthesis after second argument to different individuals atom");

		return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLDifferentIndividualsAtom(iArgument1, iArgument2);
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
		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLVariable(variableName) : null;
	}

	private SWRLIArgument parseIArgument(SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{ // Parse a SWRL variable or an OWL named individual
		SWRLToken token = tokenizer.getToken("Expecting variable or OWL individual name");

		if (token.getTokenType() == SWRLToken.SWRLTokenType.QUESTION)
			return parseSWRLVariable(tokenizer, isInHead);
		else if (token.getTokenType() == SWRLToken.SWRLTokenType.SHORTNAME) {
			String identifier = token.getValue();
			if (swrlParserSupport.isOWLNamedIndividual(identifier)) {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLIndividualArgument(identifier) : null;
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

		if (token.getTokenType() == SWRLToken.SWRLTokenType.QUESTION)
			return parseSWRLVariable(tokenizer, isInHead);
		else if (token.getTokenType() == SWRLToken.SWRLTokenType.SHORTNAME) {
			String identifier = token.getValue();
			// We allow the values "true" and "false" and interpret them as OWL literals of type xsd:boolean.
			if (identifier.equalsIgnoreCase("true") || identifier.equalsIgnoreCase("false")) {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDBooleanSWRLLiteralArgument(identifier) : null;
			} else { // Not "true" or "false"
				if (isInBuiltIn) { // SWRL built-ins in the SWRLAPI allow OWL entity names as arguments
					if (swrlParserSupport.isOWLNamedIndividual(identifier)) {
						return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLNamedIndividualBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLClass(identifier)) {
						return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLClassBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLObjectProperty(identifier)) {
						return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLObjectPropertyBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLDataProperty(identifier)) {
						return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLDataPropertyBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLAnnotationProperty(identifier)) {
						return tokenizer.isParseOnly() ? null : swrlParserSupport
								.getSWRLAnnotationPropertyBuiltInArgument(identifier);
					} else if (swrlParserSupport.isOWLDatatype(identifier)) {
						return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLDatatypeBuiltInArgument(identifier);
					} else
						throw generateEndOfRuleException("Expecting boolean or OWL entity name, got '" + identifier + "'",
								tokenizer);
				} else
					// Not "true" or "false" and not a built-in argument
					throw generateEndOfRuleException("Expecting boolean, got '" + identifier + "'", tokenizer);
			}
		} else if (token.getTokenType() == SWRLToken.SWRLTokenType.STRING) {
			String literalValue = token.getValue();
			if (tokenizer.peekToken().getTokenType() == SWRLTokenType.TYPE_QUAL) {
				tokenizer.skipToken(); // Skip the peeked token
				SWRLToken datatypeToken = tokenizer.getToken(SWRLToken.SWRLTokenType.STRING,
						"Expecting quotation-enclosed datatype after ^^");
				String datatype = datatypeToken.getValue();
				if (datatype.length() == 0)
					throw generateEndOfRuleException("Empty datatype qualifier - must supply a datatype", tokenizer);
				return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLLiteralArgument(literalValue, datatype) : null;
			} else
				return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDStringSWRLLiteralArgument(literalValue) : null;
		} else if (token.getTokenType() == SWRLToken.SWRLTokenType.LONG) {
			return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDLongSWRLLiteralArgument(token.getValue()) : null;
		} else if (token.getTokenType() == SWRLToken.SWRLTokenType.DOUBLE) {
			return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDDoubleSWRLLiteralArgument(token.getValue()) : null;
		} else
			throw new SWRLParseException("Expecting variable or OWL literal, got '" + token.getValue() + "'");
	}

	private List<SWRLDArgument> parseBuiltInArgumentList(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{ // Parse an argument list that can contain variables, OWL named entities, and literals
		List<SWRLDArgument> dArguments = !tokenizer.isParseOnly() ? new ArrayList<SWRLDArgument>() : null;

		SWRLDArgument dArgument = parseDArgument(tokenizer, isInHead, true);

		if (!tokenizer.isParseOnly())
			dArguments.add(dArgument);

		SWRLToken token = tokenizer
				.getToken("Expecting additional comma-separated built-in arguments or closing parenthesis");
		while (token.getTokenType() == SWRLToken.SWRLTokenType.COMMA) {
			dArgument = parseDArgument(tokenizer, isInHead, true);
			if (!tokenizer.isParseOnly())
				dArguments.add(dArgument);
			token = tokenizer.getToken("Expecting ',' or ')'");
			if (!(token.getTokenType() == SWRLToken.SWRLTokenType.COMMA
					|| token.getTokenType() == SWRLToken.SWRLTokenType.RPAREN))
				throw new SWRLParseException("Expecting ',' or ')', got '" + token.getValue() + "'");
		}
		return dArguments;
	}

	private SWRLParseException generateEndOfRuleException(String message, SWRLTokenizer tokenizer)
	{
		if (tokenizer.hasMoreTokens() || !tokenizer.isParseOnly())
			return new SWRLParseException(message);
		else
			return new SWRLIncompleteRuleException(message);
	}
}
