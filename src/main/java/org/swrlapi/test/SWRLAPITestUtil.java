package org.swrlapi.test;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.factory.SWRLAPIFactory;

public class SWRLAPITestUtil
{
  @NonNull private static final OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

  @NonNull public static OWLOntology createEmptyOWLOntology() throws OWLOntologyCreationException
  {
    OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
    return ontologyManager.createOntology();
  }

  @NonNull public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(@NonNull OWLOntology ontology)
  {
    return SWRLAPIFactory.createSWRLAPIOntology(ontology);
  }

  @NonNull public static IRI getIRI(@NonNull String str)
  {
    return IRI.create(str);
  }

  @NonNull public static OWLClass getOWLClass(@NonNull IRI iri)
  {
    return dataFactory.getOWLClass(iri);
  }

  @NonNull public static OWLNamedIndividual getOWLNamedIndividual(@NonNull IRI iri)
  {
    return dataFactory.getOWLNamedIndividual(iri);
  }

  @NonNull public static OWLObjectProperty getOWLObjectProperty(@NonNull IRI iri)
  {
    return dataFactory.getOWLObjectProperty(iri);
  }

  @NonNull public static OWLDataProperty getOWLDataProperty(@NonNull IRI iri)
  {
    return dataFactory.getOWLDataProperty(iri);
  }

  @NonNull public static OWLDatatype getOWLDatatype(@NonNull IRI iri)
  {
    return dataFactory.getOWLDatatype(iri);
  }

  @NonNull public static OWLAnnotationProperty getOWLAnnotationProperty(@NonNull IRI iri)
  {
    return dataFactory.getOWLAnnotationProperty(iri);
  }

  @NonNull public static OWLAnnotationProperty getRDFSComment()
  {
    return dataFactory.getRDFSComment();
  }

  @NonNull public static OWLAnnotationProperty getRDFSLabel()
  {
    return dataFactory.getRDFSLabel();
  }

  @NonNull public static OWLDatatype getTopDatatype()
  {
    return dataFactory.getTopDatatype();
  }

  @NonNull public static OWLClass getOWLThing()
  {
    return dataFactory.getOWLThing();
  }

  @NonNull public static OWLDatatype getIntegerDatatype()
  {
    return dataFactory.getIntegerOWLDatatype();
  }

  @NonNull public static OWLDatatype getDoubleDatatype()
  {
    return dataFactory.getDoubleOWLDatatype();
  }

  @NonNull public static OWLDatatype getBooleanDatatype()
  {
    return dataFactory.getBooleanOWLDatatype();
  }

  @NonNull public static OWLClass getOWLNothing()
  {
    return dataFactory.getOWLNothing();
  }

  @NonNull public static OWLDeclarationAxiom getOWLDeclarationAxiom(@NonNull OWLEntity entity)
  {
    return dataFactory.getOWLDeclarationAxiom(entity);
  }

  @NonNull public static OWLClassAssertionAxiom getOWLClassAssertionAxiom(@NonNull OWLClass cls,
    @NonNull OWLIndividual individual)
  {
    return dataFactory.getOWLClassAssertionAxiom(cls, individual);
  }

  public static void declareOWLClass(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String name)
  {
    OWLClass cls = getOWLClass(getIRI(name));
    OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(cls);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLNamedIndividual(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String name)
  {
    OWLNamedIndividual i = getOWLNamedIndividual(getIRI(name));
    OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(i);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLObjectProperty(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String name)
  {
    OWLObjectProperty p = getOWLObjectProperty(getIRI(name));
    OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(p);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLDataProperty(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String name)
  {
    OWLDataProperty p = getOWLDataProperty(getIRI(name));
    OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(p);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLDatatype(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String datatypePrefixedName)
  {
    OWLDatatype d = getOWLDatatype(getIRI(datatypePrefixedName));
    OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(d);

    manager.addAxiom(ontology, axiom);

    // List<OWLOntologyChange> changes = new ArrayList<>();
    // changes.add(new AddAxiom(ontology, axiom));
    // manager.applyChanges(changes);
  }

  public static void declareOWLClassAssertionAxiom(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String className, @NonNull String individualName)
  {
    OWLClass cls = getOWLClass(getIRI(className));
    OWLNamedIndividual i = getOWLNamedIndividual(getIRI(individualName));
    OWLClassAssertionAxiom axiom = getOWLClassAssertionAxiom(cls, i);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLObjectPropertyAssertionAxiom(@NonNull OWLOntologyManager manager,
    @NonNull OWLOntology ontology, @NonNull String subjectName, @NonNull String propertyName,
    @NonNull String objectName)
  {
    OWLObjectProperty property = getOWLObjectProperty(getIRI(propertyName));
    OWLNamedIndividual subject = getOWLNamedIndividual(getIRI(subjectName));
    OWLNamedIndividual object = getOWLNamedIndividual(getIRI(objectName));
    OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(property, subject, object);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLDataPropertyAssertionAxiom(@NonNull OWLOntologyManager manager,
    @NonNull OWLOntology ontology, @NonNull String subjectName, @NonNull String propertyName, @NonNull String value,
    @NonNull String datatypePrefixedName, @NonNull PrefixManager prefixManager)
  {
    OWLDataProperty property = getOWLDataProperty(getIRI(propertyName));
    OWLNamedIndividual subject = getOWLNamedIndividual(getIRI(subjectName));
    OWLDatatype datatype = dataFactory.getOWLDatatype(datatypePrefixedName, prefixManager);
    OWLLiteral literal = dataFactory.getOWLLiteral(value, datatype);

    OWLDataPropertyAssertionAxiom axiom = dataFactory.getOWLDataPropertyAssertionAxiom(property, subject, literal);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLSameIndividualAxiom(@NonNull OWLOntologyManager manager, @NonNull OWLOntology ontology,
    @NonNull String individual1Name, @NonNull String individual2Name)
  {
    OWLNamedIndividual individual1 = getOWLNamedIndividual(getIRI(individual1Name));
    OWLNamedIndividual individual2 = getOWLNamedIndividual(getIRI(individual2Name));
    OWLSameIndividualAxiom axiom = dataFactory.getOWLSameIndividualAxiom(individual1, individual2);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLDifferentIndividualsAxiom(@NonNull OWLOntologyManager manager,
    @NonNull OWLOntology ontology, @NonNull String individual1Name, @NonNull String individual2Name)
  {
    OWLNamedIndividual individual1 = getOWLNamedIndividual(getIRI(individual1Name));
    OWLNamedIndividual individual2 = getOWLNamedIndividual(getIRI(individual2Name));
    OWLDifferentIndividualsAxiom axiom = dataFactory.getOWLDifferentIndividualsAxiom(individual1, individual2);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLObjectPropertyDomainAxiom(@NonNull OWLOntologyManager manager,
    @NonNull OWLOntology ontology, @NonNull String propertyName, @NonNull String className)
  {
    OWLObjectProperty property = getOWLObjectProperty(getIRI(propertyName));
    OWLClass cls = getOWLClass(getIRI(className));
    OWLObjectPropertyDomainAxiom axiom = dataFactory.getOWLObjectPropertyDomainAxiom(property, cls);

    manager.addAxiom(ontology, axiom);
  }

  public static void declareOWLDataPropertyDomainAxiom(@NonNull OWLOntologyManager manager,
    @NonNull OWLOntology ontology, @NonNull String propertyName, @NonNull String className)
  {
    OWLDataProperty property = getOWLDataProperty(getIRI(propertyName));
    OWLClass cls = getOWLClass(getIRI(className));
    OWLDataPropertyDomainAxiom axiom = dataFactory.getOWLDataPropertyDomainAxiom(property, cls);

    manager.addAxiom(ontology, axiom);
  }
}
