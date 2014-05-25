package org.swrlapi.converters;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
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
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert OWL axioms to a native
 * rule engine representation. SWRL rules are also a type of OWL axiom so are also converted here.
 * 
 * @see OWLAxiom, SWRLAPIRule
 */
public interface TargetRuleEngineOWLAxiomConverter extends TargetRuleEngineConverter
{
	void convert(SWRLAPIRule axiom) throws TargetRuleEngineException;

	void convert(OWLDeclarationAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLClassAssertionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyAssertionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyAssertionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSameIndividualAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDifferentIndividualsAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointClassesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLEquivalentClassesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSubClassOfAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSubDataPropertyOfAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSubObjectPropertyOfAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLEquivalentDataPropertiesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLEquivalentObjectPropertiesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointDataPropertiesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointObjectPropertiesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyDomainAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyDomainAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyRangeAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyRangeAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLFunctionalObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLFunctionalDataPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLInverseFunctionalObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLIrreflexiveObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLTransitiveObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSymmetricObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLAsymmetricObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLInverseObjectPropertiesAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLNegativeDataPropertyAssertionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLNegativeObjectPropertyAssertionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLReflexiveObjectPropertyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointUnionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationAssertionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSubPropertyChainOfAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLHasKeyAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLDatatypeDefinitionAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationPropertyRangeAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationPropertyDomainAxiom axiom) throws TargetRuleEngineException;

	void convert(OWLSubAnnotationPropertyOfAxiom axiom) throws TargetRuleEngineException;
}
