package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import static org.swrlapi.SWRLAPITestUtil.createDefaultPrefixManager;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntology;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntologyManager;
import static org.swrlapi.SWRLAPITestUtil.createSWRLAPIOWLOntology;
import static org.swrlapi.SWRLAPITestUtil.declareOWLClass;
import static org.swrlapi.SWRLAPITestUtil.declareOWLNamedIndividual;

public class SWRLCoreTestCase
{
	String Namespace = "http://protege.org/ontologies/SWRLCoreTests.owl#";

	@Before
	public void setUp()
	{

	}

	@Test
	public void TestClassAtomInConsequentWithNamedIndividual()
			throws OWLOntologyCreationException, SWRLParseException, SQWRLException
	{
		OWLOntologyManager manager = createOWLOntologyManager();
		OWLOntology ontology = createOWLOntology();
		DefaultPrefixManager prefixManager = createDefaultPrefixManager(ontology);
		SWRLAPIOWLOntology swrlapiowlOntology = createSWRLAPIOWLOntology(ontology, prefixManager);

		prefixManager.setDefaultPrefix(Namespace);

		declareOWLClass(manager, ontology, Namespace + "Male");
		declareOWLNamedIndividual(manager, ontology, Namespace + "p1");

		SWRLAPIRule rule = swrlapiowlOntology.getSWRLRule("r1", "-> Male(p1)");
		SQWRLQuery query = swrlapiowlOntology.getSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");

		//SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(swrlapiowlOntology);

		// Male(p1) →  sqwrl:select(p1)
	}

}
