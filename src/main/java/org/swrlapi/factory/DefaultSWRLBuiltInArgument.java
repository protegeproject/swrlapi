package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLBuiltInException;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

abstract class DefaultSWRLBuiltInArgument implements SWRLBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @Nullable private String boundVariableName = null;

  @Override public boolean isVariable()
  {
    return getSWRLBuiltInArgumentType() == SWRLBuiltInArgumentType.VARIABLE ||
      getSWRLBuiltInArgumentType() == SWRLBuiltInArgumentType.MULTI_VALUE_VARIABLE ||
      getSWRLBuiltInArgumentType() == SWRLBuiltInArgumentType.COLLECTION_VARIABLE;
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

  @NonNull @Override public SQWRLCollectionVariableBuiltInArgument asCollectionVariable() throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SQWRLCollectionVariableBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument() throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(getClass().getName() + " is not an " + SWRLLiteralBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument() throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(getClass().getName() + " is not an " + SWRLNamedBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLNamedIndividualBuiltInArgument asSWRLNamedIndividualBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLNamedIndividualBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLClassBuiltInArgument asSWRLClassBuiltInArgument() throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(getClass().getName() + " is not an " + SWRLClassBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument asSWRLClassExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLClassExpressionBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLObjectPropertyBuiltInArgument asSWRLObjectPropertyBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLObjectPropertyBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLObjectPropertyExpressionBuiltInArgument asSWRLObjectPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLObjectPropertyExpressionBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLDataPropertyBuiltInArgument asSWRLDataPropertyBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLDataPropertyBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLDataPropertyExpressionBuiltInArgument asSWRLDataPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLDataPropertyExpressionBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLDatatypeBuiltInArgument asSWRLDatatypeBuiltInArgument() throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(getClass().getName() + " is not an " + SWRLDatatypeBuiltInArgument.class.getName());
  }

  @NonNull @Override public SWRLAnnotationPropertyBuiltInArgument asSWRLAnnotationPropertyBuiltInArgument()
    throws SWRLBuiltInException
  {
    throw new SWRLBuiltInException(
      getClass().getName() + " is not an " + SWRLAnnotationPropertyBuiltInArgument.class.getName());
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

  @Override public void accept(@NonNull SWRLObjectVisitor swrlObjectVisitor)
  {
    // TODO implement accept(SWRLObjectVisitor)
  }

  @Nonnull @Override public <O> O accept(@NonNull SWRLObjectVisitorEx<O> swrlObjectVisitorEx)
  {
    return null; // TODO implement accept(SWRLObjectVisitor)
  }

  @Override public void accept(@NonNull OWLObjectVisitor owlObjectVisitor)
  {
    // TODO implement accept(OWLObjectVisitor)
  }

  @Nonnull @Override public <O> O accept(@NonNull OWLObjectVisitorEx<O> owlObjectVisitorEx)
  {
    return null; // TODO implement accept(owlObjectVisitor)
  }

  @Override public int compareTo(OWLObject o)
  {
    return -1;  // TODO implement compareTo(OWLObject
  }

  @Nonnull @Override public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature()
  {
    return Collections.emptySet(); // TODO getAnnotationPropertiesInSignature
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
