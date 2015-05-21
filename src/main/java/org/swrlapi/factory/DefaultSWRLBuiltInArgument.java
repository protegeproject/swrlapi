package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
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
import java.util.Set;

abstract class DefaultSWRLBuiltInArgument implements SWRLBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @Nullable private String boundVariableName = null;

  @Override
  public boolean isVariable()
  {
    return false;
  }

  @Override
  public boolean isMultiValueVariable()
  {
    return false;
  }

  @NonNull @Override
  public SWRLVariableBuiltInArgument asVariable()
  {
    throw new SWRLAPIException("Not a SWRLVariableBuiltInArgument");
  }

  @NonNull @Override
  public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
  {
    throw new SWRLAPIException("Not a SWRLMultiVariableBuiltInArgument");
  }

  @Override
  public boolean wasBoundVariable()
  {
    return this.boundVariableName != null;
  }

  @Nullable @Override
  public String getBoundVariableName()
  {
    return this.boundVariableName;
  }

  @Override
  public void setBoundVariableName(@NonNull String boundVariableName)
  {
    this.boundVariableName = boundVariableName;
  }

  @Override
  public boolean containsEntityInSignature(@NonNull OWLEntity owlEntity)
  {
    return false; // TODO implement containsEntityInSignature
  }

  @NonNull @Override
  public Set<OWLEntity> getSignature()
  {
    return Collections.emptySet(); // TODO implement getSignature
  }

  @NonNull @Override
  public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
  {
    return Collections.emptySet(); // TODO implement getAnonymousIndividuals
  }

  @NonNull @Override
  public Set<OWLClass> getClassesInSignature()
  {
    return Collections.emptySet(); // TODO implement getClassesInSignature
  }

  @NonNull @Override
  public Set<OWLDataProperty> getDataPropertiesInSignature()
  {
    return Collections.emptySet(); // TODO implement getDataPropertiesInSignature
  }

  @NonNull @Override
  public Set<OWLObjectProperty> getObjectPropertiesInSignature()
  {
    return Collections.emptySet(); // TODO implement getObjectPropertiesInSignature
  }

  @NonNull @Override
  public Set<OWLNamedIndividual> getIndividualsInSignature()
  {
    return Collections.emptySet(); // TODO implement getIndividualInSignature
  }

  @NonNull @Override
  public Set<OWLDatatype> getDatatypesInSignature()
  {
    return Collections.emptySet(); // TODO implement getDatatypesInSignature
  }

  @NonNull @Override
  public Set<OWLClassExpression> getNestedClassExpressions()
  {
    return Collections.emptySet(); // TODO implement getNestedClassExpressions
  }

  @Override
  public boolean isTopEntity()
  {
    return false;
  }

  @Override
  public boolean isBottomEntity()
  {
    return false;
  }
}
