package org.swrlapi.test;

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
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;

public class SWRLAPITestUtil
{
	private static final OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

	public static OWLOntologyManager createOWLOntologyManager()
	{
		return SWRLAPIFactory.createOWLOntologyManager();
	}

	public static OWLOntology createOWLOntology() throws OWLOntologyCreationException
	{
		OWLOntologyManager ontologyManager = createOWLOntologyManager();
		return ontologyManager.createOntology();
	}

	public static IRI getIRI(String iri)
	{
		return IRI.create(iri);
	}

	public static DefaultPrefixManager createDefaultPrefixManager(OWLOntology ontology)
	{
		return SWRLAPIFactory.createPrefixManager(ontology);
	}

	public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(OWLOntology ontology, DefaultPrefixManager prefixManager)
	{
		return SWRLAPIFactory.createOntology(ontology, prefixManager);
	}

	public static OWLClass getOWLClass(IRI iri)
	{
		return dataFactory.getOWLClass(iri);
	}

	public static OWLNamedIndividual getOWLNamedIndividual(IRI iri)
	{
		return dataFactory.getOWLNamedIndividual(iri);
	}

	public static OWLObjectProperty getOWLObjectProperty(IRI iri)
	{
		return dataFactory.getOWLObjectProperty(iri);
	}

	public static OWLDataProperty getOWLDataProperty(IRI iri)
	{
		return dataFactory.getOWLDataProperty(iri);
	}

	public static OWLDatatype getOWLDatatype(IRI iri)
	{
		return dataFactory.getOWLDatatype(iri);
	}

	public static OWLAnnotationProperty getOWLAnnotationProperty(IRI iri)
	{
		return dataFactory.getOWLAnnotationProperty(iri);
	}

	public static OWLAnnotationProperty getRDFSComment()
	{
		return dataFactory.getRDFSComment();
	}

	public static OWLAnnotationProperty getRDFSLabel()
	{
		return dataFactory.getRDFSLabel();
	}

	public static OWLDatatype getTopDatatype()
	{
		return dataFactory.getTopDatatype();
	}

	public static OWLClass getOWLThing()
	{
		return dataFactory.getOWLThing();
	}

	public static OWLDatatype getIntegerDatatype()
	{
		return dataFactory.getIntegerOWLDatatype();
	}

	public static OWLDatatype getDoubleDatatype()
	{
		return dataFactory.getDoubleOWLDatatype();
	}

	public static OWLDatatype getBooleanDatatype()
	{
		return dataFactory.getBooleanOWLDatatype();
	}

	public static OWLClass getOWLNothing()
	{
		return dataFactory.getOWLNothing();
	}

	public static OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity entity)
	{
		return dataFactory.getOWLDeclarationAxiom(entity);
	}

	public static OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLClass cls, OWLIndividual individual)
	{
		return dataFactory.getOWLClassAssertionAxiom(cls, individual);
	}

	public static void declareOWLClass(OWLOntologyManager manager, OWLOntology ontology, String iri)
	{
		OWLClass cls = getOWLClass(getIRI(iri));
		OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(cls);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLNamedIndividual(OWLOntologyManager manager, OWLOntology ontology, String iri)
	{
		OWLNamedIndividual i = getOWLNamedIndividual(getIRI(iri));
		OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(i);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLObjectProperty(OWLOntologyManager manager, OWLOntology ontology, String iri)
	{
		OWLObjectProperty p = getOWLObjectProperty(getIRI(iri));
		OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(p);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLDataProperty(OWLOntologyManager manager, OWLOntology ontology, String iri)
	{
		OWLDataProperty p = getOWLDataProperty(getIRI(iri));
		OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(p);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLDatatype(OWLOntologyManager manager, OWLOntology ontology, String datatypePrefixedName)
	{
		OWLDatatype d = getOWLDatatype(getIRI(datatypePrefixedName));
		OWLDeclarationAxiom axiom = getOWLDeclarationAxiom(d);

		manager.addAxiom(ontology, axiom);

		// List<OWLOntologyChange> changes = new ArrayList<>();
		// changes.add(new AddAxiom(ontology, axiom));
		// manager.applyChanges(changes);
	}

	public static void declareOWLClassAssertionAxiom(OWLOntologyManager manager, OWLOntology ontology, String classIRI,
			String individualIRI)
	{
		OWLClass cls = getOWLClass(getIRI(classIRI));
		OWLNamedIndividual i = getOWLNamedIndividual(getIRI(individualIRI));
		OWLClassAssertionAxiom axiom = getOWLClassAssertionAxiom(cls, i);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLObjectPropertyAssertionAxiom(OWLOntologyManager manager, OWLOntology ontology,
			String subjectIRI, String propertyIRI, String objectIRI)
	{
		OWLObjectProperty property = getOWLObjectProperty(getIRI(propertyIRI));
		OWLNamedIndividual subject = getOWLNamedIndividual(getIRI(subjectIRI));
		OWLNamedIndividual object = getOWLNamedIndividual(getIRI(objectIRI));
		OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(property, subject, object);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLDataPropertyAssertionAxiom(OWLOntologyManager manager, OWLOntology ontology,
			String subjectIRI, String propertyIRI, String value, String datatypePrefixedName, PrefixManager prefixManager)
	{
		OWLDataProperty property = getOWLDataProperty(getIRI(propertyIRI));
		OWLNamedIndividual subject = getOWLNamedIndividual(getIRI(subjectIRI));
		OWLDatatype datatype = dataFactory.getOWLDatatype(datatypePrefixedName, prefixManager);
		OWLLiteral literal = dataFactory.getOWLLiteral(value, datatype);

		OWLDataPropertyAssertionAxiom axiom = dataFactory.getOWLDataPropertyAssertionAxiom(property, subject, literal);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLSameIndividualAxiom(OWLOntologyManager manager, OWLOntology ontology,
			String individual1IRI, String individual2IRI)
	{
		OWLNamedIndividual individual1 = getOWLNamedIndividual(getIRI(individual1IRI));
		OWLNamedIndividual individual2 = getOWLNamedIndividual(getIRI(individual2IRI));
		OWLSameIndividualAxiom axiom = dataFactory.getOWLSameIndividualAxiom(individual1, individual2);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLDifferentIndividualsAxiom(OWLOntologyManager manager, OWLOntology ontology,
			String individual1IRI, String individual2IRI)
	{
		OWLNamedIndividual individual1 = getOWLNamedIndividual(getIRI(individual1IRI));
		OWLNamedIndividual individual2 = getOWLNamedIndividual(getIRI(individual2IRI));
		OWLDifferentIndividualsAxiom axiom = dataFactory.getOWLDifferentIndividualsAxiom(individual1, individual2);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLObjectPropertyDomainAxiom(OWLOntologyManager manager, OWLOntology ontology,
			String propertyIRI, String classIRI)
	{
		OWLObjectProperty property = getOWLObjectProperty(getIRI(propertyIRI));
		OWLClass cls = getOWLClass(getIRI(classIRI));
		OWLObjectPropertyDomainAxiom axiom = dataFactory.getOWLObjectPropertyDomainAxiom(property, cls);

		manager.addAxiom(ontology, axiom);
	}

	public static void declareOWLDataPropertyDomainAxiom(OWLOntologyManager manager, OWLOntology ontology,
			String propertyIRI, String classIRI)
	{
		OWLDataProperty property = getOWLDataProperty(getIRI(propertyIRI));
		OWLClass cls = getOWLClass(getIRI(classIRI));
		OWLDataPropertyDomainAxiom axiom = dataFactory.getOWLDataPropertyDomainAxiom(property, cls);

		manager.addAxiom(ontology, axiom);
	}

}
