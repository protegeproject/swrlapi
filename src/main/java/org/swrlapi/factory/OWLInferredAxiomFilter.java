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
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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
  @NonNull private final Set<@NonNull OWLAxiom> axioms;

  public OWLInferredAxiomFilter(@NonNull Set<@NonNull OWLAxiom> axioms)
  {
    this.axioms = axioms;
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

  /**
   * Filter SCA(A, B) where A is owl:Thing or where B is owl:Nothing or where there exists SCA(A, X) and SCA(X, B)
   * where X is not owl:Thing, A, B
   *
   * @param axiom A subclass axiom
   * @return True if the axiom should be filters
   */
  @NonNull @Override public Boolean visit(@NonNull OWLSubClassOfAxiom axiom)
  {
    OWLClassExpression superClass = axiom.getSuperClass();
    OWLClassExpression subClass = axiom.getSubClass();

    if (superClass.equals(subClass))
      return true;
    else if (superClass.isOWLThing())
      return true;
    else if (subClass.isOWLNothing())
      return true;
    else {
      Set<OWLSubClassOfAxiom> subClassOfAxioms = this.axioms.stream().filter(OWLSubClassOfAxiom.class::isInstance)
        .map(OWLSubClassOfAxiom.class::cast).collect(Collectors.toSet());
      Set<OWLClassExpression> superClassSubClasses = subClassOfAxioms.stream().
        filter(sca -> sca.getSuperClass().equals(superClass)).map(sca -> sca.getSubClass())
        .filter(subC -> !subC.isOWLNothing() && !subC.equals(subClass) && !subC.equals(superClass))
        .collect(Collectors.toSet());
      Set<OWLClassExpression> subClassSuperClasses = subClassOfAxioms.stream()
        .filter(sca -> sca.getSubClass().equals(subClass)).map(sca -> sca.getSuperClass())
        .filter(supC -> !supC.isOWLThing() && !supC.equals(superClass) && !supC.equals(subClass))
        .collect(Collectors.toSet());
      Set<OWLClassExpression> common = new HashSet<>(superClassSubClasses);

      common.retainAll(subClassSuperClasses);

      return !common.isEmpty();
    }
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

  /**
   * Filter SOPA(A, B) where A is owl:TopObjectProperty or where B is owl:BottomObjectProperty or there exists
   * SOPA(A, X) and SOPA(X, B) where X is not owl:TopObjectProperty, A, B
   *
   * @param axiom OWL object property axiom
   * @return True if axiom is to be filtered
   */
  @NonNull @Override public Boolean visit(@NonNull OWLSubObjectPropertyOfAxiom axiom)
  {
    OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
    OWLObjectPropertyExpression subProperty = axiom.getSubProperty();

    if (superProperty.equals(subProperty))
      return true;
    else if (superProperty.isOWLTopObjectProperty())
      return true;
    else if (subProperty.isOWLBottomObjectProperty())
      return true;
    else {
      Set<OWLSubObjectPropertyOfAxiom> subPropertyAxioms = this.axioms.stream()
        .filter(OWLSubObjectPropertyOfAxiom.class::isInstance).map(OWLSubObjectPropertyOfAxiom.class::cast)
        .collect(Collectors.toSet());
      Set<OWLObjectPropertyExpression> superPropertySubProperties = subPropertyAxioms.stream().
        filter(sopa -> sopa.getSuperProperty().equals(superProperty)).map(sopa -> sopa.getSubProperty())
        .filter(subP -> !subP.isOWLBottomObjectProperty() && !subP.equals(subProperty) && !subP.equals(superProperty))
        .collect(Collectors.toSet());
      Set<OWLObjectPropertyExpression> subPropertySuperProperties = subPropertyAxioms.stream()
        .filter(sdpa -> sdpa.getSubProperty().equals(subProperty)).map(sdpa -> sdpa.getSuperProperty())
        .filter(supP -> !supP.isOWLTopObjectProperty() && !supP.equals(superProperty) && !supP.equals(subProperty))
        .collect(Collectors.toSet());
      Set<OWLObjectPropertyExpression> common = new HashSet<>(superPropertySubProperties);

      common.retainAll(subPropertySuperProperties);

      return !common.isEmpty();
    }
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

  /**
   * Filter SDPA(A, B) where A is owl:TopDataProperty or where B is owl:BottomDataProperty or there exists
   * SDPA(A, X) and SDPA(X, B) where X is not owl:TopDataProperty, A, B
   *
   * @param axiom OWL data property axiom
   * @return True if axiom is to be filtered
   */
  @NonNull @Override public Boolean visit(@NonNull OWLSubDataPropertyOfAxiom axiom)
  {
    OWLDataPropertyExpression superProperty = axiom.getSuperProperty();
    OWLDataPropertyExpression subProperty = axiom.getSubProperty();

    if (superProperty.equals(subProperty))
      return true;
    else if (superProperty.isOWLTopDataProperty())
      return true;
    else if (subProperty.isOWLBottomDataProperty())
      return true;
    else {
      Set<OWLSubDataPropertyOfAxiom> subPropertyAxioms = this.axioms.stream()
        .filter(OWLSubDataPropertyOfAxiom.class::isInstance).map(OWLSubDataPropertyOfAxiom.class::cast)
        .collect(Collectors.toSet());
      Set<OWLDataPropertyExpression> superPropertySubProperties = subPropertyAxioms.stream().
        filter(sdpa -> sdpa.getSuperProperty().equals(superProperty)).map(sdpa -> sdpa.getSubProperty())
        .filter(subP -> !subP.isOWLBottomDataProperty() && !subP.equals(subProperty) && !subP.equals(superProperty))
        .collect(Collectors.toSet());
      Set<OWLDataPropertyExpression> subPropertySuperProperties = subPropertyAxioms.stream()
        .filter(sdpa -> sdpa.getSubProperty().equals(subProperty)).map(sdpa -> sdpa.getSuperProperty())
        .filter(supP -> !supP.isOWLTopDataProperty() && !supP.equals(superProperty) && !supP.equals(subProperty))
        .collect(Collectors.toSet());
      Set<OWLDataPropertyExpression> common = new HashSet<>(superPropertySubProperties);

      common.retainAll(subPropertySuperProperties);

      return !common.isEmpty();
    }
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
