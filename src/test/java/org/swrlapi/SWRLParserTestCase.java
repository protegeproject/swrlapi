package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.parser.SWRLParseException;

import static org.swrlapi.SWRLAPITestUtil.createDefaultPrefixManager;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntology;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntologyManager;
import static org.swrlapi.SWRLAPITestUtil.createSWRLAPIOWLOntology;

public class SWRLParserTestCase
{
	String Namespace = "http://protege.org/ontologies/SWRLParserTests.owl#";

	OWLOntologyManager manager;
	OWLOntology ontology;
	DefaultPrefixManager prefixManager;
	SWRLAPIOWLOntology swrlapiowlOntology;

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		manager = createOWLOntologyManager();
		ontology = createOWLOntology();
		prefixManager = createDefaultPrefixManager(ontology);
		swrlapiowlOntology = createSWRLAPIOWLOntology(ontology, prefixManager);

		prefixManager.setDefaultPrefix(Namespace);

		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#dateTime");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#date");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#string");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#int");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#float");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#double");
		declareOWLDatatype("http://www.w3.org/2001/XMLSchema#boolean");
	}

	@Test
	public void TestClassAtomInConsequentWithNamedIndividual() throws SWRLParseException
	{
		declareOWLClass("Male");
		declareOWLNamedIndividual("p1");

		createSWRLRule("r1", "-> Male(p1)");
	}

	@Test
	public void TestClassAtomInAntecedentWithVariable() throws SWRLParseException
	{
		declareOWLClass("Male");

		createSWRLRule("r1", "Male(?m) -> ");
	}

	@Test
	public void TestClassAtomInAntecedentWithName() throws SWRLParseException
	{
		declareOWLClass("Male");
		declareOWLNamedIndividual("p1");

		createSWRLRule("r1", "Male(p1) -> ");
	}

	@Test
	public void Test1() throws SWRLParseException
	{
		declareOWLClass("Male");
		declareOWLNamedIndividual("p1");

		createSWRLRule("r1", "temporal:add(?x, \"1999-11-01T10:00\"^^\"xsd:dateTime\", 4, \"Years\") ^ " +
				"temporal:equals(?x, \"2003-11-01T10:00:00.0\"^^\"xsd:dateTime\") " +
				"-> sqwrl:select(\"Yes!\")");
	}

	@Test
	public void Test2() throws SWRLParseException
	{
		declareOWLDataProperty("hasAge");
		declareOWLNamedIndividual("p3");
		declareOWLNamedIndividual("p4");

		createSWRLRule("r1", "hasAge(p3, ?a) -> hasAge(p4, ?a)");
	}

	@Test
	public void Test3() throws SWRLParseException
	{
		declareOWLClass("CreatedPerson");
		declareOWLNamedIndividual("p15");

		createSWRLRule("r1", "swrlx:makeOWLThing(?i, p15) -> CreatedPerson(?i)");
	}

	@Test
	public void Test4() throws SWRLParseException
	{
		declareOWLNamedIndividual("p13");
		declareOWLDataProperty("hasLastAccessTime");

		createSWRLRule("r1",
				"swrlb:addDayTimeDurationToDateTime(?dt, \"1999-01-01T12:12:12\"^^\"xsd:string\", \"P1Y\"^^\"xsd:string\") -> hasLastAccessTime(p13, ?dt)");
	}

	@Test
	public void Test5() throws SWRLParseException
	{
		declareOWLClass("Person");
		declareOWLDataProperty("hasID");
		declareOWLNamedIndividual("s12");

		createSWRLRule("r1", "Person(?i2) ^ hasID(?i2, \"s13ID\"^^\"xsd:string\") -> sameAs(s12, ?i2)");
	}

	@Test
	public void Test6() throws SWRLParseException
	{
		declareOWLClass("Person");
		declareOWLDataProperty("hasID");
		declareOWLDataProperty("hasFirstName");
		declareOWLNamedIndividual("s12");

		createSWRLRule("r1",
				"Person(?p) ^ hasID(?p, \"p7ID\"^^\"xsd:string\") -> hasFirstName(?p, \"Angela\"^^\"xsd:string\")");
	}

	private void declareOWLClass(String name)
	{
		SWRLAPITestUtil.declareOWLClass(manager, ontology, Namespace + name);
	}

	private void declareOWLNamedIndividual(String name)
	{
		SWRLAPITestUtil.declareOWLNamedIndividual(manager, ontology, Namespace + name);
	}

	private void declareOWLObjectProperty(String name)
	{
		SWRLAPITestUtil.declareOWLObjectProperty(manager, ontology, Namespace + name);
	}

	private void declareOWLDataProperty(String name)
	{
		SWRLAPITestUtil.declareOWLDataProperty(manager, ontology, Namespace + name);
	}

	private void declareOWLDatatype(String shortName)
	{
		SWRLAPITestUtil.declareOWLDatatype(manager, ontology, shortName);
	}

	private void createSWRLRule(String ruleName, String rule) throws SWRLParseException
	{
		swrlapiowlOntology.getSWRLRule(ruleName, rule);
	}

}
