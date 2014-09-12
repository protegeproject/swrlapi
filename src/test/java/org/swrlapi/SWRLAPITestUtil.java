package org.swrlapi;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;

import java.util.Set;

public class SWRLAPITestUtil
{
	private static final OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

	public static OWLOntologyManager createOWLOntologyManager()
	{
		return OWLManager.createOWLOntologyManager();
	}

	public static OWLOntology createOWLOntology() throws OWLOntologyCreationException
	{
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		return ontologyManager.createOntology();
	}

	public static IRI getIRI(String iri)
	{
		return IRI.create(iri);
	}

	public static DefaultPrefixManager createPrefixManager(OWLOntology ontology)
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
}
