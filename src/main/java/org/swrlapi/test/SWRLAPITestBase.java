package org.swrlapi.test;

import static org.swrlapi.test.SWRLAPITestUtil.createDefaultPrefixManager;
import static org.swrlapi.test.SWRLAPITestUtil.createOWLOntology;
import static org.swrlapi.test.SWRLAPITestUtil.createOWLOntologyManager;
import static org.swrlapi.test.SWRLAPITestUtil.createSWRLAPIOWLOntology;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class SWRLAPITestBase
{
	String namespace;
	OWLOntologyManager manager;
	OWLOntology ontology;
	DefaultPrefixManager prefixManager;
	SWRLAPIOWLOntology swrlapiowlOntology;

	protected SWRLAPIOWLOntology createEmptyOntology(String namespace) throws OWLOntologyCreationException
	{
		this.namespace = namespace;
		manager = createOWLOntologyManager();
		ontology = createOWLOntology();
		prefixManager = createDefaultPrefixManager(ontology);
		swrlapiowlOntology = createSWRLAPIOWLOntology(ontology, prefixManager);

		prefixManager.setDefaultPrefix(namespace);

		return swrlapiowlOntology;
	}

	protected void declareOWLClass(String localName)
	{
		SWRLAPITestUtil.declareOWLClass(manager, ontology, namespace + localName);
	}

	protected void declareOWLNamedIndividual(String localName)
	{
		SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, namespace + localName);
	}

	protected void declareOWLNamedIndividuals(String... localNames)
	{
		for (String localName : localNames)
			SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, namespace + localName);
	}

	protected void declareOWLObjectProperties(String... localNames)
	{
		for (String localName : localNames)
			SWRLAPITestUtil.declareOWLObjectProperty(manager, ontology, namespace + localName);
	}

	protected void declareOWLObjectProperty(String localName)
	{
		SWRLAPITestUtil.declareOWLObjectProperty(manager, ontology, namespace + localName);
	}

	protected void declareOWLDataProperty(String localName)
	{
		SWRLAPITestUtil.declareOWLDataProperty(manager, ontology, namespace + localName);
	}

	protected void declareOWLDatatype(String prefixedName)
	{
		SWRLAPITestUtil.declareOWLDatatype(manager, ontology, prefixedName);
	}

	protected void declareOWLClasses(Set<String> localNames)
	{
		for (String localName : localNames)
			SWRLAPITestUtil.declareOWLClass(manager, ontology, namespace + localName);
	}

	protected void declareOWLNamedIndividuals(Set<String> localNames)
	{
		for (String localName : localNames)
			SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, namespace + localName);
	}

	protected void declareOWLClassAssertion(String classLocalName, String individualLocalName)
	{
		SWRLAPITestUtil.declareOWLClassAssertionAxiom(manager, ontology, namespace + classLocalName, namespace
				+ individualLocalName);
	}

	protected void declareOWLObjectPropertyAssertion(String subjectLocalName, String propertyLocalName,
			String objectLocalName)
	{
		SWRLAPITestUtil.declareOWLObjectPropertyAssertionAxiom(manager, ontology, namespace + subjectLocalName, namespace
				+ propertyLocalName, namespace + objectLocalName);
	}

	protected void declareOWLDataPropertyAssertion(String subjectLocalName, String propertyLocalName, String value,
			String datatypePrefixedName)
	{
		SWRLAPITestUtil.declareOWLDataPropertyAssertionAxiom(manager, ontology, namespace + subjectLocalName, namespace
				+ propertyLocalName, value, datatypePrefixedName, prefixManager);
	}

	protected void declareOWLSameAsAssertion(String individualLocalName1, String individualLocalName2)
	{
		SWRLAPITestUtil.declareOWLSameIndividualAxiom(manager, ontology, namespace + individualLocalName1, namespace
				+ individualLocalName2);
	}

	protected void declareOWLDifferentFromAssertion(String individualLocalName1, String individualLocalName2)
	{
		SWRLAPITestUtil.declareOWLDifferentIndividualsAxiom(manager, ontology, namespace + individualLocalName1, namespace
				+ individualLocalName2);
	}

	protected void declareOWLObjectPropertyDomainAxiom(String propertyLocalName, String classLocalName)
	{
		SWRLAPITestUtil.declareOWLObjectPropertyDomainAxiom(manager, ontology, namespace + propertyLocalName, namespace
				+ classLocalName);
	}

	protected void declareOWLDataPropertyDomainAxiom(String propertyLocalName, String classLocalName)
	{
		SWRLAPITestUtil.declareOWLDataPropertyDomainAxiom(manager, ontology, namespace + propertyLocalName, namespace
				+ classLocalName);
	}

	protected void createSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
	{
		swrlapiowlOntology.createSQWRLQuery(queryName, query);
	}

	protected SWRLAPIRule createSWRLRule(String ruleName, String rule) throws SWRLParseException
	{
		return swrlapiowlOntology.createSWRLRule(ruleName, rule);
	}
}
