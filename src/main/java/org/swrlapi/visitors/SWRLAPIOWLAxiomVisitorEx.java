package org.swrlapi.visitors;

import checkers.nullness.quals.NonNull;
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
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
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
  @NonNull T visit(@NonNull SWRLAPIRule swrlapiRule);

  @NonNull T visit(@NonNull OWLDeclarationAxiom axiom);

  @NonNull T visit(@NonNull OWLSubClassOfAxiom owlSubClassOfAxiom);

  @NonNull T visit(@NonNull OWLNegativeObjectPropertyAssertionAxiom owlNegativeObjectPropertyAssertionAxiom);

  @NonNull T visit(@NonNull OWLAsymmetricObjectPropertyAxiom owlAsymmetricObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLReflexiveObjectPropertyAxiom owlReflexiveObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLDisjointClassesAxiom owlDisjointClassesAxiom);

  @NonNull T visit(@NonNull OWLDataPropertyDomainAxiom owlDataPropertyDomainAxiom);

  @NonNull T visit(@NonNull OWLObjectPropertyDomainAxiom owlObjectPropertyDomainAxiom);

  @NonNull T visit(@NonNull OWLEquivalentObjectPropertiesAxiom owlEquivalentObjectPropertiesAxiom);

  @NonNull T visit(@NonNull OWLNegativeDataPropertyAssertionAxiom owlNegativeDataPropertyAssertionAxiom);

  @NonNull T visit(@NonNull OWLDifferentIndividualsAxiom owlDifferentIndividualsAxiom);

  @NonNull T visit(@NonNull OWLDisjointDataPropertiesAxiom owlDisjointDataPropertiesAxiom);

  @NonNull T visit(@NonNull OWLDisjointObjectPropertiesAxiom owlDisjointObjectPropertiesAxiom);

  @NonNull T visit(@NonNull OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom);

  @NonNull T visit(@NonNull OWLObjectPropertyAssertionAxiom owlObjectPropertyAssertionAxiom);

  @NonNull T visit(@NonNull OWLFunctionalObjectPropertyAxiom owlFunctionalObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLSubObjectPropertyOfAxiom owlSubObjectPropertyOfAxiom);

  @NonNull T visit(@NonNull OWLDisjointUnionAxiom owlDisjointUnionAxiom);

  @NonNull T visit(@NonNull OWLSymmetricObjectPropertyAxiom owlSymmetricObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLDataPropertyRangeAxiom owlDataPropertyRangeAxiom);

  @NonNull T visit(@NonNull OWLFunctionalDataPropertyAxiom owlFunctionalDataPropertyAxiom);

  @NonNull T visit(@NonNull OWLEquivalentDataPropertiesAxiom owlEquivalentDataPropertiesAxiom);

  @NonNull T visit(@NonNull OWLClassAssertionAxiom owlClassAssertionAxiom);

  @NonNull T visit(@NonNull OWLEquivalentClassesAxiom owlEquivalentClassesAxiom);

  @NonNull T visit(@NonNull OWLDataPropertyAssertionAxiom owlDataPropertyAssertionAxiom);

  @NonNull T visit(@NonNull OWLTransitiveObjectPropertyAxiom owlTransitiveObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLIrreflexiveObjectPropertyAxiom owlIrreflexiveObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLSubDataPropertyOfAxiom owlSubDataPropertyOfAxiom);

  @NonNull T visit(@NonNull OWLInverseFunctionalObjectPropertyAxiom owlInverseFunctionalObjectPropertyAxiom);

  @NonNull T visit(@NonNull OWLSameIndividualAxiom owlSameIndividualAxiom);

  @NonNull T visit(@NonNull OWLSubPropertyChainOfAxiom owlSubPropertyChainOfAxiom);

  @NonNull T visit(@NonNull OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom);

  @NonNull T visit(@NonNull OWLHasKeyAxiom owlHasKeyAxiom);

  @NonNull T visit(@NonNull OWLDatatypeDefinitionAxiom owlDatatypeDefinitionAxiom);
}
