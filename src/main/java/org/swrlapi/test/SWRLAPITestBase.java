package org.swrlapi.test;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

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

		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#dateTime");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#date");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#string");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#int");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#long");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#float");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#double");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#boolean");

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

	protected void createSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
	{
		swrlapiowlOntology.getSQWRLQuery(queryName, query);
	}

	protected void createSWRLRule(String ruleName, String rule) throws SWRLParseException
	{
		swrlapiowlOntology.getSWRLRule(ruleName, rule);
	}
}
