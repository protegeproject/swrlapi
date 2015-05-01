package org.swrlapi.sqwrl.values.impl;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.impl.DefaultOWLLiteralFactory;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

public class DefaultSQWRLResultValueFactory implements SQWRLResultValueFactory
{
  private final IRIResolver iriResolver;
  private final OWLLiteralFactory owlLiteralFactory;

  public DefaultSQWRLResultValueFactory(IRIResolver iriResolver)
  {
    this.iriResolver = iriResolver;
    this.owlLiteralFactory = new DefaultOWLLiteralFactory();
  }

  @Override
  public SQWRLClassResultValue getClassValue(SWRLClassBuiltInArgument classArgument)
  {
    return getClassValue(classArgument.getIRI());
  }

  @Override
  public SQWRLClassResultValue getClassValue(IRI classIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(classIRI);

    return new SQWRLClassResultValueImpl(classIRI, prefixedName);
  }

  @Override
  public SQWRLIndividualResultValue getIndividualValue(SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

    return new SQWRLIndividualResultValueImpl(individualArgument.getIRI(), prefixedName);
  }

  @Override
  public SQWRLIndividualResultValue getIndividualValue(IRI individualIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualIRI);

    return new SQWRLIndividualResultValueImpl(individualIRI, prefixedName);
  }

  @Override
  public SQWRLObjectPropertyResultValue getObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument)
  {
    return getObjectPropertyValue(objectPropertyArgument.getIRI());
  }

  @Override
  public SQWRLObjectPropertyResultValue getObjectPropertyValue(IRI propertyIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

    return new SQWRLObjectPropertyResultValueImpl(propertyIRI, prefixedName);
  }

  @Override
  public SQWRLDataPropertyResultValue getDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(dataPropertyArgument.getIRI());

    return new SQWRLDataPropertyResultValueImpl(dataPropertyArgument.getIRI(), prefixedName);
  }

  @Override
  public SQWRLDataPropertyResultValue getDataPropertyValue(IRI propertyIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

    return new SQWRLDataPropertyResultValueImpl(propertyIRI, prefixedName);
  }

  @Override
  public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
      SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(annotationPropertyArgument.getIRI());

    return new SQWRLAnnotationPropertyResultValueImpl(annotationPropertyArgument.getIRI(), prefixedName);
  }

  @Override
  public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(IRI propertyIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

    return new SQWRLAnnotationPropertyResultValueImpl(propertyIRI, prefixedName);
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(byte b)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(b),
        XSDVocabulary.BYTE.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(short s)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(s),
        XSDVocabulary.SHORT.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(int i)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(i), XSDVocabulary.INT.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(long l)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(l),
        XSDVocabulary.LONG.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(float f)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(f),
        XSDVocabulary.FLOAT.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(double d)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(d),
        XSDVocabulary.DOUBLE.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(String s)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(s),
        XSDVocabulary.STRING.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(boolean b)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(b),
        XSDVocabulary.BOOLEAN.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(URI uri)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(uri),
        XSDVocabulary.ANY_URI.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(XSDTime time)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(time),
        XSDVocabulary.TIME.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(XSDDate date)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(date),
        XSDVocabulary.DATE.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(XSDDateTime dateTime)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(dateTime),
        XSDVocabulary.DATE_TIME.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(XSDDuration duration)
  {
    return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(duration),
        XSDVocabulary.DURATION.getPrefixedName());
  }

  @Override
  public SQWRLLiteralResultValue getLiteralValue(OWLLiteral literal)
  {
    IRI datatypeIRI = literal.getDatatype().getIRI();

    return new SQWRLLiteralResultValueImpl(literal, getIRIResolver().iri2PrefixedName(datatypeIRI));
  }

  @Override
  public SQWRLLiteralResultValue createLeastNarrowNumericLiteralValue(double value,
      List<SQWRLLiteralResultValue> inputResultValues)
  {
    List<OWLLiteral> numericLiterals = inputResultValues.stream().filter(e -> e.isNumeric())
        .map(e -> e.getOWLLiteral()).collect(Collectors.toList());

    OWLLiteral literal = getOWLLiteralFactory().createLeastNarrowNumericOWLLiteral(value, numericLiterals);

    return getLiteralValue(literal);
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
