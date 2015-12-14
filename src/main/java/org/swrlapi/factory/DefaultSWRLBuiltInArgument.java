package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLAPIException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

abstract class DefaultSWRLBuiltInArgument implements SWRLBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @Nullable private String boundVariableName = null;

  @Override public boolean isVariable()
  {
    return false;
  }

  @Override public boolean isMultiValueVariable()
  {
    return false;
  }

  @NonNull @Override public SWRLVariableBuiltInArgument asVariable()
  {
    throw new SWRLAPIException("Not a SWRLVariableBuiltInArgument");
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
  {
    throw new SWRLAPIException("Not a SWRLMultiVariableBuiltInArgument");
  }

  @Override public boolean wasBoundVariable()
  {
    return this.boundVariableName != null;
  }

  @NonNull @Override public Optional<@NonNull String> getBoundVariableName()
  {
    if (this.boundVariableName != null)
      return Optional.of(this.boundVariableName);
    else
      return Optional.<@NonNull String>empty();
  }

  @Override public void setBoundVariableName(@NonNull String boundVariableName)
  {
    this.boundVariableName = boundVariableName;
  }

  @Override public boolean containsEntityInSignature(@NonNull OWLEntity owlEntity)
  {
    return false; // TODO implement containsEntityInSignature
  }

  @NonNull @Override public Set<@NonNull OWLEntity> getSignature()
  {
    return Collections.<@NonNull OWLEntity>emptySet(); // TODO implement getSignature
  }

  @NonNull @Override public Set<@NonNull OWLAnonymousIndividual> getAnonymousIndividuals()
  {
    return Collections.<@NonNull OWLAnonymousIndividual>emptySet(); // TODO implement getAnonymousIndividuals
  }

  @NonNull @Override public Set<@NonNull OWLClass> getClassesInSignature()
  {
    return Collections.<@NonNull OWLClass>emptySet(); // TODO implement getClassesInSignature
  }

  @NonNull @Override public Set<@NonNull OWLDataProperty> getDataPropertiesInSignature()
  {
    return Collections.<@NonNull OWLDataProperty>emptySet(); // TODO implement getDataPropertiesInSignature
  }

  @NonNull @Override public Set<@NonNull OWLObjectProperty> getObjectPropertiesInSignature()
  {
    return Collections.<@NonNull OWLObjectProperty>emptySet(); // TODO implement getObjectPropertiesInSignature
  }

  @NonNull @Override public Set<@NonNull OWLNamedIndividual> getIndividualsInSignature()
  {
    return Collections.<@NonNull OWLNamedIndividual>emptySet(); // TODO implement getIndividualInSignature
  }

  @NonNull @Override public Set<@NonNull OWLDatatype> getDatatypesInSignature()
  {
    return Collections.<@NonNull OWLDatatype>emptySet(); // TODO implement getDatatypesInSignature
  }

  @NonNull @Override public Set<@NonNull OWLClassExpression> getNestedClassExpressions()
  {
    return Collections.<@NonNull OWLClassExpression>emptySet(); // TODO implement getNestedClassExpressions
  }

  @Override public boolean isTopEntity()
  {
    return false;
  }

  @Override public boolean isBottomEntity()
  {
    return false;
  }
}
