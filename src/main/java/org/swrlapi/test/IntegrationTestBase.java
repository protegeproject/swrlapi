package org.swrlapi.test;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.Set;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

public class IntegrationTestBase
{
	final protected double DELTA = 1e-6;

  protected static final OWLDatatype RDFS_LITERAL = Datatype(IRI(Namespaces.RDFS + "Literal"));
  protected static final OWLDatatype XSD_STRING = Datatype(IRI(Namespaces.XSD + "string"));
  protected static final OWLDatatype XSD_BOOLEAN = Datatype(IRI(Namespaces.XSD + "boolean"));
  protected static final OWLDatatype XSD_BYTE = Datatype(IRI(Namespaces.XSD + "byte"));
  protected static final OWLDatatype XSD_SHORT = Datatype(IRI(Namespaces.XSD + "short"));
  protected static final OWLDatatype XSD_INT = Datatype(IRI(Namespaces.XSD + "int"));
  protected static final OWLDatatype XSD_LONG = Datatype(IRI(Namespaces.XSD + "long"));
  protected static final OWLDatatype XSD_FLOAT = Datatype(IRI(Namespaces.XSD + "float"));
  protected static final OWLDatatype XSD_DOUBLE = Datatype(IRI(Namespaces.XSD + "double"));
  protected static final OWLDatatype XSD_DATE = Datatype(IRI(Namespaces.XSD + "date"));
  protected static final OWLDatatype XSD_DATETIME = Datatype(IRI(Namespaces.XSD + "dateTime"));
  protected static final OWLDatatype XSD_TIME = Datatype(IRI(Namespaces.XSD + "time"));
  protected static final OWLDatatype XSD_DURATION = Datatype(IRI(Namespaces.XSD + "duration"));
  protected static final OWLDatatype XSD_ANY_URI = Datatype(IRI(Namespaces.XSD + "anyURI"));

	private String prefix;
	private OWLOntology ontology;
	private OWLOntologyManager manager;
	private SQWRLQueryEngine sqwrlQueryEngine;
	private DefaultPrefixManager prefixManager;
	private SWRLAPIOWLOntology swrlapiOWLOntology;

	// TODO This approach does not allow tests to be run in parallel. Use createSWRLRuleEngine and
	// createSQWRLQueryEngine approach below and delete these calls. Kill TestUtil then also.
	protected void createOWLOntology() throws OWLOntologyCreationException
	{
		this.prefix = ":";
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = this.manager.createOntology();
		this.prefixManager = new DefaultPrefixManager();
		this.prefixManager.setDefaultPrefix(this.prefix);
		this.swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(this.ontology, this.prefixManager);
	}

	protected void createOWLOntologyAndSQWRLQueryEngine() throws OWLOntologyCreationException
	{
		this.prefix = ":";
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = this.manager.createOntology();
		this.prefixManager = new DefaultPrefixManager();
		this.prefixManager.setDefaultPrefix(this.prefix);
		this.swrlapiOWLOntology = SWRLAPIFactory.createSWRLAPIOntology(this.ontology, this.prefixManager);
		this.sqwrlQueryEngine = SWRLAPIFactory.createSQWRLQueryEngine(this.ontology);
	}

	protected SWRLRuleEngine createSWRLRuleEngine() throws OWLOntologyCreationException
	{
		OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
		return SWRLAPIFactory.createSWRLRuleEngine(ontology);
	}

  protected SQWRLQueryEngine createSQWRLQueryEngine() throws OWLOntologyCreationException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    return SWRLAPIFactory.createSQWRLQueryEngine(ontology);
  }

  protected void addOWLAxioms(OWLOntology ontology, OWLAxiom... axioms)
	{
		OWLOntologyManager ontologyManager = ontology.getOWLOntologyManager();

		for (OWLAxiom axiom : axioms)
			ontologyManager.addAxiom(ontology, axiom);
	}

	protected void createSQWRLQuery(@NonNull String queryName, @NonNull String query) throws SQWRLException,
			SWRLParseException
	{
		this.swrlapiOWLOntology.createSQWRLQuery(queryName, query);
	}

	@NonNull
	protected SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule) throws SWRLParseException
	{
		return this.swrlapiOWLOntology.createSWRLRule(ruleName, rule);
	}

	@NonNull
	protected SQWRLResult executeSQWRLQuery(@NonNull String queryName) throws SQWRLException
	{
		return this.sqwrlQueryEngine.runSQWRLQuery(queryName);
	}

	@NonNull
	protected SQWRLResult executeSQWRLQuery(@NonNull String queryName, @NonNull String query) throws SQWRLException,
			SWRLParseException
	{
		createSQWRLQuery(queryName, query);

		return executeSQWRLQuery(queryName);
	}

	protected void declareOWLClass(@NonNull String localName)
	{
		TestUtil.declareOWLClass(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLClasses(@NonNull String... localNames)
	{
		for (String localName : localNames)
			TestUtil.declareOWLClass(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLNamedIndividual(@NonNull String localName)
	{
		TestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLNamedIndividuals(@NonNull String... localNames)
	{
		for (String localName : localNames)
			TestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLObjectProperties(@NonNull String... localNames)
	{
		for (String localName : localNames)
			TestUtil.declareOWLObjectProperty(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLObjectProperty(@NonNull String localName)
	{
		TestUtil.declareOWLObjectProperty(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLDataProperty(@NonNull String localName)
	{
		TestUtil.declareOWLDataProperty(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLDatatype(@NonNull String prefixedName)
	{
		TestUtil.declareOWLDatatype(this.manager, this.ontology, prefixedName);
	}

	protected void declareOWLClasses(@NonNull Set<String> localNames)
	{
		for (String localName : localNames)
			TestUtil.declareOWLClass(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLNamedIndividuals(@NonNull Set<String> localNames)
	{
		for (String localName : localNames)
			TestUtil.declareOWLNamedIndividual(this.manager, this.ontology, this.prefix + localName);
	}

	protected void declareOWLClassAssertion(@NonNull String classLocalName, @NonNull String individualLocalName)
	{
		TestUtil.declareOWLClassAssertionAxiom(this.manager, this.ontology, this.prefix + classLocalName,
      this.prefix + individualLocalName);
	}

	protected void declareOWLObjectPropertyAssertion(@NonNull String subjectLocalName, @NonNull String propertyLocalName,
			@NonNull String objectLocalName)
	{
		TestUtil.declareOWLObjectPropertyAssertionAxiom(this.manager, this.ontology, this.prefix + subjectLocalName,
      this.prefix + propertyLocalName, this.prefix + objectLocalName);
	}

	protected void declareOWLDataPropertyAssertion(@NonNull String subjectLocalName, @NonNull String propertyLocalName,
			@NonNull String value, @NonNull String datatypePrefixedName)
	{
		TestUtil.declareOWLDataPropertyAssertionAxiom(this.manager, this.ontology, this.prefix + subjectLocalName,
      this.prefix + propertyLocalName, value, datatypePrefixedName, this.prefixManager);
	}

	protected void declareOWLSameAsAssertion(@NonNull String individualLocalName1, @NonNull String individualLocalName2)
	{
		TestUtil.declareOWLSameIndividualAxiom(this.manager, this.ontology, this.prefix + individualLocalName1,
      this.prefix + individualLocalName2);
	}

	protected void declareOWLDifferentFromAssertion(@NonNull String individualLocalName1,
			@NonNull String individualLocalName2)
	{
		TestUtil.declareOWLDifferentIndividualsAxiom(this.manager, this.ontology, this.prefix + individualLocalName1,
      this.prefix + individualLocalName2);
	}

	protected void declareOWLObjectPropertyDomainAxiom(@NonNull String propertyLocalName, @NonNull String classLocalName)
	{
		TestUtil.declareOWLObjectPropertyDomainAxiom(this.manager, this.ontology, this.prefix + propertyLocalName,
      this.prefix + classLocalName);
	}

	protected void declareOWLDataPropertyDomainAxiom(@NonNull String propertyLocalName, @NonNull String classLocalName)
	{
		TestUtil.declareOWLDataPropertyDomainAxiom(this.manager, this.ontology, this.prefix + propertyLocalName,
      this.prefix + classLocalName);
	}
}
