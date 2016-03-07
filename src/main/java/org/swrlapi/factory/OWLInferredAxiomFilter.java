package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.SWRLRule;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A class to filter trivially obvious inferred OWL axioms
 */
public class OWLInferredAxiomFilter implements OWLAxiomVisitorEx<Boolean>
{
  @NonNull private final Set<@NonNull OWLAxiom> assertedAxioms;

  public OWLInferredAxiomFilter(@NonNull Set<@NonNull OWLAxiom> assertedAxioms)
  {
    this.assertedAxioms = assertedAxioms;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDeclarationAxiom owlDeclarationAxiom)
  {
    return true;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDatatypeDefinitionAxiom owlDatatypeDefinitionAxiom)
  {
    return true;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLAnnotationAssertionAxiom owlAnnotationAssertionAxiom)
  {
    return true;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSubAnnotationPropertyOfAxiom owlSubAnnotationPropertyOfAxiom)
  {
    return true;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLAnnotationPropertyDomainAxiom owlAnnotationPropertyDomainAxiom)
  {
    return true;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLAnnotationPropertyRangeAxiom owlAnnotationPropertyRangeAxiom)
  {
    return true;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSubClassOfAxiom axiom)
  {
    OWLClassExpression superClass = axiom.getSuperClass();
    OWLClassExpression subClass = axiom.getSubClass();
    Set<OWLSubClassOfAxiom> subClassOfAxioms = this.assertedAxioms.stream().filter(OWLSubClassOfAxiom.class::isInstance)
      .map(OWLSubClassOfAxiom.class::cast).collect(Collectors.toSet());
    Set<OWLClassExpression> superClassSubClasses = subClassOfAxioms.stream().
      filter(sca -> sca.getSuperClass().equals(superClass)).map(sca -> sca.getSubClass())
      .filter(subC -> !subC.isOWLNothing() && !subC.equals(subClass)).collect(Collectors.toSet());
    Set<OWLClassExpression> subClassSuperClasses = subClassOfAxioms.stream()
      .filter(sca -> sca.getSubClass().equals(subClass)).map(sca -> sca.getSuperClass())
      .filter(supC -> !supC.isOWLThing() && !supC.equals(superClass)).collect(Collectors.toSet());
    Set<OWLClassExpression> common = new HashSet<>(superClassSubClasses);

    common.retainAll(subClassSuperClasses);

    return !common.isEmpty();

    //    return axiom.getSubClass().isOWLNothing() || axiom.getSuperClass().isOWLThing() || axiom.getSubClass()
    //      .equals(axiom.getSuperClass());
    //     TODO filter SCA(A, B) where there exists SCA(A, X) and SCA(X, B) where X is not owl:Thing
  }

  @NonNull @Override public Boolean visit(@NonNull OWLNegativeObjectPropertyAssertionAxiom axiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLAsymmetricObjectPropertyAxiom owlAsymmetricObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLReflexiveObjectPropertyAxiom owlReflexiveObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDisjointClassesAxiom owlDisjointClassesAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDataPropertyDomainAxiom axiom)
  {
    return axiom.getDomain().isOWLThing();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLObjectPropertyDomainAxiom axiom)
  {
    return axiom.getDomain().isOWLThing();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLEquivalentObjectPropertiesAxiom axiom)
  {
    return axiom.getProperties().size() == 1 || (axiom.getProperties().size() == 2 && axiom.getProperties().stream()
      .anyMatch(p -> p.isOWLTopObjectProperty()));
  }

  @NonNull @Override public Boolean visit(
    @NonNull OWLNegativeDataPropertyAssertionAxiom owlNegativeDataPropertyAssertionAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDifferentIndividualsAxiom axiom)
  {
    return axiom.getIndividuals().size() == 1;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDisjointDataPropertiesAxiom owlDisjointDataPropertiesAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDisjointObjectPropertiesAxiom owlDisjointObjectPropertiesAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLObjectPropertyRangeAxiom axiom)
  {
    return axiom.getRange().isOWLThing();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLObjectPropertyAssertionAxiom axiom)
  {
    return axiom.getProperty().isOWLTopObjectProperty();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLFunctionalObjectPropertyAxiom owlFunctionalObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSubObjectPropertyOfAxiom axiom)
  {
    return axiom.getSubProperty().equals(axiom.getSuperProperty()) ||
      axiom.getSubProperty().isOWLBottomObjectProperty() || axiom.getSuperProperty().isOWLTopObjectProperty();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDisjointUnionAxiom owlDisjointUnionAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSymmetricObjectPropertyAxiom owlSymmetricObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDataPropertyRangeAxiom axiom)
  {
    return axiom.getRange().isTopDatatype();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLFunctionalDataPropertyAxiom owlFunctionalDataPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLEquivalentDataPropertiesAxiom axiom)
  {
    return axiom.getProperties().size() == 1 || (axiom.getProperties().size() == 2 && axiom.getProperties().stream()
      .anyMatch(p -> p.isOWLTopDataProperty()));
  }

  @NonNull @Override public Boolean visit(@NonNull OWLClassAssertionAxiom axiom)
  {
    return axiom.getClassExpression().isOWLThing();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLEquivalentClassesAxiom axiom)
  {
    return axiom.getClassExpressions().size() == 1 || axiom.containsOWLThing();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLDataPropertyAssertionAxiom axiom)
  {
    return axiom.getProperty().isOWLTopDataProperty();
  }

  @NonNull @Override public Boolean visit(@NonNull OWLTransitiveObjectPropertyAxiom owlTransitiveObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLIrreflexiveObjectPropertyAxiom owlIrreflexiveObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSubDataPropertyOfAxiom axiom)
  {
    return axiom.getSubProperty().equals(axiom.getSuperProperty()) ||
      axiom.getSubProperty().isOWLBottomDataProperty() || axiom.getSuperProperty().isOWLTopDataProperty();
  }

  @NonNull @Override public Boolean visit(
    @NonNull OWLInverseFunctionalObjectPropertyAxiom owlInverseFunctionalObjectPropertyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSameIndividualAxiom axiom)
  {
    return axiom.getIndividuals().size() <= 1;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLSubPropertyChainOfAxiom owlSubPropertyChainOfAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull OWLHasKeyAxiom owlHasKeyAxiom)
  {
    return false;
  }

  @NonNull @Override public Boolean visit(@NonNull SWRLRule swrlRule)
  {
    return true;
  }
}
