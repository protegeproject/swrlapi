package org.protege.swrlapi.converters;

import org.protege.owl.portability.axioms.OWLAnnotationAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAnnotationPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAnnotationPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAnnotationPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAsymmetricObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLClassAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLClassDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDatatypeDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDatatypeDefinitionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDifferentIndividualsAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointClassesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointDataPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointUnionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentClassesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentDataPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLFunctionalDataPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLFunctionalObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLHasKeyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLIndividualDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLInverseFunctionalObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLInverseObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLIrreflexiveObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLNegativeDataPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLNegativeObjectPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLReflexiveObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSameIndividualAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubAnnotationPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubClassOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubDataPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubObjectPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubPropertyChainOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSymmetricObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLTransitiveObjectPropertyAxiomAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.ext.SWRLAPIRule;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert OWL axioms to their native rule engine representation.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineOWLAxiomConverter extends TargetRuleEngineConverter {
	void convert(SWRLAPIRule axiom) throws TargetRuleEngineException;

	void convert(OWLClassDeclarationAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLIndividualDeclarationAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyDeclarationAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyDeclarationAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationPropertyDeclarationAxiomAdapter axiom) throws TargetRuleEngineException;
	
	void convert(OWLDatatypeDeclarationAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLClassAssertionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyAssertionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyAssertionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSameIndividualAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDifferentIndividualsAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointClassesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLEquivalentClassesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSubClassOfAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSubDataPropertyOfAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSubObjectPropertyOfAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLEquivalentDataPropertiesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLEquivalentObjectPropertiesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointDataPropertiesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointObjectPropertiesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyDomainAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyDomainAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyRangeAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDataPropertyRangeAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLFunctionalObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLFunctionalDataPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLInverseFunctionalObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLIrreflexiveObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLTransitiveObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSymmetricObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLAsymmetricObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLInverseObjectPropertiesAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLNegativeDataPropertyAssertionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLNegativeObjectPropertyAssertionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLReflexiveObjectPropertyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDisjointUnionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationAssertionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSubPropertyChainOfAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLHasKeyAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLDatatypeDefinitionAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationPropertyRangeAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLAnnotationPropertyDomainAxiomAdapter axiom) throws TargetRuleEngineException;

	void convert(OWLSubAnnotationPropertyOfAxiomAdapter axiom) throws TargetRuleEngineException;
}
