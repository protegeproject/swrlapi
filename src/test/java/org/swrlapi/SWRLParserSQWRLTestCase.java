package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import static org.swrlapi.SWRLAPITestUtil.createDefaultPrefixManager;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntology;
import static org.swrlapi.SWRLAPITestUtil.createOWLOntologyManager;
import static org.swrlapi.SWRLAPITestUtil.createSWRLAPIOWLOntology;

public class SWRLParserSQWRLTestCase
{
	String Namespace = "http://protege.org/ontologies/SWRLParserSQWRLTests.owl#";

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
	public void TestClassAtomInAntecedentWithNamedIndividual() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Male");
		declareOWLNamedIndividual("p1");

		createSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");
	}

	@Test
	public void TestClassAtomInAntecedentWithVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Male");

		createSQWRLQuery("q1", "Male(?m) -> sqwrl:select(?m)");
	}

	@Test
	public void TestClassAtomInAntecedentWithSetConstruction() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Male");

		createSQWRLQuery("q1", "Male(?m) . sqwrl:makeSet(?s, ?m) . sqwrl:element(?e, ?s) -> sqwrl:select(?e)");
	}

	@Test
	public void Test1() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1",
				"swrlb:booleanNot(?x, \"false\"^^\"xsd:boolean\") ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
	}

	@Test
	public void Test2() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1", "swrlb:add(?x, \"2.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\") ^ "
				+ "swrlb:multiply(?y, ?x, \"2.0\"^^\"xsd:double\") -> sqwrl:select(?y)");
	}

	@Test
	public void Test3() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Person");
		declareOWLDataProperty("hasSurname");

		createSQWRLQuery("q1", "Person(?p) ^ hasSurname(?p, \"Gunderson\") -> sqwrl:select(?p)");
	}

	@Test
	public void Test4() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("PersonNamedFred");

		createSQWRLQuery("q1", "PersonNamedFred(?fp) -> sqwrl:select(?fp) ^ sqwrl:orderBy(?fp)");
	}

	@Test
	public void Test5() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Person");
		declareOWLNamedIndividual("s4");
		declareOWLDataProperty("hasID");

		createSQWRLQuery("q1", "Person(?i1) ^ hasID(?i1, \"s3ID\") ^ sameAs(?i1, s4) -> sqwrl:selectDistinct(\"Yes!\")");
	}

	@Test
	public void Test6() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividual("p1");
		declareOWLDataProperty("hasFirstName");

		createSQWRLQuery("q1", "hasFirstName(p1, \"Fred\") -> sqwrl:select(p1)");
	}

	@Test
	public void Test7() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1", "temporal:add(?x, \"1999-11-01T10:00\", 4, \"Years\") ^ "
				+ " temporal:equals(?x, \"2003-11-01T10:00:00.0\"^^\"xsd:dateTime\") -> sqwrl:select(\"Yes!\")");
	}

	@Test
	public void Test8() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividual("p1");
		declareOWLDataProperty("hasDOB");

		createSQWRLQuery("q1",
				"hasDOB(p1, ?dob) ^ temporal:equals(?dob, \"2000-01-05\"^^\"xsd:date\") -> sqwrl:select(p1)");
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

	private void createSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
	{
		swrlapiowlOntology.getSQWRLQuery(queryName, query);
	}
}
