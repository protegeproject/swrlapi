package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.test.SWRLAPITestBase;

public class SWRLParserTestCase extends SWRLAPITestBase
{
	String Namespace = "http://protege.org/ontologies/SWRLParserTests.owl#";

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		createEmptyOntology(Namespace);
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
	public void TestStringLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasName");

		createSWRLRule("r1", "hasName(?p, \"Fred\") ->");
	}

	@Test
	public void TestRawBooleanTrueLowerCaseLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("isFrench");

		createSWRLRule("r1", "isFrench(?f, true) ->");
	}

	@Test
	public void TestRawBooleanFalseLowerCaseLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("isFrench");

		createSWRLRule("r1", "isFrench(?f, false) ->");
	}

	@Test
	public void TestRawBooleanTrueUpperCaseLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("isFrench");

		createSWRLRule("r1", "isFrench(?f, True) ->");
	}

	@Test
	public void TestRawBooleanFalseUpperCaseLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("isFrench");

		createSWRLRule("r1", "isFrench(?f, False) ->");
	}

	@Test
	public void TestBooleanTrueQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("isFrench");

		createSWRLRule("r1", "isFrench(?f, \"true\"^^\"xsd:boolean\") ->");
	}

	@Test
	public void TestRawIntLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasAge");

		createSWRLRule("r1", "hasAge(?p, 34) ->");
	}

	@Test
	public void TestLongQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasAge");

		createSWRLRule("r1", "hasAge(?p, \"34\"^^\"xsd:long\") ->");
	}

	@Test
	public void TestIntQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasAge");

		createSWRLRule("r1", "hasAge(?p, \"34\"^^\"xsd:int\") ->");
	}

	@Test
	public void TestFloatQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasHeight");

		createSWRLRule("r1", "hasHeight(?p, \"34.0\"^^\"xsd:float\") ->");
	}

	@Test
	public void TestDoubleQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasHeight");

		createSWRLRule("r1", "hasHeight(?p, \"34.0\"^^\"xsd:double\") ->");
	}

	@Test
	public void TestFloatRawLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasHeight");

		createSWRLRule("r1", "hasHeight(?p, 34.5) ->");
	}

	@Test
	public void TestDateQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasDOB");

		createSWRLRule("r1", "hasDOB(?p, \"1999-11-22\"^^\"xsd:date\") ->");
	}

	@Test
	public void TestDateTimeQualifiedLiteral() throws SWRLParseException
	{
		declareOWLDataProperty("hasTOB");

		createSWRLRule("r1", "hasTOB(?p, \"1999-11-22T10:10:10.23\"^^\"xsd:dateTime\") ->");
	}

	@Test
	public void TestAddAndEqualsTemporalBuiltIns() throws SWRLParseException
	{
		createSWRLRule("r1", "temporal:add(?x, \"1999-11-01T10:00\"^^\"xsd:dateTime\", 4, \"Years\") ^ " +
				"temporal:equals(?x, \"2003-11-01T10:00:00.0\"^^\"xsd:dateTime\") " +
				"-> sqwrl:select(\"Yes!\")");
	}

	@Test
	public void TestObjectPropertyInAntecedentWithVariables() throws SWRLParseException
	{
		declareOWLObjectProperty("hasUncle");

		createSWRLRule("r1", "hasUncle(?p, ?u) -> ");
	}

	@Test
	public void TestObjectPropertyInAntecedentWithNamedIndivudals() throws SWRLParseException
	{
		declareOWLObjectProperty("hasUncle");
		declareOWLNamedIndividual("p1");
		declareOWLNamedIndividual("p2");

		createSWRLRule("r1", "hasUncle(p1, p2) -> ");
	}

	@Test
	public void TestObjectPropertyInConsequentWithNamedIndivudals() throws SWRLParseException
	{
		declareOWLObjectProperty("hasUncle");
		declareOWLNamedIndividual("p1");
		declareOWLNamedIndividual("p2");

		createSWRLRule("r1", "-> hasUncle(p1, p2)");
	}

	@Test
	public void TestDataPropertyInAntecedent() throws SWRLParseException
	{
		declareOWLDataProperty("hasAge");
		declareOWLNamedIndividual("p3");

		createSWRLRule("r1", "hasAge(p3, ?a) -> ");
	}

	@Test
	public void TestDataPropertyInConsequent() throws SWRLParseException
	{
		declareOWLDataProperty("hasAge");
		declareOWLNamedIndividual("p4");

		createSWRLRule("r1", " -> hasAge(p4, 34)");
	}

	@Test
	public void TestClassAtomInAntecedentWithNamedIndividual() throws SWRLParseException
	{
		declareOWLClass("Male");
		declareOWLNamedIndividual("m1");

		createSWRLRule("r1", "Male(m1) ->");
	}

	@Test
	public void TestClassAtomInConsequent() throws SWRLParseException
	{
		declareOWLClass("Male");
		declareOWLNamedIndividual("m1");

		createSWRLRule("r1", "-> Male(m1)");
	}

	@Test
	public void TestBuiltInWithLiteralsAndVariables() throws SWRLParseException
	{
		declareOWLNamedIndividual("p13");
		declareOWLDataProperty("hasLastAccessTime");

		createSWRLRule("r1",
				"swrlb:addDayTimeDurationToDateTime(?dt, \"1999-01-01T12:12:12\", \"P1Y\") -> hasLastAccessTime(p13, ?dt)");
	}

	@Test
	public void TestSameAsInConsequentWithNamedIndiviualAndVariable() throws SWRLParseException
	{
		declareOWLClass("Person");
		declareOWLDataProperty("hasID");
		declareOWLNamedIndividual("s12");

		createSWRLRule("r1", "Person(?i2) ^ hasID(?i2, \"s13ID\") -> sameAs(s12, ?i2)");
	}

	@Test
	public void TestSameAsInAntecedentWithVariables() throws SWRLParseException
	{
		createSWRLRule("r1", "sameAs(?i1, ?i2) ->");
	}

	@Test
	public void TestSameAsInAntecedentWithNamedIndividual() throws SWRLParseException
	{
		declareOWLNamedIndividual("i1");
		declareOWLNamedIndividual("i2");

		createSWRLRule("r1", "sameAs(i1, i2) ->");
	}

	@Test
	public void TestSameAsInConsequentWithNamedIndividual() throws SWRLParseException
	{
		declareOWLNamedIndividual("i1");
		declareOWLNamedIndividual("i2");

		createSWRLRule("r1", "-> sameAs(i1, i2)");
	}

	@Test
	public void TestDifferentFromInAntecedentWithVariables() throws SWRLParseException
	{
		createSWRLRule("r1", "differentFrom(?i1, ?i2) ->");
	}

	@Test
	public void TestDifferentFromInAntecedentWithNamedIndividual() throws SWRLParseException
	{
		declareOWLNamedIndividual("i1");
		declareOWLNamedIndividual("i2");

		createSWRLRule("r1", "differentFrom(i1, i2) ->");
	}

	@Test
	public void TestDifferentFromInConsequentWithNamedIndividual() throws SWRLParseException
	{
		declareOWLNamedIndividual("i1");
		declareOWLNamedIndividual("i2");

		createSWRLRule("r1", "-> differentFrom(i1, i2)");
	}

	@Test
	public void TestClassAndDataPropertyAtom() throws SWRLParseException
	{
		declareOWLClass("Person");
		declareOWLDataProperty("hasID");
		declareOWLDataProperty("hasFirstName");
		declareOWLNamedIndividual("s12");

		createSWRLRule("r1", "Person(?p) ^ hasID(?p, \"p7ID\") -> hasFirstName(?p, \"Angela\")");
	}
}
