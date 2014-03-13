package org.protege.swrlapi.ext.impl;

import java.util.HashSet;
import java.util.Set;

import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.semanticweb.owlapi.model.AxiomType;
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
		return getAxioms(AxiomType.SAME_INDIVIDUAL, true);
	}

	@Override
	public Set<OWLDifferentIndividualsAxiom> getOWLDifferentIndividualsAxioms()
	{
		return getAxioms(AxiomType.DIFFERENT_INDIVIDUALS, true);
	}

	@Override
	public Set<OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyOfAxioms()
	{
		return getAxioms(AxiomType.SUB_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLSubDataPropertyOfAxiom> getOWLSubDataPropertyOfAxioms()
	{
		return getAxioms(AxiomType.SUB_DATA_PROPERTY, true);
	}

	@Override
	public Set<OWLEquivalentClassesAxiom> getOWLEquivalentClassesAxioms()
	{
		return getAxioms(AxiomType.EQUIVALENT_CLASSES, true);
	}

	@Override
	public Set<OWLClassAssertionAxiom> getOWLClassAssertionAxioms()
	{
		return getAxioms(AxiomType.CLASS_ASSERTION, true);
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms()
	{
		return getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, true);
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms()
	{
		return getAxioms(AxiomType.DATA_PROPERTY_ASSERTION, true);
	}

	@Override
	public Set<OWLSubClassOfAxiom> getOWLSubClassOfAxioms()
	{
		return getAxioms(AxiomType.SUBCLASS_OF, true);
	}

	@Override
	public Set<OWLDisjointClassesAxiom> getOWLDisjointClassesAxioms()
	{
		return getAxioms(AxiomType.DISJOINT_CLASSES, true);
	}

	@Override
	public Set<OWLEquivalentDataPropertiesAxiom> getOWLEquivalentDataPropertiesAxioms()
	{
		return getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES, true);
	}

	@Override
	public Set<OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentObjectPropertiesAxioms()
	{
		return getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES, true);
	}

	@Override
	public Set<OWLDisjointDataPropertiesAxiom> getOWLDisjointDataPropertiesAxioms()
	{
		return getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES, true);
	}

	@Override
	public Set<OWLDisjointObjectPropertiesAxiom> getOWLDisjointObjectPropertiesAxioms()
	{
		return getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES, true);
	}

	@Override
	public Set<OWLObjectPropertyDomainAxiom> getOWLObjectPropertyDomainAxioms()
	{
		return getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN, true);
	}

	@Override
	public Set<OWLDataPropertyDomainAxiom> getOWLDataPropertyDomainAxioms()
	{
		return getAxioms(AxiomType.DATA_PROPERTY_DOMAIN, true);
	}

	@Override
	public Set<OWLObjectPropertyRangeAxiom> getOWLObjectPropertyRangeAxioms()
	{
		return getAxioms(AxiomType.OBJECT_PROPERTY_RANGE, true);
	}

	@Override
	public Set<OWLDataPropertyRangeAxiom> getOWLDataPropertyRangeAxioms()
	{
		return getAxioms(AxiomType.DATA_PROPERTY_RANGE, true);
	}

	@Override
	public Set<OWLFunctionalObjectPropertyAxiom> getOWLFunctionalObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.FUNCTIONAL_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLFunctionalDataPropertyAxiom> getOWLFunctionalDataPropertyAxioms()
	{
		return getAxioms(AxiomType.FUNCTIONAL_DATA_PROPERTY, true);
	}

	@Override
	public Set<OWLIrreflexiveObjectPropertyAxiom> getOWLIrreflexiveObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLInverseFunctionalObjectPropertyAxiom> getOWLInverseFunctionalObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLTransitiveObjectPropertyAxiom> getOWLTransitiveObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLSymmetricObjectPropertyAxiom> getOWLSymmetricObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLAsymmetricObjectPropertyAxiom> getOWLAsymmetricObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.ASYMMETRIC_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLInverseObjectPropertiesAxiom> getOWLInverseObjectPropertiesAxioms()
	{
		return getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES, true);
	}

	@Override
	public Set<OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms()
	{
		return getAxioms(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION, true);
	}

	@Override
	public Set<OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms()
	{
		return getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION, true);
	}

	@Override
	public Set<OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms()
	{
		return getAxioms(AxiomType.REFLEXIVE_OBJECT_PROPERTY, true);
	}

	@Override
	public Set<OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms()
	{
		return getAxioms(AxiomType.DISJOINT_UNION, true);
	}

	@Override
	public Set<OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms()
	{
		return getAxioms(AxiomType.ANNOTATION_ASSERTION, true);
	}

	@Override
	public Set<OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms()
	{
		return getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF, true);
	}

	@Override
	public Set<OWLHasKeyAxiom> getOWLHasKeyAxioms()
	{
		return getAxioms(AxiomType.HAS_KEY, true);
	}

	@Override
	public Set<OWLDatatypeDefinitionAxiom> getOWLDatatypeDefinitionAxioms()
	{
		return getAxioms(AxiomType.DATATYPE_DEFINITION, true);
	}

	@Override
	public Set<OWLAnnotationPropertyRangeAxiom> getOWLAnnotationPropertyRangeAxioms()
	{
		return getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE, true);
	}

	@Override
	public Set<OWLAnnotationPropertyDomainAxiom> getOWLAnnotationPropertyDomainAxioms()
	{
		return getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN, true);
	}

	@Override
	public Set<OWLSubAnnotationPropertyOfAxiom> getOWLSubAnnotationPropertyOfAxioms()
	{
		return getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF, true);
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLClassDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlClassDeclarationAxioms = new HashSet<OWLDeclarationAxiom>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getAxioms(AxiomType.DECLARATION, true)) {
			if (owlDeclarationAxiom.getEntity().isOWLClass())
				owlClassDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlClassDeclarationAxioms;
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLIndividualDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlIndividualDeclarationAxioms = new HashSet<OWLDeclarationAxiom>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getAxioms(AxiomType.DECLARATION, true)) {
			if (owlDeclarationAxiom.getEntity().isOWLNamedIndividual())
				owlIndividualDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlIndividualDeclarationAxioms;
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLObjectPropertyDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlObjectPropertyDeclarationAxioms = new HashSet<OWLDeclarationAxiom>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getAxioms(AxiomType.DECLARATION, true)) {
			if (owlDeclarationAxiom.getEntity().isOWLObjectProperty())
				owlObjectPropertyDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlObjectPropertyDeclarationAxioms;
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLDataPropertyDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlDataPropertyDeclarationAxioms = new HashSet<OWLDeclarationAxiom>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getAxioms(AxiomType.DECLARATION, true)) {
			if (owlDeclarationAxiom.getEntity().isOWLDataProperty())
				owlDataPropertyDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlDataPropertyDeclarationAxioms;
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLAnnotationPropertyDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms = new HashSet<OWLDeclarationAxiom>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getAxioms(AxiomType.DECLARATION, true)) {
			if (owlDeclarationAxiom.getEntity().isOWLAnnotationProperty())
				owlAnnotationPropertyDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlAnnotationPropertyDeclarationAxioms;
	}

	@Override
	public Set<OWLDeclarationAxiom> getOWLDatatypeDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlDatatypeDeclarationAxioms = new HashSet<OWLDeclarationAxiom>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getAxioms(AxiomType.DECLARATION, true)) {
			if (owlDeclarationAxiom.getEntity().isOWLDatatype())
				owlDatatypeDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlDatatypeDeclarationAxioms;
	}
}
