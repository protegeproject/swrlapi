package org.swrlapi.test;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.Set;

import static org.swrlapi.test.SWRLAPITestUtil.createDefaultPrefixManager;
import static org.swrlapi.test.SWRLAPITestUtil.createOWLOntology;
import static org.swrlapi.test.SWRLAPITestUtil.createOWLOntologyManager;
import static org.swrlapi.test.SWRLAPITestUtil.createSWRLAPIOWLOntology;

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

	protected void declareOWLClass(String name)
	{
		SWRLAPITestUtil.declareOWLClass(manager, ontology, namespace + name);
	}

	protected void declareOWLNamedIndividual(String name)
	{
		SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, namespace + name);
	}

	protected void declareOWLNamedIndividuals(String... names)
	{
		for (String name : names)
			SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, namespace + name);
	}

	protected void declareOWLObjectProperties(String... names)
	{
		for (String name : names)
			SWRLAPITestUtil.declareOWLObjectProperty(manager, ontology, namespace + name);
	}

	protected void declareOWLObjectProperty(String name)
	{
		SWRLAPITestUtil.declareOWLObjectProperty(manager, ontology, namespace + name);
	}

	protected void declareOWLDataProperty(String name)
	{
		SWRLAPITestUtil.declareOWLDataProperty(manager, ontology, namespace + name);
	}

	protected void declareOWLDatatype(String shortName)
	{
		SWRLAPITestUtil.declareOWLDatatype(manager, ontology, shortName);
	}

	protected void declareOWLClasses(Set<String> names)
	{
		for (String name : names)
			SWRLAPITestUtil.declareOWLClass(manager, ontology, namespace + name);
	}

	protected void declareOWLNamedIndividuals(Set<String> names)
	{
		for (String name : names)
			SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, namespace + name);
	}

	protected void declareOWLClassAssertion(String className, String individualName)
	{
		SWRLAPITestUtil.declareOWLClassAssertionAxiom(manager, ontology, namespace + className, namespace + individualName);
	}

	protected void declareOWLObjectPropertyAssertion(String subjectName, String propertyName, String objectName)
	{
		SWRLAPITestUtil
				.declareOWLObjectPropertyAssertionAxiom(manager, ontology, namespace + subjectName, namespace + propertyName,
						namespace + objectName);
	}

	protected void declareOWLDataPropertyAssertion(String subjectName, String propertyName, String value,
			String datatypeShortName)
	{
		SWRLAPITestUtil
				.declareOWLDataPropertyAssertionAxiom(manager, ontology, namespace + subjectName, namespace + propertyName,
						value, datatypeShortName, prefixManager);
	}

	protected void createSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
	{
		swrlapiowlOntology.createSQWRLQuery(queryName, query);
	}

	protected void createSWRLRule(String ruleName, String rule) throws SWRLParseException
	{
		swrlapiowlOntology.createSWRLRule(ruleName, rule);
	}
}
