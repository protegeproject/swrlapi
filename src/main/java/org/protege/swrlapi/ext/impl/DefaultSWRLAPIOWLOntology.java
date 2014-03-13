package org.protege.swrlapi.ext.impl;

import java.util.Set;

import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

public class DefaultSWRLAPIOWLOntology extends OWLOntologyImpl implements SWRLAPIOWLOntology
{
	private static final long serialVersionUID = 1L;

	public DefaultSWRLAPIOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID)
	{
		super(manager, ontologyID);
	}

	@Override
	public void startBulkConversion()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void completeBulkConversion()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean hasOntologyChanged()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void resetOntologyChanged()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<SWRLAPIRule> getSWRLRules()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public SWRLAPIRule createSWRLRule(String ruleName, String ruleText)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void putOWLAxiom(OWLAxiom axiom)
	{
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Take a prefix and generate a unique IRI from it in the default namespace of the ontology, e.g., a local name prefix
	 * of "MM_CLASS_" supplied to this method where the default namespace is
	 * "http://bmir.stanford.edu/protege/ontologies/Map" will generate a IRI something like
	 * "http://bmir.stanford.edu/protege/ontologies/Map#MM_CLASS_34".
	 */
	@Override
	public IRI generateOWLEntityIRI(String localNamePrefix)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String iri2PrefixedName(IRI iri)
	{
		throw new RuntimeException("Not implemented");
	}

	// TODO We really do not want all the following methods here. They are convenience methods only and are used only by a
	// few built-in libraries..
	@Override
	public boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	// Get OWL axioms of a particular type

	@Override
	public Set<OWLSameIndividualAxiom> getOWLSameIndividualAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDifferentIndividualsAxiom> getOWLDifferentIndividualsAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyOfAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLSubDataPropertyOfAxiom> getOWLSubDataPropertyOfAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLEquivalentClassesAxiom> getOWLEquivalentClassesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClassAssertionAxiom> getOWLClassAssertionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLSubClassOfAxiom> getOWLSubClassOfAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDisjointClassesAxiom> getOWLDisjointClassesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLEquivalentDataPropertiesAxiom> getOWLEquivalentDataPropertiesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentObjectPropertiesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDisjointDataPropertiesAxiom> getOWLDisjointDataPropertiesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDisjointObjectPropertiesAxiom> getOWLDisjointObjectPropertiesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyDomainAxiom> getOWLObjectPropertyDomainAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyDomainAxiom> getOWLDataPropertyDomainAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyRangeAxiom> getOWLObjectPropertyRangeAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyRangeAxiom> getOWLDataPropertyRangeAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLFunctionalObjectPropertyAxiom> getOWLFunctionalObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLFunctionalDataPropertyAxiom> getOWLFunctionalDataPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLIrreflexiveObjectPropertyAxiom> getOWLIrreflexiveObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLInverseFunctionalObjectPropertyAxiom> getOWLInverseFunctionalObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLTransitiveObjectPropertyAxiom> getOWLTransitiveObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLSymmetricObjectPropertyAxiom> getOWLSymmetricObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAsymmetricObjectPropertyAxiom> getOWLAsymmetricObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLInverseObjectPropertiesAxiom> getOWLInverseObjectPropertiesAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLHasKeyAxiom> getOWLHasKeyAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDatatypeDefinitionAxiom> getOWLDatatypeDefinitionAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAnnotationPropertyRangeAxiom> getOWLAnnotationPropertyRangeAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAnnotationPropertyDomainAxiom> getOWLAnnotationPropertyDomainAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLSubAnnotationPropertyOfAxiom> getOWLSubAnnotationPropertyOfAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLClassDeclarationAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLIndividualDeclarationAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLObjectPropertyDeclarationAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLDataPropertyDeclarationAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLAnnotationPropertyDeclarationAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLDatatypeDeclarationAxioms()
	{
		throw new RuntimeException("Not implemented");
	}

}
