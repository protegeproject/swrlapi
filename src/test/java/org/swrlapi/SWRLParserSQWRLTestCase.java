package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.SWRLAPIIntegrationTestBase;

public class SWRLParserSQWRLTestCase extends SWRLAPIIntegrationTestBase
{
	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		createOWLOntology();
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
	public void TestSetConstruction() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Male");

		createSQWRLQuery("q1", "Male(?m) . sqwrl:makeSet(?s, ?m) . sqwrl:element(?e, ?s) -> sqwrl:select(?e)");
	}

	@Test
	public void TestBooleanTrueRawLiteral() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1", "swrlb:booleanNot(?x, true) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
	}

	@Test
	public void TestBooleanFalseRawLiteral() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1", "swrlb:booleanNot(?x, false) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
	}

	@Test
	public void TestBooleanQualifiedLiteral() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1",
				"swrlb:booleanNot(?x, \"false\"^^xsd:boolean) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
	}

	@Test
	public void TestUnboundVariableQuery() throws SWRLParseException, SQWRLException
	{
		createSQWRLQuery("q1", "swrlb:add(?x, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double) ^ "
				+ "swrlb:multiply(?y, ?x, \"2.0\"^^xsd:double) -> sqwrl:select(?y)");
	}

	@Test
	public void TestBasicDatatypeSelectionQuery() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Person");
		declareOWLDataProperty("hasSurname");

		createSQWRLQuery("q1", "Person(?p) ^ hasSurname(?p, \"Gunderson\") -> sqwrl:select(?p)");
	}

	@Test
	public void TestOrderBy() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("PersonNamedFred");

		createSQWRLQuery("q1", "PersonNamedFred(?fp) -> sqwrl:select(?fp) ^ sqwrl:orderBy(?fp)");
	}

	@Test
	public void TestSelectDistinct() throws SWRLParseException, SQWRLException
	{
		declareOWLClass("Person");
		declareOWLNamedIndividual("s4");
		declareOWLDataProperty("hasID");

		createSQWRLQuery("q1", "Person(?i1) ^ hasID(?i1, \"s3ID\") ^ sameAs(?i1, s4) -> sqwrl:selectDistinct(\"Yes!\")");
	}
}
