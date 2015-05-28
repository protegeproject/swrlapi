package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.literal.Literal;
import org.swrlapi.resolvers.IRIResolver;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSQWRLResultValueFactory implements SQWRLResultValueFactory
{
  @NonNull private final IRIResolver iriResolver;
  @NonNull private final OWLLiteralFactory owlLiteralFactory;

  public DefaultSQWRLResultValueFactory(@NonNull IRIResolver iriResolver)
  {
    this.iriResolver = iriResolver;
    this.owlLiteralFactory = SWRLAPIFactory.createOWLLiteralFactory();
  }

  @NonNull @Override
  public SQWRLClassResultValue getClassValue(@NonNull SWRLClassBuiltInArgument classArgument)
  {
    return getClassValue(classArgument.getIRI());
  }

  @NonNull @Override
  public SQWRLClassResultValue getClassValue(@NonNull IRI classIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(classIRI);

    return new DefaultSQWRLClassResultValue(classIRI, prefixedName);
  }

  @NonNull @Override
  public SQWRLIndividualResultValue getIndividualValue(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

    return new DefaultSQWRLIndividualResultValue(individualArgument.getIRI(), prefixedName);
  }

  @NonNull @Override
  public SQWRLIndividualResultValue getIndividualValue(@NonNull IRI individualIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualIRI);

    return new DefaultSQWRLIndividualResultValue(individualIRI, prefixedName);
  }

  @NonNull @Override
  public SQWRLObjectPropertyResultValue getObjectPropertyValue(@NonNull SWRLObjectPropertyBuiltInArgument objectPropertyArgument)
  {
    return getObjectPropertyValue(objectPropertyArgument.getIRI());
  }

  @NonNull @Override
  public SQWRLObjectPropertyResultValue getObjectPropertyValue(@NonNull IRI propertyIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

    return new DefaultSQWRLObjectPropertyResultValue(propertyIRI, prefixedName);
  }

  @NonNull @Override
  public SQWRLDataPropertyResultValue getDataPropertyValue(@NonNull SWRLDataPropertyBuiltInArgument dataPropertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(dataPropertyArgument.getIRI());

    return new DefaultSQWRLDataPropertyResultValue(dataPropertyArgument.getIRI(), prefixedName);
  }

  @NonNull @Override
  public SQWRLDataPropertyResultValue getDataPropertyValue(@NonNull IRI propertyIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

    return new DefaultSQWRLDataPropertyResultValue(propertyIRI, prefixedName);
  }

  @NonNull @Override
  public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
      @NonNull SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(annotationPropertyArgument.getIRI());

    return new DefaultSQWRLAnnotationPropertyResultValue(annotationPropertyArgument.getIRI(), prefixedName);
  }

  @NonNull @Override
  public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(@NonNull IRI propertyIRI)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

    return new DefaultSQWRLAnnotationPropertyResultValue(propertyIRI, prefixedName);
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(byte b)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(b),
        XSDVocabulary.BYTE.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(short s)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(s),
        XSDVocabulary.SHORT.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(int i)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(i), XSDVocabulary.INT.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(long l)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(l),
        XSDVocabulary.LONG.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(float f)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(f),
        XSDVocabulary.FLOAT.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(double d)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(d),
        XSDVocabulary.DOUBLE.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull String s)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(s),
        XSDVocabulary.STRING.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(boolean b)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(b),
        XSDVocabulary.BOOLEAN.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull URI uri)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(uri),
        XSDVocabulary.ANY_URI.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDTime time)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(time),
        XSDVocabulary.TIME.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDate date)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(date),
        XSDVocabulary.DATE.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDateTime dateTime)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(dateTime),
        XSDVocabulary.DATE_TIME.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDuration duration)
  {
    return new DefaultSQWRLLiteralResultValue(getOWLLiteralFactory().getOWLLiteral(duration),
        XSDVocabulary.DURATION.getPrefixedName());
  }

  @NonNull @Override
  public SQWRLLiteralResultValue getLiteralValue(@NonNull OWLLiteral literal)
  {
    IRI datatypeIRI = literal.getDatatype().getIRI();

    return new DefaultSQWRLLiteralResultValue(literal, getIRIResolver().iri2PrefixedName(datatypeIRI));
  }

  @NonNull @Override
  public SQWRLLiteralResultValue createLeastNarrowNumericLiteralValue(double value,
      @NonNull List<SQWRLLiteralResultValue> inputResultValues)
  {
    List<OWLLiteral> numericLiterals = inputResultValues.stream().filter(Literal::isNumeric)
        .map(Literal::getOWLLiteral).collect(Collectors.toList());

    OWLLiteral literal = getOWLLiteralFactory().createLeastNarrowNumericOWLLiteral(value, numericLiterals);

    return getLiteralValue(literal);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }

  @NonNull private IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }
}
