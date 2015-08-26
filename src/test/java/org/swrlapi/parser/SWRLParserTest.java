package org.swrlapi.parser;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.test.IntegrationTestBase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

public class SWRLParserTest extends IntegrationTestBase
{
  private static final OWLClass PERSON = Class(iri("Person"));
  private static final OWLClass MALE = Class(iri("Male"));
  private static final OWLNamedIndividual P1 = NamedIndividual(iri("p1"));
  private static final OWLNamedIndividual P2 = NamedIndividual(iri("p2"));
  private static final OWLNamedIndividual P3 = NamedIndividual(iri("p3"));
  private static final OWLNamedIndividual P4 = NamedIndividual(iri("p4"));
  private static final OWLNamedIndividual I1 = NamedIndividual(iri("i1"));
  private static final OWLNamedIndividual I2 = NamedIndividual(iri("i2"));
  private static final OWLObjectProperty HAS_UNCLE = ObjectProperty(iri("hasUncle"));
  private static final OWLDataProperty HAS_NAME = DataProperty(iri("hasName"));
  private static final OWLDataProperty HAS_FIRST_NAME = DataProperty(iri("hasFirstName"));
  private static final OWLDataProperty HAS_HOME_PAGE = DataProperty(iri("hasHomePage"));
  private static final OWLDataProperty YEARS_TO_BIRTH = DataProperty(iri("yearsToBirth"));
  private static final OWLDataProperty HAS_ID = DataProperty(iri("hasID"));
  private static final OWLDataProperty HAS_AGE = DataProperty(iri("hasAge"));
  private static final OWLDataProperty IS_FRENCH = DataProperty(iri("isFrench"));
  private static final OWLDataProperty HAS_TOB = DataProperty(iri("hasTOB"));
  private static final OWLDataProperty HAS_DOB = DataProperty(iri("hasDOB"));
  private static final OWLDataProperty HAS_HEIGHT = DataProperty(iri("hasHeight"));
  private static final OWLDataProperty HAS_LAST_ACCESS_TIME = DataProperty(iri("hasLastAccessTime"));

  @Test public void TestClassAtomInConsequentWithShortNamedIndividual()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(MALE), Declaration(P1));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "-> Male(p1)");

    assertEquals(rule.getBodyAtoms().size(), 0);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test public void TestClassAtomInConsequentWithPrefixedNamedIndividual()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(MALE), Declaration(P1));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "-> Male(:p1)");

    assertEquals(rule.getBodyAtoms().size(), 0);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test public void TestClassAtomInAntecedentWithVariable() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(MALE));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "Male(?m) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test public void TestClassAtomInAntecedentWithPrefixedVariable()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(MALE));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "Male(?m) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test public void TestClassAtomInAntecedentWithName() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);
    addOWLAxioms(ontology, Declaration(MALE), Declaration(P1));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "Male(p1) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  // TODO owl:Thing should be accessible in rules
  // @Test
  // public void TestOWLThingClass() throws SWRLParseException
  // {
  // SWRLAPIRule rule = createSWRLRule("r1", "owl:Thing(?x) -> ");
  //
  // assertEquals(rule.getBodyAtoms().size(), 1);
  // assertEquals(rule.getHeadAtoms().size(), 0);
  // assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLClassAtom.class));
  // }

  @Test public void TestStringLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_NAME));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "hasName(?p, \"Fred\") ->");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test public void TestRawBooleanTrueLowerCaseLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(IS_FRENCH));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "isFrench(?f, true) ->");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test public void TestRawBooleanFalseLowerCaseLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(IS_FRENCH));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "isFrench(?f, false) ->");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test public void TestRawBooleanTrueUpperCaseLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(IS_FRENCH));

    swrlapiOWLOntology.createSWRLRule("r1", "isFrench(?f, True) ->");
  }

  @Test public void TestRawBooleanFalseUpperCaseLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(IS_FRENCH));

    swrlapiOWLOntology.createSWRLRule("r1", "isFrench(?f, False) ->");
  }

  @Test public void TestBooleanTrueQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(IS_FRENCH));

    swrlapiOWLOntology.createSWRLRule("r1", "isFrench(?f, \"true\"^^xsd:boolean) ->");
  }

  @Test public void TestByteLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:byte) ->");
  }

  @Test public void TestNegativeByteLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(YEARS_TO_BIRTH));

    swrlapiOWLOntology.createSWRLRule("r1", "yearsToBirth(?p, \"-34\"^^xsd:byte) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidByteLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:byte) ->");
  }

  @Test public void TestShortLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:short) ->");
  }

  @Test public void TestNegativeShortLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"-34\"^^xsd:short) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidShortLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:short) ->");
  }

  @Test public void TestRawIntLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, 34) ->");
  }

  @Test public void TestRawNegativeIntLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, -34) ->");
  }

  @Test public void TestIntQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:int) ->");
  }

  @Test public void TestNegativeIntQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"-34\"^^xsd:int) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidIntLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:int) ->");
  }

  @Test public void TestLongLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:long) ->");
  }

  @Test public void TestNegativeLongLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);
    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"-34\"^^xsd:long) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidLongLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);
    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:long) ->");
  }

  @Test public void TestFloatRawLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, 34.5) ->");
  }

  @Test public void TestNegativeFloatRawLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, -34.5) ->");
  }

  @Test public void TestFloatQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, \"34.0\"^^xsd:float) ->");
  }

  @Test public void TestNegativeFloatQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, \"-34.0\"^^xsd:float) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidFloatLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, \"x34.0\"^^xsd:float) ->");
  }

  @Test public void TestDoubleQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, \"34.0\"^^xsd:double) ->");
  }

  @Test public void TestNegativeDoubleQualifiedLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, \"-34.0\"^^xsd:double) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidDoubleLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HEIGHT));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHeight(?p, \"x34.0\"^^xsd:double) ->");
  }

  @Test public void TestURILiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HOME_PAGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHomePage(?p, \"http://stanford.edu/~fred\"^^xsd:anyURI) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidURILiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_HOME_PAGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasHomePage(?p, \":stanford.edu/~fred\"^^xsd:anyURI) ->");
  }

  @Test public void TestDateLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_DOB));

    swrlapiOWLOntology.createSWRLRule("r1", "hasDOB(?p, \"1999-11-22\"^^xsd:date) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidDateLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_DOB));

    swrlapiOWLOntology.createSWRLRule("r1", "hasDOB(?p, \"x199-11-22\"^^xsd:date) ->");
  }

  @Test public void TestTimeLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_TOB));

    swrlapiOWLOntology.createSWRLRule("r1", "hasTOB(?p, \"10:10:10.23\"^^xsd:time) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidTimeLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_TOB));

    swrlapiOWLOntology.createSWRLRule("r1", "hasTOB(?p, \"10:0:10.23\"^^xsd:time) ->");
  }

  @Test public void TestDateTimeLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_TOB));

    swrlapiOWLOntology.createSWRLRule("r1", "hasTOB(?p, \"1999-11-22T10:10:10.23\"^^xsd:dateTime) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidDateTimeLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_TOB));

    swrlapiOWLOntology.createSWRLRule("r1", "hasTOB(?p, \"x1999-11-22T10:10:10.23\"^^xsd:dateTime) ->");
  }

  @Test public void TestDurationLiteral() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"P43Y\"^^xsd:duration) ->");
  }

  @Test(expected = SWRLParseException.class) public void TestInvalidDurationLiteral()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(?p, \"43Y\"^^xsd:duration) ->");
  }

  @Test public void TestAddAndEqualsTemporalBuiltIns() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    swrlapiOWLOntology.createSWRLRule("r1", "temporal:add(?x, \"1999-11-01T10:00:00\"^^xsd:dateTime, 4, \"Years\") ^ "
      + "temporal:equals(?x, \"2003-11-01T10:00:00.0\"^^xsd:dateTime) " + "-> sqwrl:select(\"Yes!\")");
  }

  @Test public void TestObjectPropertyInAntecedentWithVariables()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_UNCLE));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "hasUncle(?p, ?u) -> ");

    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLObjectPropertyAtom.class));
  }

  @Test public void TestObjectPropertyInAntecedentWithNamedIndividuals()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_UNCLE), Declaration(P1), Declaration(P2));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "hasUncle(p1, p2) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLObjectPropertyAtom.class));
  }

  @Test public void TestObjectPropertyInConsequentWithNamedIndividals()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_UNCLE), Declaration(P1), Declaration(P2));

    swrlapiOWLOntology.createSWRLRule("r1", "-> hasUncle(p1, p2)");
  }

  @Test public void TestDataPropertyInAntecedent() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE), Declaration(P3));

    swrlapiOWLOntology.createSWRLRule("r1", "hasAge(p3, ?a) -> ");
  }

  @Test public void TestDataPropertyInConsequent() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_AGE), Declaration(P4));

    swrlapiOWLOntology.createSWRLRule("r1", " -> hasAge(p4, 34)");
  }

  @Test public void TestClassAtomInAntecedentWithNamedIndividual()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(MALE), Declaration(P1));

    swrlapiOWLOntology.createSWRLRule("r1", "Male(p1) ->");
  }

  @Test public void TestClassAtomInConsequent() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(MALE), Declaration(P1));

    swrlapiOWLOntology.createSWRLRule("r1", "-> Male(p1)");
  }

  @Test public void TestBuiltInWithLiteralsAndVariables() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(HAS_LAST_ACCESS_TIME), Declaration(P1));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1",
      "swrlb:addDayTimeDurationToDateTime(?dt, \"1999-01-01T12:12:12\", \"P1Y\") -> hasLastAccessTime(p1, ?dt)");

    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLBuiltInAtom.class));
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test public void TestSameAsInConsequentWithNamedIndividualAndVariable()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(PERSON), Declaration(HAS_ID), Declaration(I1));

    SWRLAPIRule rule = swrlapiOWLOntology.createSWRLRule("r1", "Person(?i2) ^ hasID(?i2, \"i2ID\") -> sameAs(i1, ?i2)");

    assertEquals(rule.getBodyAtoms().size(), 2);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
    assertThat(rule.getBodyAtoms().get(1), instanceOf(SWRLDataPropertyAtom.class));
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLSameIndividualAtom.class));
  }

  @Test public void TestSameAsInAntecedentWithVariables() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    swrlapiOWLOntology.createSWRLRule("r1", "sameAs(?i1, ?i2) ->");
  }

  @Test public void TestSameAsInAntecedentWithNamedIndividual() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(I1), Declaration(I2));

    swrlapiOWLOntology.createSWRLRule("r1", "sameAs(i1, i2) ->");
  }

  @Test public void TestSameAsInConsequentWithNamedIndividual() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(I1), Declaration(I2));

    swrlapiOWLOntology.createSWRLRule("r1", "-> sameAs(i1, i2)");
  }

  @Test public void TestDifferentFromInAntecedentWithVariables() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    swrlapiOWLOntology.createSWRLRule("r1", "differentFrom(?i1, ?i2) ->");
  }

  @Test public void TestDifferentFromInAntecedentWithNamedIndividual()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(I1), Declaration(I2));

    swrlapiOWLOntology.createSWRLRule("r1", "differentFrom(i1, i2) ->");
  }

  @Test public void TestDifferentFromInConsequentWithNamedIndividual()
    throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(I1), Declaration(I2));

    swrlapiOWLOntology.createSWRLRule("r1", "-> differentFrom(i1, i2)");
  }

  @Test public void TestClassAndDataPropertyAtom() throws SWRLParseException, OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    DefaultPrefixManager prefixManager = createPrefixManager(ontology);
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(ontology, prefixManager);

    addOWLAxioms(ontology, Declaration(PERSON), Declaration(HAS_ID), Declaration(HAS_FIRST_NAME));

    swrlapiOWLOntology.createSWRLRule("r1", "Person(?p) ^ hasID(?p, \"p7ID\") -> hasFirstName(?p, \"Angela\")");
  }
}
