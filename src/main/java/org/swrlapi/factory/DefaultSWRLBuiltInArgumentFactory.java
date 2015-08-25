package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.net.URI;
import java.util.List;

class DefaultSWRLBuiltInArgumentFactory implements SWRLBuiltInArgumentFactory
{
  @NonNull private final IRIResolver iriResolver;
  @NonNull private final OWLLiteralFactory owlLiteralFactory;

  public DefaultSWRLBuiltInArgumentFactory(@NonNull IRIResolver iriResolver)
  {
    this.iriResolver = iriResolver;
    this.owlLiteralFactory = SWRLAPIFactory.createOWLLiteralFactory();
  }

  @NonNull @Override public SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(@NonNull IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);
    SWRLVariableBuiltInArgument argument = new DefaultSWRLVariableBuiltInArgument(variableIRI, variablePrefixedName);
    argument.setUnbound();
    return argument;
  }

  @NonNull @Override public SWRLVariableBuiltInArgument getVariableBuiltInArgument(@NonNull IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);
    return new DefaultSWRLVariableBuiltInArgument(variableIRI, variablePrefixedName);
  }

  @NonNull @Override public SWRLClassBuiltInArgument getClassBuiltInArgument(OWLClass cls)
  {
    return new DefaultSWRLClassBuiltInArgument(cls);
  }

  @NonNull @Override public SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(
    @NonNull OWLObjectProperty property)
  {
    return new DefaultSWRLObjectPropertyBuiltInArgument(property);
  }

  @NonNull @Override public SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(
    @NonNull OWLDataProperty property)
  {
    return new DefaultSWRLDataPropertyBuiltInArgument(property);
  }

  @NonNull @Override public SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(
    @NonNull OWLAnnotationProperty property)
  {
    return new DefaultDefaultSWRLAnnotationPropertyBuiltInArgument(property);
  }

  @NonNull @Override public SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(@NonNull OWLDatatype datatype)
  {
    return new DefaultSWRLDatatypeBuiltInArgument(datatype);
  }

  @NonNull @Override public SWRLNamedIndividualBuiltInArgument getNamedIndividualBuiltInArgument(
    @NonNull OWLNamedIndividual individual)
  {
    return new DefaultSWRLNamedIndividualBuiltInArgument(individual);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull OWLLiteral literal)
  {
    return new DefaultSWRLLiteralBuiltInArgument(literal);
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull String s)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(boolean b)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(short s)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(int i)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(i));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(long l)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(l));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(float f)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(f));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(double d)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(d));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(byte b)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull URI uri)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDDate date)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDTime time)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDDateTime datetime)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @NonNull @Override public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDDuration duration)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(
    @NonNull IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return new DefaultSWRLMultiValueVariableBuiltInArgument(variableIRI, variablePrefixedName);
  }

  @NonNull @Override public SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(
    @NonNull IRI variableIRI, List<SWRLBuiltInArgument> arguments)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return new DefaultSWRLMultiValueVariableBuiltInArgument(variableIRI, variablePrefixedName, arguments);
  }

  @NonNull @Override public SQWRLCollectionVariableBuiltInArgument getSQWRLCollectionVariableBuiltInArgument(
    @NonNull IRI variableIRI, @NonNull String queryName, @NonNull String collectionName,
    @NonNull String collectionGroupID)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return new DefaultSQWRLCollectionVariableBuiltInArgument(variableIRI, variablePrefixedName, queryName,
      collectionName, collectionGroupID);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }

  private IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }
}
