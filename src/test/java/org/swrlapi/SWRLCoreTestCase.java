package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.parser.SWRLParseException;

import java.util.ArrayList;
import java.util.List;

import static org.swrlapi.SWRLAPITestUtil.createOWLOntologyManager;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntology;
import static org.swrlapi.SWRLAPITestUtil.createPrefixManager;
import static org.swrlapi.SWRLAPITestUtil.createSWRLAPIOWLOntology;
import static org.swrlapi.SWRLAPITestUtil.getIRI;
import static org.swrlapi.SWRLAPITestUtil.getOWLClass;
import static org.swrlapi.SWRLAPITestUtil.getOWLNamedIndividual;
import static org.swrlapi.SWRLAPITestUtil.getOWLDeclarationAxiom;

public class SWRLCoreTestCase
{
	String Namespace = "http://protege.org/ontologies/SWRLCoreTests.owl#";

	@Before
	public void setUp()
	{

	}

	@Test
	public void Test1() throws OWLOntologyCreationException, SWRLParseException
	{
		OWLOntologyManager manager = createOWLOntologyManager();
		OWLOntology ontology = createOWLOntology();
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);
		SWRLAPIOWLOntology swrlapiowlOntology = createSWRLAPIOWLOntology(ontology, prefixManager);

		OWLClass male = getOWLClass(getIRI(Namespace + "Male"));
		OWLDeclarationAxiom a1 = getOWLDeclarationAxiom(male);

		OWLNamedIndividual p1 = getOWLNamedIndividual(getIRI(Namespace + "p1"));
		OWLDeclarationAxiom a2 = getOWLDeclarationAxiom(p1);

		List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
		changes.add(new AddAxiom(ontology, a1));
		changes.add(new AddAxiom(ontology, a2));
		manager.applyChanges(changes);

		prefixManager.setDefaultPrefix(Namespace);

		//SWRLAPIRule rule = swrlapiowlOntology.getSWRLRule("Fred", "Male(p1) -> sqwrl:select(p1)");

		// test-class-atom-in-consequent-with-named-individual-query
		// Male(p1) → sqwrl:select(p1)
		// Male(p1) →  sqwrl:select(p1)
	}

}
