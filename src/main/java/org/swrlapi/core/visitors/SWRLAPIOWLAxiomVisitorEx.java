package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.core.SWRLAPIRule;

/**
 * SWRLAPI OWL axiom visitor customized to deal with {@link org.swrlapi.core.SWRLAPIRule}s instead of OWLAPI-based
 * {@link org.swrlapi.core.SWRLAPIRule}s.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.semanticweb.owlapi.model.OWLAxiomVisitorEx
 */
public interface SWRLAPIOWLAxiomVisitorEx<T>
{
  T visit(SWRLAPIRule swrlapiRule);

  T visit(OWLDeclarationAxiom axiom);

  T visit(OWLSubClassOfAxiom owlSubClassOfAxiom);

  T visit(OWLNegativeObjectPropertyAssertionAxiom owlNegativeObjectPropertyAssertionAxiom);

  T visit(OWLAsymmetricObjectPropertyAxiom owlAsymmetricObjectPropertyAxiom);

  T visit(OWLReflexiveObjectPropertyAxiom owlReflexiveObjectPropertyAxiom);

  T visit(OWLDisjointClassesAxiom owlDisjointClassesAxiom);

  T visit(OWLDataPropertyDomainAxiom owlDataPropertyDomainAxiom);

  T visit(OWLObjectPropertyDomainAxiom owlObjectPropertyDomainAxiom);

  T visit(OWLEquivalentObjectPropertiesAxiom owlEquivalentObjectPropertiesAxiom);

  T visit(OWLNegativeDataPropertyAssertionAxiom owlNegativeDataPropertyAssertionAxiom);

  T visit(OWLDifferentIndividualsAxiom owlDifferentIndividualsAxiom);

  T visit(OWLDisjointDataPropertiesAxiom owlDisjointDataPropertiesAxiom);

  T visit(OWLDisjointObjectPropertiesAxiom owlDisjointObjectPropertiesAxiom);

  T visit(OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom);

  T visit(OWLObjectPropertyAssertionAxiom owlObjectPropertyAssertionAxiom);

  T visit(OWLFunctionalObjectPropertyAxiom owlFunctionalObjectPropertyAxiom);

  T visit(OWLSubObjectPropertyOfAxiom owlSubObjectPropertyOfAxiom);

  T visit(OWLDisjointUnionAxiom owlDisjointUnionAxiom);

  T visit(OWLSymmetricObjectPropertyAxiom owlSymmetricObjectPropertyAxiom);

  T visit(OWLDataPropertyRangeAxiom owlDataPropertyRangeAxiom);

  T visit(OWLFunctionalDataPropertyAxiom owlFunctionalDataPropertyAxiom);

  T visit(OWLEquivalentDataPropertiesAxiom owlEquivalentDataPropertiesAxiom);

  T visit(OWLClassAssertionAxiom owlClassAssertionAxiom);

  T visit(OWLEquivalentClassesAxiom owlEquivalentClassesAxiom);

  T visit(OWLDataPropertyAssertionAxiom owlDataPropertyAssertionAxiom);

  T visit(OWLTransitiveObjectPropertyAxiom owlTransitiveObjectPropertyAxiom);

  T visit(OWLIrreflexiveObjectPropertyAxiom owlIrreflexiveObjectPropertyAxiom);

  T visit(OWLSubDataPropertyOfAxiom owlSubDataPropertyOfAxiom);

  T visit(OWLInverseFunctionalObjectPropertyAxiom owlInverseFunctionalObjectPropertyAxiom);

  T visit(OWLSameIndividualAxiom owlSameIndividualAxiom);

  T visit(OWLSubPropertyChainOfAxiom owlSubPropertyChainOfAxiom);

  T visit(OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom);

  T visit(OWLHasKeyAxiom owlHasKeyAxiom);

  T visit(OWLDatatypeDefinitionAxiom owlDatatypeDefinitionAxiom);
}
