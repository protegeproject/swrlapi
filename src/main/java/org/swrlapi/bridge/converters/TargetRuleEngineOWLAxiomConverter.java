package org.swrlapi.bridge.converters;

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

/**
 * This interface describes methods that can be implemented by a target rule engine to convert OWL axioms to a native
 * rule engine representation. SWRL rules are also a type of OWL axiom so are also converted here.
 * 
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.swrlapi.core.SWRLAPIRule
 */
public interface TargetRuleEngineOWLAxiomConverter extends TargetRuleEngineConverter
{
	void convert(SWRLAPIRule axiom);

	void convert(OWLDeclarationAxiom axiom);

	void convert(OWLClassAssertionAxiom axiom);

	void convert(OWLObjectPropertyAssertionAxiom axiom);

	void convert(OWLDataPropertyAssertionAxiom axiom);

	void convert(OWLSameIndividualAxiom axiom);

	void convert(OWLDifferentIndividualsAxiom axiom);

	void convert(OWLDisjointClassesAxiom axiom);

	void convert(OWLEquivalentClassesAxiom axiom);

	void convert(OWLSubClassOfAxiom axiom);

	void convert(OWLSubDataPropertyOfAxiom axiom);

	void convert(OWLSubObjectPropertyOfAxiom axiom);

	void convert(OWLEquivalentDataPropertiesAxiom axiom);

	void convert(OWLEquivalentObjectPropertiesAxiom axiom);

	void convert(OWLDisjointDataPropertiesAxiom axiom);

	void convert(OWLDisjointObjectPropertiesAxiom axiom);

	void convert(OWLObjectPropertyDomainAxiom axiom);

	void convert(OWLDataPropertyDomainAxiom axiom);

	void convert(OWLObjectPropertyRangeAxiom axiom);

	void convert(OWLDataPropertyRangeAxiom axiom);

	void convert(OWLFunctionalObjectPropertyAxiom axiom);

	void convert(OWLFunctionalDataPropertyAxiom axiom);

	void convert(OWLInverseFunctionalObjectPropertyAxiom axiom);

	void convert(OWLIrreflexiveObjectPropertyAxiom axiom);

	void convert(OWLTransitiveObjectPropertyAxiom axiom);

	void convert(OWLSymmetricObjectPropertyAxiom axiom);

	void convert(OWLAsymmetricObjectPropertyAxiom axiom);

	void convert(OWLInverseObjectPropertiesAxiom axiom);

	void convert(OWLNegativeDataPropertyAssertionAxiom axiom);

	void convert(OWLNegativeObjectPropertyAssertionAxiom axiom);

	void convert(OWLReflexiveObjectPropertyAxiom axiom);

	void convert(OWLDisjointUnionAxiom axiom);

	void convert(OWLAnnotationAssertionAxiom axiom);

	void convert(OWLSubPropertyChainOfAxiom axiom);

	void convert(OWLHasKeyAxiom axiom);

	void convert(OWLDatatypeDefinitionAxiom axiom);

	void convert(OWLAnnotationPropertyRangeAxiom axiom);

	void convert(OWLAnnotationPropertyDomainAxiom axiom);

	void convert(OWLSubAnnotationPropertyOfAxiom axiom);
}
