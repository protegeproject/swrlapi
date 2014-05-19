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
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.parser.SWRLToken.SWRLTokenType;

public class SWRLParser
{
	private final SWRLParserSupport swrlParserSupport;

	private static final String SAME_AS_PREDICATE = "sameAs";
	private static final String DIFFERENT_FROM_PREDICATE = "differentFrom";

	public SWRLParser(SWRLAPIOWLOntology swrlapiOWLOntology, DefaultPrefixManager prefixManager)
	{
		this.swrlParserSupport = new SWRLParserSupport(swrlapiOWLOntology, prefixManager);
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

	/**
	 * This parser will throw a {@link SWRLParseException} if it finds an error in the supplied rule. If the rule is
	 * correct but incomplete, a {@link SWRLIncompleteRuleException} (which is a subclass of {@link SWRLParseException})
	 * will be thrown.
	 * <p>
	 * If {@link #parseOnly} is true, only checking is performed - no SWRL rules are created; if it is false, rules are
	 * created.
	 */
	public SWRLRule parseSWRLRule(String ruleText, boolean parseOnly) throws SWRLParseException
	{
		SWRLTokenizer tokenizer = new SWRLTokenizer(ruleText.trim(), parseOnly);
		Set<SWRLAtom> head = !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLHeadAtomList() : null;
		Set<SWRLAtom> body = !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLBodyAtomList() : null;
		boolean atLeastOneAtom = false, justProcessedAtom = false, isInHead = false;
		String message;

		if (!tokenizer.isParseOnly() && !tokenizer.hasMoreTokens())
			throw new SWRLParseException("Empty rule");

		do {
			if (justProcessedAtom)
				message = isInHead ? "Expecting " + SWRLTokenizer.AND_CHAR : "Expecting " + SWRLTokenizer.IMP_CHAR + " or "
						+ SWRLTokenizer.AND_CHAR + " or " + SWRLTokenizer.RING_CHAR;
			else
				message = isInHead ? "Expecting atom" : "Expecting atom or " + SWRLTokenizer.IMP_CHAR + " or "
						+ SWRLTokenizer.RING_CHAR;

			SWRLToken currentToken = tokenizer.getToken(message);

			if (currentToken.getTokenType() == SWRLToken.SWRLTokenType.IMP) { // An empty body is ok
				if (isInHead)
					throw new SWRLParseException("Second occurence of " + SWRLTokenizer.IMP_CHAR);
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
			} else if (currentToken.getTokenType() == SWRLToken.SWRLTokenType.IDENTIFIER
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
		} else {
			if (!tokenizer.isParseOnly())
				throw new SWRLParseException("Invalid SWRL atom predicate " + predicate);
			else
				throw new SWRLIncompleteRuleException("Possibly incomplete SWRL atom predicate " + predicate);
		}
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
		SWRLToken token = tokenizer.getToken(SWRLToken.SWRLTokenType.IDENTIFIER, "Expecting variable name");
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
		SWRLToken token = tokenizer.getToken();

		if (token.getTokenType() == SWRLToken.SWRLTokenType.QUESTION)
			return parseSWRLVariable(tokenizer, isInHead);
		else if (token.getTokenType() == SWRLToken.SWRLTokenType.IDENTIFIER) {
			String identifier = token.getValue();
			if (swrlParserSupport.isOWLNamedIndividual(identifier)) {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLIndividualArgument(identifier) : null;
			} else {
				if (tokenizer.hasMoreTokens())
					throw new SWRLParseException("Invalid OWL individual name '" + token.getValue() + "'");
				else
					throw new SWRLIncompleteRuleException("Incomplete rule - OWL individual name '" + token.getValue()
							+ "' not valid");
			}
		} else {
			if (!tokenizer.isParseOnly())
				throw new SWRLParseException("Expecting variable or OWL entity name '" + token.getValue() + "'");
			else
				throw new SWRLIncompleteRuleException("Expecting variable or OWL entity name, got '" + token.getValue() + "'");
		}
	}

	private SWRLDArgument parseDArgument(SWRLTokenizer tokenizer, boolean isInHead, boolean isInBuiltIn)
			throws SWRLParseException
	{ // Parse a SWRL variable or an OWL literal
		String message = isInBuiltIn ? "Expecting variable, literal or OWL entity name as built-in atom argument"
				: "Expecting variable or literal of datatype atom argument";
		SWRLToken token = tokenizer.getToken(message);

		if (token.getTokenType() == SWRLToken.SWRLTokenType.QUESTION)
			return parseSWRLVariable(tokenizer, isInHead);
		else if (token.getTokenType() == SWRLToken.SWRLTokenType.IDENTIFIER) {
			String identifier = token.getValue();
			// According to the XSD Specification, xsd:boolean's have the lexical space: {true, false, 1, 0}. We don't allow
			// {1, 0} since these are parsed as xsd:longs.
			if (identifier.equalsIgnoreCase("true") || identifier.equalsIgnoreCase("false")) {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDBooleanSWRLLiteralArgument(identifier) : null;
			} else {
				if (isInBuiltIn) {
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
					} else {
						if (tokenizer.hasMoreTokens())
							throw new SWRLParseException("Invalid OWL entity name " + identifier);
						else
							throw new SWRLIncompleteRuleException("Incomplete rule - OWL entity name " + identifier + " not valid");
					}
				} else
					throw new SWRLParseException("Invalid OWL boolean " + identifier);
			}
		} else if (token.getTokenType() == SWRLToken.SWRLTokenType.STRING) {
			String literalValue = token.getValue();
			if (tokenizer.peekToken().getTokenType() == SWRLTokenType.TYPE_QUAL) {
				tokenizer.skipToken(); // Skip the peeked token
				SWRLToken datatypeToken = tokenizer.getToken(SWRLToken.SWRLTokenType.STRING,
						"Expected quotation-enclosed datatype after ^^");
				String datatype = datatypeToken.getValue();
				if (datatype.length() == 0)
					throw new SWRLParseException("Empty datatype qualifier - must supply a datatype");
				return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLLiteralArgument(literalValue, datatype) : null;
			} else
				return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDStringSWRLLiteralArgument(literalValue) : null;
		} else if (token.getTokenType() == SWRLToken.SWRLTokenType.NUMBER) {
			// if (token.contains("."))
			// return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDDoubleSWRLLiteralArgument(token) : null;
			// else
			// return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDLongSWRLLiteralArgument(token) : null;
			return null;
		} else
			throw new SWRLParseException("Expecting variable or OWL literal, got '" + token.getValue() + "'");
	}

	private List<SWRLDArgument> parseBuiltInArgumentList(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{ // Parse a list of variables, OWL named entities, and literals
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
			if (!(token.getTokenType() == SWRLToken.SWRLTokenType.COMMA || token.getTokenType() == SWRLToken.SWRLTokenType.RPAREN))
				throw new SWRLParseException("Expecting ',' or ')', got '" + token.getValue() + "'");
		}
		return dArguments;
	}
}
