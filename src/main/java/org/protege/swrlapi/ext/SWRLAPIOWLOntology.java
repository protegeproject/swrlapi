package org.protege.swrlapi.ext;

import java.util.Set;

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
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * Extends the OWLAPI's OWLOntology class with additional methods used by the SWRLAPI.
 */
public interface SWRLAPIOWLOntology extends OWLOntology
{
	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	Set<SWRLAPIRule> getSWRLRules();

	SWRLAPIRule createSWRLRule(String ruleName, String ruleText);

	void putOWLAxiom(OWLAxiom axiom);

	/**
	 * Take a prefix and generate a unique IRI from it in the default namespace of the ontology, e.g., a local name prefix
	 * of "MM_CLASS_" supplied to this method where the default namespace is
	 * "http://bmir.stanford.edu/protege/ontologies/Map" will generate a IRI something like
	 * "http://bmir.stanford.edu/protege/ontologies/Map#MM_CLASS_34".
	 */
	IRI generateOWLEntityIRI(String localNamePrefix);

	String iri2PrefixedName(IRI iri);

	Set<OWLDeclarationAxiom> getOWLClassDeclarationAxioms();

	Set<OWLDeclarationAxiom> getOWLIndividualDeclarationAxioms();

	Set<OWLDeclarationAxiom> getOWLObjectPropertyDeclarationAxioms();

	Set<OWLDeclarationAxiom> getOWLDataPropertyDeclarationAxioms();

	Set<OWLDeclarationAxiom> getOWLAnnotationPropertyDeclarationAxioms();

	Set<OWLDeclarationAxiom> getOWLDatatypeDeclarationAxioms();

	Set<OWLSameIndividualAxiom> getOWLSameIndividualAxioms();

	Set<OWLDifferentIndividualsAxiom> getOWLDifferentIndividualsAxioms();

	Set<OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyOfAxioms();

	Set<OWLSubDataPropertyOfAxiom> getOWLSubDataPropertyOfAxioms();

	Set<OWLEquivalentClassesAxiom> getOWLEquivalentClassesAxioms();

	Set<OWLClassAssertionAxiom> getOWLClassAssertionAxioms();

	Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms();

	Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms();

	Set<OWLSubClassOfAxiom> getOWLSubClassOfAxioms();

	Set<OWLDisjointClassesAxiom> getOWLDisjointClassesAxioms();

	Set<OWLEquivalentDataPropertiesAxiom> getOWLEquivalentDataPropertiesAxioms();

	Set<OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentObjectPropertiesAxioms();

	Set<OWLDisjointDataPropertiesAxiom> getOWLDisjointDataPropertiesAxioms();

	Set<OWLDisjointObjectPropertiesAxiom> getOWLDisjointObjectPropertiesAxioms();

	Set<OWLObjectPropertyDomainAxiom> getOWLObjectPropertyDomainAxioms();

	Set<OWLDataPropertyDomainAxiom> getOWLDataPropertyDomainAxioms();

	Set<OWLObjectPropertyRangeAxiom> getOWLObjectPropertyRangeAxioms();

	Set<OWLDataPropertyRangeAxiom> getOWLDataPropertyRangeAxioms();

	Set<OWLFunctionalObjectPropertyAxiom> getOWLFunctionalObjectPropertyAxioms();

	Set<OWLFunctionalDataPropertyAxiom> getOWLFunctionalDataPropertyAxioms();

	Set<OWLIrreflexiveObjectPropertyAxiom> getOWLIrreflexiveObjectPropertyAxioms();

	Set<OWLInverseFunctionalObjectPropertyAxiom> getOWLInverseFunctionalObjectPropertyAxioms();

	Set<OWLTransitiveObjectPropertyAxiom> getOWLTransitiveObjectPropertyAxioms();

	Set<OWLSymmetricObjectPropertyAxiom> getOWLSymmetricObjectPropertyAxioms();

	Set<OWLAsymmetricObjectPropertyAxiom> getOWLAsymmetricObjectPropertyAxioms();

	Set<OWLInverseObjectPropertiesAxiom> getOWLInverseObjectPropertiesAxioms();

	Set<OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms();

	Set<OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms();

	Set<OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms();

	Set<OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms();

	Set<OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms();

	Set<OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms();

	Set<OWLHasKeyAxiom> getOWLHasKeyAxioms();

	Set<OWLDatatypeDefinitionAxiom> getOWLDatatypeDefinitionAxioms();

	Set<OWLAnnotationPropertyRangeAxiom> getOWLAnnotationPropertyRangeAxioms();

	Set<OWLAnnotationPropertyDomainAxiom> getOWLAnnotationPropertyDomainAxioms();

	Set<OWLSubAnnotationPropertyOfAxiom> getOWLSubAnnotationPropertyOfAxioms();

	// TODO We really do not want all the following methods here. They are convenience methods only and are used only by a
	// few built-in libraries..
	boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI);

	Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	boolean isOWLClass(IRI classIRI);

	boolean isOWLNamedIndividual(IRI individualIRI);

	boolean isOWLObjectProperty(IRI propertyIRI);

	boolean isOWLDataProperty(IRI propertyIRI);
}
