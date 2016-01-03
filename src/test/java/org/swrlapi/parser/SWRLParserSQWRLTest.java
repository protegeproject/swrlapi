package org.swrlapi.parser;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.IntegrationTestBase;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

public class SWRLParserSQWRLTest extends IntegrationTestBase
{
  private static final OWLClass PERSON = Class(iri("Person"));
  private static final OWLClass PERSON_NAMED_FRED = Class(iri("PersonNamedFred"));
  private static final OWLClass MALE = Class(iri("Male"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual S4 = NamedIndividual(iri("s4"));
  private static final OWLDataProperty HAS_SURNAME = DataProperty(iri("hasSurname"));
  private static final OWLDataProperty HAS_ID = DataProperty(iri("hasID"));

  @Test public void TestClassAtomInAntecedentWithNamedIndividual()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    addOWLAxioms(ontology, Declaration(MALE), Declaration(P1));

    swrlapiOWLOntology.createSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");
  }

  @Test public void TestClassAtomInAntecedentWithVariable()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    addOWLAxioms(ontology, Declaration(MALE));

    swrlapiOWLOntology.createSQWRLQuery("q1", "Male(?m) -> sqwrl:select(?m)");
  }

  @Test public void TestSetConstruction() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    addOWLAxioms(ontology, Declaration(MALE));

    swrlapiOWLOntology
      .createSQWRLQuery("q1", "Male(?m) . sqwrl:makeSet(?s, ?m) . sqwrl:element(?e, ?s) -> sqwrl:select(?e)");
  }

  @Test public void TestBooleanTrueRawLiteral() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    swrlapiOWLOntology
      .createSQWRLQuery("q1", "swrlb:booleanNot(?x, true) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
  }

  @Test public void TestBooleanFalseRawLiteral() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    swrlapiOWLOntology
      .createSQWRLQuery("q1", "swrlb:booleanNot(?x, false) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
  }

  @Test public void TestBooleanQualifiedLiteral()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    swrlapiOWLOntology.createSQWRLQuery("q1",
      "swrlb:booleanNot(?x, \"false\"^^xsd:boolean) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)");
  }

  @Test public void TestUnboundVariableQuery() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    swrlapiOWLOntology.createSQWRLQuery("q1", "swrlb:add(?x, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double) ^ "
      + "swrlb:multiply(?y, ?x, \"2.0\"^^xsd:double) -> sqwrl:select(?y)");
  }

  @Test public void TestBasicDatatypeSelectionQuery()
    throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    addOWLAxioms(ontology, Declaration(PERSON), Declaration(HAS_SURNAME));

    swrlapiOWLOntology.createSQWRLQuery("q1", "Person(?p) ^ hasSurname(?p, \"Gunderson\") -> sqwrl:select(?p)");
  }

  @Test public void TestOrderBy() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    addOWLAxioms(ontology, Declaration(PERSON_NAMED_FRED));

    swrlapiOWLOntology.createSQWRLQuery("q1", "PersonNamedFred(?fp) -> sqwrl:select(?fp) ^ sqwrl:orderBy(?fp)");
  }

  @Test public void TestSelectDistinct() throws SWRLParseException, SQWRLException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology);

    addOWLAxioms(ontology, Declaration(PERSON), Declaration(S4), Declaration(HAS_ID));

    swrlapiOWLOntology
      .createSQWRLQuery("q1", "Person(?i1) ^ hasID(?i1, \"s3ID\") ^ sameAs(?i1, s4) -> sqwrl:selectDistinct(\"Yes!\")");
  }
}
