package org.swrlapi.parser;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
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
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;

/**
 * Provides support methods used by the {@link SWRLParser}.
 * 
 * @see org.swrlapi.parser.SWRLParser
 */
public class SWRLParserSupport
{
	private final SWRLAPIOWLOntology swrlapiOWLOntology;
	private final DefaultPrefixManager prefixManager;

	public SWRLParserSupport(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;
		this.prefixManager = swrlapiOWLOntology.getPrefixManager();
	}

	public boolean isOWLEntity(String entityShortName)
	{
		return isOWLClass(entityShortName) || isOWLNamedIndividual(entityShortName) || isOWLObjectProperty(entityShortName)
				|| isOWLDataProperty(entityShortName) || isOWLAnnotationProperty(entityShortName)
				|| isOWLDatatype(entityShortName);
	}

	public boolean isOWLClass(String classShortName)
	{
		IRI iri = getPrefixManager().getIRI(classShortName);

		return getOWLOntology().containsClassInSignature(iri, true);
	}

	public boolean isOWLNamedIndividual(String individualShortName)
	{
		IRI iri = getPrefixManager().getIRI(individualShortName);

		return getOWLOntology().containsIndividualInSignature(iri, true);
	}

	public boolean isOWLObjectProperty(String objectPropertyShortName)
	{
		IRI iri = getPrefixManager().getIRI(objectPropertyShortName);
		return getOWLOntology().containsObjectPropertyInSignature(iri, true);
	}

	public boolean isOWLDataProperty(String dataPropertyShortName)
	{
		IRI iri = getPrefixManager().getIRI(dataPropertyShortName);
		return getOWLOntology().containsDataPropertyInSignature(iri, true);
	}

	public boolean isOWLAnnotationProperty(String annotationPropertyShortName)
	{
		IRI iri = getPrefixManager().getIRI(annotationPropertyShortName);
		return getOWLOntology().containsAnnotationPropertyInSignature(iri, true);
	}

	public boolean isOWLDatatype(String datatypeShortName)
	{
		IRI iri = getPrefixManager().getIRI(datatypeShortName);
		return getOWLOntology().containsDatatypeInSignature(iri, true);
	}

	public boolean isSWRLBuiltIn(String builtInShortName)
	{
		IRI iri = getPrefixManager().getIRI(builtInShortName);

		return getSWRLAPIOWLOntology().isSWRLBuiltIn(iri);
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

		if (isOWLEntity(variableName))
			throw new SWRLParseException("Invalid SWRL variable name " + variableName
					+ ". Cannot use name of existing OWL class, individual, property, or datatype");
	}

	public OWLClass getOWLClass(String classShortName) throws SWRLParseException
	{
		if (isOWLClass(classShortName)) {
			IRI iri = getPrefixManager().getIRI(classShortName);
			return getOWLDataFactory().getOWLClass(iri);
		} else
			throw new SWRLParseException(classShortName + " is not an OWL class");
	}

	public OWLNamedIndividual getOWLNamedIndividual(String individualShortName) throws SWRLParseException
	{
		if (isOWLNamedIndividual(individualShortName)) {
			IRI iri = getPrefixManager().getIRI(individualShortName);
			return getOWLDataFactory().getOWLNamedIndividual(iri);
		} else
			throw new SWRLParseException(individualShortName + " is not an OWL named individual");
	}

	public OWLObjectProperty getOWLObjectProperty(String objectPropertyShortName) throws SWRLParseException
	{
		if (isOWLObjectProperty(objectPropertyShortName)) {
			IRI iri = getPrefixManager().getIRI(objectPropertyShortName);
			return getOWLDataFactory().getOWLObjectProperty(iri);
		} else
			throw new SWRLParseException(objectPropertyShortName + " is not an OWL object property");
	}

	public OWLDataProperty getOWLDataProperty(String dataPropertyShortName) throws SWRLParseException
	{
		if (isOWLDataProperty(dataPropertyShortName)) {
			IRI iri = getPrefixManager().getIRI(dataPropertyShortName);
			return getOWLDataFactory().getOWLDataProperty(iri);
		} else
			throw new SWRLParseException(dataPropertyShortName + " is not an OWL data property");
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(String annotationPropertyShortName) throws SWRLParseException
	{
		if (isOWLAnnotationProperty(annotationPropertyShortName)) {
			IRI iri = getPrefixManager().getIRI(annotationPropertyShortName);
			return getOWLDataFactory().getOWLAnnotationProperty(iri);
		} else
			throw new SWRLParseException(annotationPropertyShortName + " is not an OWL annotation property");
	}

	public OWLDatatype getOWLDatatype(String datatypeShortName) throws SWRLParseException
	{
		if (isOWLDatatype(datatypeShortName)) {
			IRI iri = getPrefixManager().getIRI(datatypeShortName);
			return getOWLDataFactory().getOWLDatatype(iri);
		} else
			throw new SWRLParseException(datatypeShortName + " is not an OWL datatype");
	}

	public SWRLLiteralArgument getXSDStringSWRLLiteralArgument(String lexicalValue)
	{
		OWLLiteral literal = getSWRLAPIOWLDataFactory().getOWLLiteral(lexicalValue);
		return getOWLDataFactory().getSWRLLiteralArgument(literal);
	}

	public SWRLLiteralArgument getXSDBooleanSWRLLiteralArgument(String lexicalValue) throws SWRLParseException
	{
		try {
			Boolean value = Boolean.parseBoolean(lexicalValue);
			OWLLiteral literal = getSWRLAPIOWLDataFactory().getOWLLiteral(value);
			return getOWLDataFactory().getSWRLLiteralArgument(literal);
		} catch (NumberFormatException e) {
			throw new SWRLParseException(lexicalValue + " is not a valid xsd:boolean");
		}
	}

	public SWRLLiteralArgument getXSDLongSWRLLiteralArgument(String lexicalValue) throws SWRLParseException
	{
		try {
			long value = Long.parseLong(lexicalValue);
			OWLLiteral literal = getSWRLAPIOWLDataFactory().getOWLLiteral(value);
			return getOWLDataFactory().getSWRLLiteralArgument(literal);
		} catch (NumberFormatException e) {
			throw new SWRLParseException(lexicalValue + " is not a valid xsd:long");
		}
	}

	public SWRLLiteralArgument getXSDDoubleSWRLLiteralArgument(String lexicalValue) throws SWRLParseException
	{
		try {
			double value = Double.parseDouble(lexicalValue);
			OWLLiteral literal = getSWRLAPIOWLDataFactory().getOWLLiteral(value);
			return getOWLDataFactory().getSWRLLiteralArgument(literal);
		} catch (NumberFormatException e) {
			throw new SWRLParseException(lexicalValue + " is not a valid xsd:double");
		}
	}

	public SWRLVariable getSWRLVariable(String variableName) throws SWRLParseException
	{
		IRI iri = getPrefixManager().getIRI(variableName);

		if (isOWLEntity(variableName))
			throw new SWRLParseException(variableName
					+ " cannot be used as a SWRL variable name because it refers to an existing OWL entity");
		return getOWLDataFactory().getSWRLVariable(iri);
	}

	public IRI getSWRLBuiltIn(String builtInShortName) throws SWRLParseException
	{
		if (isSWRLBuiltIn(builtInShortName))
			throw new SWRLParseException(builtInShortName + " is not a SWRL built-in");
		else
			return getPrefixManager().getIRI(builtInShortName);
	}

	public SWRLLiteralArgument getSWRLLiteralArgument(String lexicalValue, String datatypeShortName)
			throws SWRLParseException
	{
		OWLDatatype datatype = getOWLDatatype(datatypeShortName);
		OWLLiteral literal = getSWRLAPIOWLDataFactory().getOWLLiteral(lexicalValue, datatype);
		return getOWLDataFactory().getSWRLLiteralArgument(literal);
	}

	public SWRLRule getSWRLRule(Set<SWRLAtom> head, Set<SWRLAtom> body)
	{
		return getOWLDataFactory().getSWRLRule(body, head);
	}

	public Set<SWRLAtom> getSWRLBodyAtomList()
	{
		return new LinkedHashSet<SWRLAtom>();
	}

	public Set<SWRLAtom> getSWRLHeadAtomList()
	{
		return new LinkedHashSet<SWRLAtom>();
	}

	public SWRLClassAtom getSWRLClassAtom(String classShortName, SWRLIArgument iArgument) throws SWRLParseException
	{
		OWLClass cls = getOWLClass(classShortName);

		return getOWLDataFactory().getSWRLClassAtom(cls, iArgument);
	}

	public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(String objectPropertyShortName, SWRLIArgument iArgument1,
			SWRLIArgument iArgument2) throws SWRLParseException
	{
		OWLObjectProperty objectProperty = getOWLObjectProperty(objectPropertyShortName);

		return getOWLDataFactory().getSWRLObjectPropertyAtom(objectProperty, iArgument1, iArgument2);
	}

	public SWRLDataPropertyAtom getSWRLDataPropertyAtom(String dataPropertyShortName, SWRLIArgument iArgument,
			SWRLDArgument dArgument) throws SWRLParseException
	{
		OWLDataProperty dataProperty = getOWLDataProperty(dataPropertyShortName);

		return getOWLDataFactory().getSWRLDataPropertyAtom(dataProperty, iArgument, dArgument);
	}

	public SWRLBuiltInAtom getSWRLBuiltInAtom(String builtInShortName, List<SWRLDArgument> arguments)
			throws SWRLParseException
	{
		IRI builtInIRI = getSWRLBuiltIn(builtInShortName);
		return getOWLDataFactory().getSWRLBuiltInAtom(builtInIRI, arguments);
	}

	public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument iArgument1, SWRLIArgument iArgument2)
	{
		return getOWLDataFactory().getSWRLSameIndividualAtom(iArgument1, iArgument2);
	}

	public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument iArgument1, SWRLIArgument iArgument2)
	{
		return getOWLDataFactory().getSWRLDifferentIndividualsAtom(iArgument1, iArgument2);
	}

	public SWRLIndividualArgument getSWRLIndividualArgument(String individualShortName) throws SWRLParseException
	{
		OWLNamedIndividual individual = getOWLNamedIndividual(individualShortName);
		return getOWLDataFactory().getSWRLIndividualArgument(individual);
	}

	public SWRLClassBuiltInArgument getSWRLClassBuiltInArgument(String classShortName) throws SWRLParseException
	{
		OWLClass cls = getOWLClass(classShortName);
		return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
	}

	public SWRLNamedIndividualBuiltInArgument getSWRLNamedIndividualBuiltInArgument(String individualShortName)
			throws SWRLParseException
	{
		OWLNamedIndividual individual = getOWLNamedIndividual(individualShortName);
		return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
	}

	public SWRLObjectPropertyBuiltInArgument getSWRLObjectPropertyBuiltInArgument(String propertyShortName)
			throws SWRLParseException
	{
		OWLObjectProperty property = getOWLObjectProperty(propertyShortName);
		return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
	}

	public SWRLDataPropertyBuiltInArgument getSWRLDataPropertyBuiltInArgument(String propertyShortName)
			throws SWRLParseException
	{
		OWLDataProperty property = getOWLDataProperty(propertyShortName);
		return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
	}

	public SWRLAnnotationPropertyBuiltInArgument getSWRLAnnotationPropertyBuiltInArgument(String propertyShortName)
			throws SWRLParseException
	{
		OWLAnnotationProperty property = getOWLAnnotationProperty(propertyShortName);
		return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
	}

	public SWRLDatatypeBuiltInArgument getSWRLDatatypeBuiltInArgument(String datatypeShortName) throws SWRLParseException
	{
		OWLDatatype datatype = getOWLDatatype(datatypeShortName);
		return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
	}

	private SWRLAPIOWLOntology getSWRLAPIOWLOntology()
	{
		return this.swrlapiOWLOntology;
	}

	private OWLOntology getOWLOntology()
	{
		return getSWRLAPIOWLOntology().getOWLOntology();
	}

	private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
	}

	private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
	}

	private OWLDataFactory getOWLDataFactory()
	{
		return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
	}

	private DefaultPrefixManager getPrefixManager()
	{
		return this.prefixManager;
	}
}
