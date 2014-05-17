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
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.ext.SWRLAPIOWLOntology;

public class SWRLParser
{
	private final SWRLParserSupport swrlParserSupport;

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
		boolean atLeastOneAtom = false, justProcessedAtom = true, isInHead = false;
		String message;

		if (!tokenizer.isParseOnly() && !tokenizer.hasMoreTokens())
			throw new SWRLParseException("Empty rule");

		do {
			if (justProcessedAtom)
				message = isInHead ? "Expecting " + SWRLTokenizer.AND_CHAR : "Expecting " + SWRLTokenizer.IMP_CHAR + " or "
						+ SWRLTokenizer.AND_CHAR + " or " + SWRLTokenizer.RING_CHAR;
			else
				message = isInHead ? "Expecting atom" : "Expecting atom or " + SWRLTokenizer.IMP_CHAR;

			String currentToken = tokenizer.getNextNonSpaceToken(message);

			if (currentToken.equals("" + SWRLTokenizer.IMP_CHAR) || currentToken.equals("->")) { // An empty body is ok
				if (isInHead)
					throw new SWRLParseException("Second occurence of " + SWRLTokenizer.IMP_CHAR);
				isInHead = true;
				justProcessedAtom = false;
			} else if (currentToken.equals("-")) {
				continue; // Ignore "->" while we build up IMP_CHAR.
			} else if (currentToken.equals("" + SWRLTokenizer.AND_CHAR) || currentToken.equals("^")) {
				if (!justProcessedAtom)
					throw new SWRLParseException(SWRLTokenizer.AND_CHAR + " may occur only after an atom");
				justProcessedAtom = false;
			} else if (currentToken.equals("" + SWRLTokenizer.RING_CHAR) || currentToken.equals(".")) {
				if (isInHead || !justProcessedAtom)
					throw new SWRLParseException(SWRLTokenizer.RING_CHAR + " may only occur in query body");
				justProcessedAtom = false;
			} else {
				String predicate = currentToken;
				SWRLAtom atom = parseSWRLAtom(predicate, tokenizer, isInHead);
				atLeastOneAtom = true;
				if (!tokenizer.isParseOnly()) {
					if (isInHead)
						head.add(atom);
					else
						body.add(atom);
				}
				justProcessedAtom = true;
			}
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
		if (predicate.equalsIgnoreCase("sameAs")) {
			tokenizer.checkAndSkipToken("(", "Expecting parentheses-enclosed arguments for same individual atom");
			return parseSWRLSameAsAtomArguments(tokenizer, isInHead);
		} else if (predicate.equalsIgnoreCase("differentFrom")) {
			tokenizer.checkAndSkipToken("(", "Expecting parentheses-enclosed arguments for different individuals atom");
			return parseSWRLDifferentFromAtomArguments(tokenizer, isInHead);
		} else if (swrlParserSupport.isOWLClass(predicate)) {
			tokenizer.checkAndSkipToken("(", "Expecting parentheses-enclosed arguments for class atom");
			return parseSWRLClassAtomArguments(predicate, tokenizer, isInHead);
		} else if (swrlParserSupport.isOWLObjectProperty(predicate)) {
			tokenizer.checkAndSkipToken("(", "Expecting parentheses-enclosed arguments for object property atom");
			return parseSWRLIndividualPropertyAtomArguments(predicate, tokenizer, isInHead);
		} else if (swrlParserSupport.isOWLDataProperty(predicate)) {
			tokenizer.checkAndSkipToken("(", "Expecting parentheses-enclosed arguments for data property atom");
			return parseSWRLDataPropertyAtomArguments(predicate, tokenizer, isInHead);
		} else if (swrlParserSupport.isSWRLBuiltIn(predicate)) {
			tokenizer.checkAndSkipToken("(", "Expecting parentheses-enclosed arguments for built-in atom");
			return parseSWRLBuiltinAtomArguments(predicate, tokenizer, isInHead);
		} else
			throw new SWRLParseException("Invalid SWRL atom predicate " + predicate);
	}

	private SWRLClassAtom parseSWRLClassAtomArguments(String predicate, SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument = parseIArgument(tokenizer, isInHead);

		tokenizer.checkAndSkipToken(")", "Expecting closing parenthesis for argument for class atom " + predicate);

		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLClassAtom(predicate, iArgument) : null;
	}

	private SWRLObjectPropertyAtom parseSWRLIndividualPropertyAtomArguments(String predicate, SWRLTokenizer tokenizer,
			boolean isInHead) throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipToken(",", "Expecting comma-separated second argument for object property atom " + predicate);
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipToken(")", "Expecting closing parenthesis after second argument of object property atom "
				+ predicate);

		return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLObjectPropertyAtom(predicate, iArgument1, iArgument2)
				: null;
	}

	private SWRLDataPropertyAtom parseSWRLDataPropertyAtomArguments(String predicate, SWRLTokenizer tokenizer,
			boolean isInHead) throws SWRLParseException
	{
		SWRLIArgument iArgument = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipToken(",", "Expecting comma-separated second parameter for data property atom " + predicate);
		SWRLDArgument dArgument = parseDArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipToken(")", "Expecting closing parenthesis after second argument of data property atom "
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
		tokenizer.checkAndSkipToken(",", "Expecting comma-separated second argument for same individual atom");
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);

		tokenizer.checkAndSkipToken(")", "Expecting closing parenthesis after second argument to same individual atom");

		return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLSameIndividualAtom(iArgument1, iArgument2);
	}

	private SWRLDifferentIndividualsAtom parseSWRLDifferentFromAtomArguments(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{
		SWRLIArgument iArgument1 = parseIArgument(tokenizer, isInHead);
		tokenizer.checkAndSkipToken(",", "Expecting comma-separated second argument for different individuals atom");
		SWRLIArgument iArgument2 = parseIArgument(tokenizer, isInHead);

		tokenizer.checkAndSkipToken(")",
				"Expecting closing parenthesis after second argument to different individuals atom");

		return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLDifferentIndividualsAtom(iArgument1, iArgument2);
	}

	private SWRLVariable parseSWRLVariable(SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{
		String variableName = tokenizer.getNextNonSpaceToken("Expecting variable name");
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
		String token = tokenizer.getNextNonSpaceToken("Expecting variable, literal, or OWL entity name");

		if (token.equals("?"))
			return parseSWRLVariable(tokenizer, isInHead);
		else { // The entity is an OWL named entity or a literal
			if (swrlParserSupport.isOWLNamedIndividual(token)) {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getSWRLIndividualArgument(token) : null;
			} else {
				if (tokenizer.hasMoreTokens())
					throw new SWRLParseException("Invalid OWL individual name " + token);
				else
					throw new SWRLIncompleteRuleException("Incomplete rule - OWL individual name " + token + " not valid");
			}
		}
	}

	private SWRLDArgument parseDArgument(SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{ // Parse a SWRL variable or an OWL literal
		String token = tokenizer.getNextNonSpaceToken("Expecting variable or OWL entity name");

		if (token.equals("?"))
			return parseSWRLVariable(tokenizer, isInHead);
		else
			return parseOWLLiteral(token, tokenizer);
	}

	private SWRLLiteralArgument parseOWLLiteral(String token, SWRLTokenizer tokenizer) throws SWRLParseException
	{
		if (token.equals("\"")) { // The parsed entity is a string
			String stringValue = tokenizer.getNextStringToken("Expected a string");

			return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDStringSWRLLiteralArgument(stringValue) : null;
		} else if (token.startsWith("t") || token.startsWith("T") || token.startsWith("f") || token.startsWith("F")) {
			// According to the XSD Specification, xsd:boolean's have the lexical space: {true, false, 1, 0}. We don't allow
			// {1, 0} since these are parsed as xsd:ints.
			if (tokenizer.hasMoreTokens()) {
				if (token.equalsIgnoreCase("true") || token.equalsIgnoreCase("false")) {
					return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDBooleanSWRLLiteralArgument(token) : null;
				} else
					throw new SWRLParseException("Invalid OWL literal " + token);
			} else
				return null;
		} else { // Is it a long or double then
			if (token.contains(".")) {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDDoubleSWRLLiteralArgument(token) : null;
			} else {
				return !tokenizer.isParseOnly() ? swrlParserSupport.getXSDLongSWRLLiteralArgument(token) : null;
			}
		}
	}

	private SWRLDArgument parseBuiltInArgument(SWRLTokenizer tokenizer, boolean isInHead) throws SWRLParseException
	{ // Parse a SWRL variable or an OWL named entity
		String token = tokenizer.getNextNonSpaceToken("Expecting variable or OWL entity name");

		if (token.equals("?"))
			return parseSWRLVariable(tokenizer, isInHead);
		else {
			String entityShortName = token;

			if (swrlParserSupport.isOWLNamedIndividual(entityShortName)) {
				return tokenizer.isParseOnly() ? null : swrlParserSupport
						.getSWRLNamedIndividualBuiltInArgument(entityShortName);
			} else if (swrlParserSupport.isOWLClass(entityShortName)) {
				return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLClassBuiltInArgument(entityShortName);
			} else if (swrlParserSupport.isOWLObjectProperty(entityShortName)) {
				return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLObjectPropertyBuiltInArgument(entityShortName);
			} else if (swrlParserSupport.isOWLDataProperty(entityShortName)) {
				return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLDataPropertyBuiltInArgument(entityShortName);
			} else if (swrlParserSupport.isOWLAnnotationProperty(entityShortName)) {
				return tokenizer.isParseOnly() ? null : swrlParserSupport
						.getSWRLAnnotationPropertyBuiltInArgument(entityShortName);
			} else if (swrlParserSupport.isOWLDatatype(entityShortName)) {
				return tokenizer.isParseOnly() ? null : swrlParserSupport.getSWRLDatatypeBuiltInArgument(entityShortName);
			} else {
				if (tokenizer.hasMoreTokens())
					throw new SWRLParseException("Invalid OWL entity name " + entityShortName);
				else
					throw new SWRLIncompleteRuleException("Incomplete rule - OWL entity name " + entityShortName + " not valid");
			}
		}
	}

	private List<SWRLDArgument> parseBuiltInArgumentList(SWRLTokenizer tokenizer, boolean isInHead)
			throws SWRLParseException
	{ // Parse a list of variables, OWL named entities, and literals
		List<SWRLDArgument> dArguments = !tokenizer.isParseOnly() ? new ArrayList<SWRLDArgument>() : null;

		SWRLDArgument dArgument = parseBuiltInArgument(tokenizer, isInHead);

		if (!tokenizer.isParseOnly())
			dArguments.add(dArgument);

		String token = tokenizer
				.getNextNonSpaceToken("Expecting additional comma-separated variables or literals or closing parenthesis");
		while (token.equals(",")) {
			dArgument = parseDArgument(tokenizer, isInHead);
			if (!tokenizer.isParseOnly())
				dArguments.add(dArgument);
			token = tokenizer.getNextNonSpaceToken("Expecting ',' or ')'");
			if (!(token.equals(",") || token.equals(")")))
				throw new SWRLParseException("Expecting ',' or ')', got " + token);
		}
		return dArguments;
	}
}
