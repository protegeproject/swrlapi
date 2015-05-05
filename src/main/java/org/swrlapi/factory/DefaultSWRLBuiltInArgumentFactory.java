package org.swrlapi.factory;

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
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;
import java.util.List;

class DefaultSWRLBuiltInArgumentFactory implements SWRLBuiltInArgumentFactory
{
  private final IRIResolver iriResolver;
  private final OWLLiteralFactory owlLiteralFactory;

  public DefaultSWRLBuiltInArgumentFactory(IRIResolver iriResolver)
  {
    this.iriResolver = iriResolver;
    this.owlLiteralFactory = SWRLAPIFactory.getOWLLiteralFactory();
  }

  @Override
  public SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);
    SWRLVariableBuiltInArgument argument = new DefaultSWRLVariableBuiltInArgument(variableIRI, variablePrefixedName);
    argument.setUnbound();
    return argument;
  }

  @Override
  public SWRLVariableBuiltInArgument getVariableBuiltInArgument(IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);
    return new DefaultSWRLVariableBuiltInArgument(variableIRI, variablePrefixedName);
  }

  @Override
  public SWRLClassBuiltInArgument getClassBuiltInArgument(OWLClass cls)
  {
    return new DefaultSWRLClassBuiltInArgument(cls);
  }

  @Override
  public SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(OWLObjectProperty property)
  {
    return new DefaultSWRLObjectPropertyBuiltInArgument(property);
  }

  @Override
  public SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(OWLDataProperty property)
  {
    return new DefaultSWRLDataPropertyBuiltInArgument(property);
  }

  @Override
  public SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
  {
    return new DefaultDefaultSWRLAnnotationPropertyBuiltInArgument(property);
  }

  @Override
  public SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(OWLDatatype datatype)
  {
    return new DefaultSWRLDatatypeBuiltInArgument(datatype);
  }

  @Override
  public SWRLNamedIndividualBuiltInArgument getNamedIndividualBuiltInArgument(OWLNamedIndividual individual)
  {
    return new DefaultSWRLNamedIndividualBuiltInArgument(individual);
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(OWLLiteral literal)
  {
    return new DefaultSWRLLiteralBuiltInArgument(literal);
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(String s)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(boolean b)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(short s)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(s));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(int i)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(i));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(long l)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(l));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(float f)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(f));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(double d)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(d));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(byte b)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(b));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(URI uri)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(uri));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDate date)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(date));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDTime time)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(time));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDateTime datetime)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(datetime));
  }

  @Override
  public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDuration duration)
  {
    return new DefaultSWRLLiteralBuiltInArgument(getOWLLiteralFactory().getOWLLiteral(duration));
  }

  @Override
  public SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return new DefaultSWRLMultiValueVariableBuiltInArgument(variableIRI, variablePrefixedName);
  }

  @Override
  public SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI,
      List<SWRLBuiltInArgument> arguments)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return new DefaultSWRLMultiValueVariableBuiltInArgument(variableIRI, variablePrefixedName, arguments);
  }

  @Override
  public SQWRLCollectionVariableBuiltInArgument getSQWRLCollectionVariableBuiltInArgument(IRI variableIRI,
      String queryName, String collectionName, String collectionGroupID)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return new DefaultSQWRLCollectionVariableBuiltInArgument(variableIRI, variablePrefixedName, queryName, collectionName,
        collectionGroupID);
  }

  private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }

  private IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }
}
