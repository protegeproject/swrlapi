package org.swrlapi.parser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.ext.SWRLAPIRule;

public class SWRLParserSupport
{
	public SWRLParserSupport()
	{
		// TODO
	}

	public SWRLAPIRule getSWRLRule(List<SWRLAtom> head, List<SWRLAtom> body)
	{
		return null; // TODO
	}

	public List<SWRLAtom> getSWRLBodyAtomList()
	{
		return new ArrayList<SWRLAtom>();
	}

	public List<SWRLAtom> getSWRLHeadAtomList()
	{
		return new ArrayList<SWRLAtom>();
	}

	public SWRLClassAtom getSWRLClassAtom(String classShortName, SWRLIArgument iArgument) throws SWRLParseException
	{
		// OWLClass cls = getOWLClass(classShortName);

		return null; // TODO
	}

	public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(String objectPropertyShortName, SWRLIArgument iArgument1,
			SWRLIArgument iArgument2) throws SWRLParseException
	{
		// OWLObjectProperty objectProperty = getOWLObjectProperty(predicate);

		return null; // TODO
	}

	public SWRLDataPropertyAtom getSWRLDataPropertyAtom(String dataPropertyShortName, SWRLIArgument iObject,
			SWRLDArgument dObject) throws SWRLParseException
	{
		// OWLDataProperty dataProperty = getOWLDataProperty(dataPropertyShortName);

		return null; // TODO
	}

	public SWRLBuiltInAtom getSWRLBuiltInAtom(String builtInShortName, List<SWRLDArgument> arguments)
			throws SWRLParseException
	{
		return null; // TODO
	}

	public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument iArgument1, SWRLIArgument iArgument2)
	{
		return null; // TODO
	}

	public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument iArgument1, SWRLIArgument iArgument2)
	{
		return null; // TODO
	}

	public OWLClass getOWLClass(String classShortName) throws SWRLParseException
	{
		return null; // TODO
	}

	public OWLObjectProperty getOWLObjectProperty(String objectPropertyShortName) throws SWRLParseException
	{
		return null; // TODO
	}

	public OWLDataProperty getOWLDataProperty(String dataPropertyShortName) throws SWRLParseException
	{
		return null; // TODO
	}

	public SWRLVariable getSWRLVariable(String variableName) throws SWRLParseException
	{
		// throw new SWRLParseException(name + " cannot be used as a SWRL variable name");
		return null; // TODO
	}

	public boolean isOWLClass(String classShortName)
	{
		return false; // TODO
	}

	public boolean isOWLNamedIndividual(String individualShortName)
	{
		return false; // TODO
	}

	public SWRLIndividualArgument getOWLNamedIndividual(String individualShortName) throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL individual");
		return null; // TODO
	}

	public SWRLClassBuiltInArgument getOWLClassBuiltInArgument(String individualShortName) throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL class");
		return null; // TODO
	}

	public SWRLNamedIndividualBuiltInArgument getOWLNamedIndividualBuiltInArgument(String individualShortName)
			throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL named individual");
		return null; // TODO
	}

	public SWRLObjectPropertyBuiltInArgument getOWLObjectPropertyBuiltInArgument(String propertyShortName)
			throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL object property");
		return null; // TODO
	}

	public SWRLDataPropertyBuiltInArgument getOWLDataPropertyBuiltInArgument(String propertyShortName)
			throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL data property");
		return null; // TODO
	}

	public SWRLAnnotationPropertyBuiltInArgument getOWLAnnotationPropertyBuiltInArgument(String propertyShortName)
			throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL annotation property");
		return null; // TODO
	}

	public SWRLDatatypeBuiltInArgument getOWLDatatypeBuiltInArgument(String datatypeShortName) throws SWRLParseException
	{
		// throw new SWRLParseException(name + " is not a valid OWL datatype");
		return null; // TODO
	}

	public boolean isOWLObjectProperty(String objectPropertyShortName)
	{
		return false; // TODO
	}

	public boolean isOWLDataProperty(String dataPropertyShortName)
	{
		return false; // TODO
	}

	public boolean isOWLAnnotationProperty(String annotationPropertyShortName)
	{
		return false; // TODO
	}

	public boolean isOWLDatatype(String datatypeShortName)
	{
		return false; // TODO
	}

	public boolean isSWRLBuiltIn(String builtInShortName)
	{
		return false; // TODO
	}

	public boolean isXSDDatatype(String datatypeShortName)
	{
		return false; // TODO
	}

	public SWRLLiteralArgument getOWLXSDStringLiteral(String rawLiteralValue)
	{
		return null; // TODO
	}

	public SWRLLiteralArgument getOWLXSDBooleanLiteral(String rawLiteralValue) throws SWRLParseException
	{
		return null; // TODO
	}

	public SWRLLiteralArgument getOWLXSDLongLiteral(String rawLiteralValue) throws SWRLParseException
	{
		return null; // TODO
	}

	public SWRLLiteralArgument getOWLXSDDoubleLiteral(String rawLiteralValue) throws SWRLParseException
	{
		return null; // TODO
	}

	public SWRLLiteralArgument getOWLLiteral(String rawLiteralValue, String datatypeName)
	{
		return null; // TODO
	}

	public boolean isValidSWRLVariableName(String candidateVariableName)
	{
		if (candidateVariableName.length() == 0)
			return false;

		if (!Character.isJavaIdentifierStart(candidateVariableName.charAt(0)))
			return false;

		for (int i = 1; i < candidateVariableName.length(); i++) {
			char c = candidateVariableName.charAt(i);
			if (!(Character.isJavaIdentifierPart(c) || c == ':' || c == '-')) {
				return false;
			}
		}
		return true;
	}

	public void checkThatSWRLVariableNameIsValid(String variableName) throws SWRLParseException
	{
		if (!isValidSWRLVariableName(variableName))
			throw new SWRLParseException("Invalid SWRL variable name " + variableName);

		// TODO
		// throw new SWRLParseException("Invalid SWRL variable name " + variableName
		// + ". Cannot use name of existing OWL class, individual, property, or datatype");
	}
}
