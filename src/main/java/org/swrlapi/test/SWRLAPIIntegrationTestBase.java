package org.swrlapi.test;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.Set;

public class SWRLAPIIntegrationTestBase
{
  final protected double DELTA = 1e-6;

  private String namespace;
  private OWLOntology ontology;
  private OWLOntologyManager manager;
  private SWRLRuleEngine swrlRuleEngine;
  private SQWRLQueryEngine sqwrlQueryEngine;
  private DefaultPrefixManager prefixManager;
  private SWRLAPIOWLOntology swrlapiOWLOntology;

  // TODO This approach does not allow tests to be run in parallel
  protected void createOWLOntology() throws OWLOntologyCreationException
  {
    this.namespace = ":";
    this.manager = OWLManager.createOWLOntologyManager();
    this.ontology = this.manager.createOntology();
    this.prefixManager = new DefaultPrefixManager();
    this.prefixManager.setDefaultPrefix(this.namespace);
    this.swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(this.ontology, this.prefixManager);
  }

  protected void createOWLOntologyAndSQWRLQueryEngine() throws OWLOntologyCreationException
  {
    this.namespace = ":";
    this.manager = OWLManager.createOWLOntologyManager();
    this.ontology = this.manager.createOntology();
    this.prefixManager = new DefaultPrefixManager();
    this.prefixManager.setDefaultPrefix(this.namespace);
    this.swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(this.ontology, this.prefixManager);
    this.sqwrlQueryEngine = SWRLAPIFactory.createSQWRLQueryEngine(this.ontology);
  }

  protected void createSQWRLQuery(@NonNull String queryName, @NonNull String query)
    throws SQWRLException, SWRLParseException
  {
    this.swrlapiOWLOntology.createSQWRLQuery(queryName, query);
  }

  @NonNull protected SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule)
    throws SWRLParseException
  {
    return this.swrlapiOWLOntology.createSWRLRule(ruleName, rule);
  }

  @NonNull protected SQWRLResult executeSQWRLQuery(@NonNull String queryName) throws SQWRLException
  {
    return this.sqwrlQueryEngine.runSQWRLQuery(queryName);
  }

  @NonNull protected SQWRLResult executeSQWRLQuery(@NonNull String queryName, @NonNull String query)
    throws SQWRLException, SWRLParseException
  {
    createSQWRLQuery(queryName, query);

    return executeSQWRLQuery(queryName);
  }

  protected void declareOWLClass(@NonNull String localName)
  {
    SWRLAPITestUtil.declareOWLClass(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLClasses(@NonNull String... localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLClass(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLNamedIndividual(@NonNull String localName)
  {
    SWRLAPITestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLNamedIndividuals(@NonNull String... localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLObjectProperties(@NonNull String... localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLObjectProperty(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLObjectProperty(@NonNull String localName)
  {
    SWRLAPITestUtil.declareOWLObjectProperty(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLDataProperty(@NonNull String localName)
  {
    SWRLAPITestUtil.declareOWLDataProperty(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLDatatype(@NonNull String prefixedName)
  {
    SWRLAPITestUtil.declareOWLDatatype(this.manager, this.ontology, prefixedName);
  }

  protected void declareOWLClasses(@NonNull Set<String> localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLClass(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLNamedIndividuals(@NonNull Set<String> localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLClassAssertion(String classLocalName, String individualLocalName)
  {
    SWRLAPITestUtil.declareOWLClassAssertionAxiom(this.manager, this.ontology, this.namespace + classLocalName,
      this.namespace + individualLocalName);
  }

  protected void declareOWLObjectPropertyAssertion(@NonNull String subjectLocalName, @NonNull String propertyLocalName,
    @NonNull String objectLocalName)
  {
    SWRLAPITestUtil
      .declareOWLObjectPropertyAssertionAxiom(this.manager, this.ontology, this.namespace + subjectLocalName,
        this.namespace + propertyLocalName, this.namespace + objectLocalName);
  }

  protected void declareOWLDataPropertyAssertion(@NonNull String subjectLocalName, @NonNull String propertyLocalName,
    @NonNull String value, @NonNull String datatypePrefixedName)
  {
    SWRLAPITestUtil.declareOWLDataPropertyAssertionAxiom(this.manager, this.ontology, this.namespace + subjectLocalName,
      this.namespace + propertyLocalName, value, datatypePrefixedName, this.prefixManager);
  }

  protected void declareOWLSameAsAssertion(@NonNull String individualLocalName1, @NonNull String individualLocalName2)
  {
    SWRLAPITestUtil.declareOWLSameIndividualAxiom(this.manager, this.ontology, this.namespace + individualLocalName1,
      this.namespace + individualLocalName2);
  }

  protected void declareOWLDifferentFromAssertion(@NonNull String individualLocalName1,
    @NonNull String individualLocalName2)
  {
    SWRLAPITestUtil
      .declareOWLDifferentIndividualsAxiom(this.manager, this.ontology, this.namespace + individualLocalName1,
        this.namespace + individualLocalName2);
  }

  protected void declareOWLObjectPropertyDomainAxiom(@NonNull String propertyLocalName, @NonNull String classLocalName)
  {
    SWRLAPITestUtil.declareOWLObjectPropertyDomainAxiom(this.manager, this.ontology, this.namespace + propertyLocalName,
      this.namespace + classLocalName);
  }

  protected void declareOWLDataPropertyDomainAxiom(@NonNull String propertyLocalName, @NonNull String classLocalName)
  {
    SWRLAPITestUtil.declareOWLDataPropertyDomainAxiom(this.manager, this.ontology, this.namespace + propertyLocalName,
      this.namespace + classLocalName);
  }
}
