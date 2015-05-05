package org.swrlapi.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
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

  protected void createSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
  {
    this.swrlapiOWLOntology.createSQWRLQuery(queryName, query);
  }

  protected SWRLAPIRule createSWRLRule(String ruleName, String rule) throws SWRLParseException
  {
    return this.swrlapiOWLOntology.createSWRLRule(ruleName, rule);
  }

  protected SQWRLResult executeSQWRLQuery(String queryName) throws SQWRLException
  {
    return this.sqwrlQueryEngine.runSQWRLQuery(queryName);
  }

  protected SQWRLResult executeSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
  {
    createSQWRLQuery(queryName, query);

    return executeSQWRLQuery(queryName);
  }

  protected void declareOWLClass(String localName)
  {
    SWRLAPITestUtil.declareOWLClass(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLClasses(String... localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLClass(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLNamedIndividual(String localName)
  {
    SWRLAPITestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLNamedIndividuals(String... localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLObjectProperties(String... localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLObjectProperty(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLObjectProperty(String localName)
  {
    SWRLAPITestUtil.declareOWLObjectProperty(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLDataProperty(String localName)
  {
    SWRLAPITestUtil.declareOWLDataProperty(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLDatatype(String prefixedName)
  {
    SWRLAPITestUtil.declareOWLDatatype(this.manager, this.ontology, prefixedName);
  }

  protected void declareOWLClasses(Set<String> localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLClass(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLNamedIndividuals(Set<String> localNames)
  {
    for (String localName : localNames)
      SWRLAPITestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.namespace + localName);
  }

  protected void declareOWLClassAssertion(String classLocalName, String individualLocalName)
  {
    SWRLAPITestUtil.declareOWLClassAssertionAxiom(this.manager, this.ontology, this.namespace + classLocalName,
        this.namespace + individualLocalName);
  }

  protected void declareOWLObjectPropertyAssertion(String subjectLocalName, String propertyLocalName,
      String objectLocalName)
  {
    SWRLAPITestUtil.declareOWLObjectPropertyAssertionAxiom(this.manager, this.ontology, this.namespace
        + subjectLocalName, this.namespace + propertyLocalName, this.namespace + objectLocalName);
  }

  protected void declareOWLDataPropertyAssertion(String subjectLocalName, String propertyLocalName, String value,
      String datatypePrefixedName)
  {
    SWRLAPITestUtil.declareOWLDataPropertyAssertionAxiom(this.manager, this.ontology,
        this.namespace + subjectLocalName, this.namespace + propertyLocalName, value, datatypePrefixedName,
        this.prefixManager);
  }

  protected void declareOWLSameAsAssertion(String individualLocalName1, String individualLocalName2)
  {
    SWRLAPITestUtil.declareOWLSameIndividualAxiom(this.manager, this.ontology, this.namespace + individualLocalName1,
        this.namespace + individualLocalName2);
  }

  protected void declareOWLDifferentFromAssertion(String individualLocalName1, String individualLocalName2)
  {
    SWRLAPITestUtil.declareOWLDifferentIndividualsAxiom(this.manager, this.ontology, this.namespace
        + individualLocalName1, this.namespace + individualLocalName2);
  }

  protected void declareOWLObjectPropertyDomainAxiom(String propertyLocalName, String classLocalName)
  {
    SWRLAPITestUtil.declareOWLObjectPropertyDomainAxiom(this.manager, this.ontology,
        this.namespace + propertyLocalName, this.namespace + classLocalName);
  }

  protected void declareOWLDataPropertyDomainAxiom(String propertyLocalName, String classLocalName)
  {
    SWRLAPITestUtil.declareOWLDataPropertyDomainAxiom(this.manager, this.ontology, this.namespace + propertyLocalName,
        this.namespace + classLocalName);
  }
}
