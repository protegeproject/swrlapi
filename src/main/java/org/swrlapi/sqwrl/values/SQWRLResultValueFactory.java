package org.swrlapi.sqwrl.values;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;
import java.util.List;

/**
 * A factory for creating {@link org.swrlapi.sqwrl.values.SQWRLResultValue} objects.
 *
 * @see org.swrlapi.sqwrl.values.SQWRLResultValue
 */
public interface SQWRLResultValueFactory
{
  @NonNull SQWRLClassResultValue getClassValue(@NonNull SWRLClassBuiltInArgument classArgument);

  @NonNull SQWRLClassResultValue getClassValue(@NonNull IRI classIRI);

  @NonNull SQWRLIndividualResultValue getIndividualValue(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument);

  @NonNull SQWRLIndividualResultValue getIndividualValue(@NonNull IRI individualIRI);

  @NonNull SQWRLObjectPropertyResultValue getObjectPropertyValue(
    SWRLObjectPropertyBuiltInArgument objectPropertyArgument);

  @NonNull SQWRLObjectPropertyResultValue getObjectPropertyValue(@NonNull IRI propertyIRI);

  @NonNull SQWRLDataPropertyResultValue getDataPropertyValue(@NonNull SWRLDataPropertyBuiltInArgument dataPropertyArgument);

  @NonNull SQWRLDataPropertyResultValue getDataPropertyValue(@NonNull IRI propertyIRI);

  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
    SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(IRI propertyIRI);

  @NonNull SQWRLLiteralResultValue getLiteralValue(byte b);

  @NonNull SQWRLLiteralResultValue getLiteralValue(short s);

  @NonNull SQWRLLiteralResultValue getLiteralValue(int i);

  @NonNull SQWRLLiteralResultValue getLiteralValue(long l);

  @NonNull SQWRLLiteralResultValue getLiteralValue(float f);

  @NonNull SQWRLLiteralResultValue getLiteralValue(double d);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull String s);

  @NonNull SQWRLLiteralResultValue getLiteralValue(boolean b);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull URI uri);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull XSDTime time);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDate date);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDateTime dateTime);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull XSDDuration duration);

  @NonNull SQWRLLiteralResultValue getLiteralValue(@NonNull OWLLiteral literal);

  @NonNull SQWRLLiteralResultValue createLeastNarrowNumericLiteralValue(double value,
    @NonNull List<SQWRLLiteralResultValue> inputResultValues);
}
