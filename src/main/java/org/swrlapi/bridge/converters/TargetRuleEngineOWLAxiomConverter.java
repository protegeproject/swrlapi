package org.swrlapi.bridge.converters;

import checkers.nullness.quals.NonNull;
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
  void convert(@NonNull SWRLAPIRule axiom);

  void convert(@NonNull OWLDeclarationAxiom axiom);

  void convert(@NonNull OWLClassAssertionAxiom axiom);

  void convert(@NonNull OWLObjectPropertyAssertionAxiom axiom);

  void convert(@NonNull OWLDataPropertyAssertionAxiom axiom);

  void convert(@NonNull OWLSameIndividualAxiom axiom);

  void convert(@NonNull OWLDifferentIndividualsAxiom axiom);

  void convert(@NonNull OWLDisjointClassesAxiom axiom);

  void convert(@NonNull OWLEquivalentClassesAxiom axiom);

  void convert(@NonNull OWLSubClassOfAxiom axiom);

  void convert(@NonNull OWLSubDataPropertyOfAxiom axiom);

  void convert(@NonNull OWLSubObjectPropertyOfAxiom axiom);

  void convert(@NonNull OWLEquivalentDataPropertiesAxiom axiom);

  void convert(@NonNull OWLEquivalentObjectPropertiesAxiom axiom);

  void convert(@NonNull OWLDisjointDataPropertiesAxiom axiom);

  void convert(@NonNull OWLDisjointObjectPropertiesAxiom axiom);

  void convert(@NonNull OWLObjectPropertyDomainAxiom axiom);

  void convert(@NonNull OWLDataPropertyDomainAxiom axiom);

  void convert(@NonNull OWLObjectPropertyRangeAxiom axiom);

  void convert(@NonNull OWLDataPropertyRangeAxiom axiom);

  void convert(@NonNull OWLFunctionalObjectPropertyAxiom axiom);

  void convert(@NonNull OWLFunctionalDataPropertyAxiom axiom);

  void convert(@NonNull OWLInverseFunctionalObjectPropertyAxiom axiom);

  void convert(@NonNull OWLIrreflexiveObjectPropertyAxiom axiom);

  void convert(@NonNull OWLTransitiveObjectPropertyAxiom axiom);

  void convert(@NonNull OWLSymmetricObjectPropertyAxiom axiom);

  void convert(@NonNull OWLAsymmetricObjectPropertyAxiom axiom);

  void convert(@NonNull OWLInverseObjectPropertiesAxiom axiom);

  void convert(@NonNull OWLNegativeDataPropertyAssertionAxiom axiom);

  void convert(@NonNull OWLNegativeObjectPropertyAssertionAxiom axiom);

  void convert(@NonNull OWLReflexiveObjectPropertyAxiom axiom);

  void convert(@NonNull OWLDisjointUnionAxiom axiom);

  void convert(@NonNull OWLAnnotationAssertionAxiom axiom);

  void convert(@NonNull OWLSubPropertyChainOfAxiom axiom);

  void convert(@NonNull OWLHasKeyAxiom axiom);

  void convert(@NonNull OWLDatatypeDefinitionAxiom axiom);

  void convert(@NonNull OWLAnnotationPropertyRangeAxiom axiom);

  void convert(@NonNull OWLAnnotationPropertyDomainAxiom axiom);

  void convert(@NonNull OWLSubAnnotationPropertyOfAxiom axiom);
}
