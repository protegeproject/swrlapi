package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLDatatypeResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyExpressionResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

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

  @NonNull SQWRLClassExpressionResultValue getClassValue(
    @NonNull SWRLClassExpressionBuiltInArgument classExpressionArgument);

  @NonNull SQWRLNamedIndividualResultValue getNamedIndividualValue(
    @NonNull SWRLNamedIndividualBuiltInArgument individualArgument);

  @NonNull SQWRLNamedIndividualResultValue getNamedIndividualValue(@NonNull IRI individualIRI);

  @NonNull SQWRLObjectPropertyResultValue getObjectPropertyValue(
    SWRLObjectPropertyBuiltInArgument objectPropertyArgument);

  @NonNull SQWRLObjectPropertyResultValue getObjectPropertyValue(@NonNull IRI propertyIRI);

  @NonNull SQWRLObjectPropertyExpressionResultValue getObjectPropertyExpressionValue(
    SWRLObjectPropertyExpressionBuiltInArgument objectPropertyExpressionArgument);

  @NonNull SQWRLDataPropertyResultValue getDataPropertyValue(
    @NonNull SWRLDataPropertyBuiltInArgument dataPropertyArgument);

  @NonNull SQWRLDataPropertyResultValue getDataPropertyValue(@NonNull IRI propertyIRI);

  @NonNull SQWRLDataPropertyExpressionResultValue getDataPropertyExpressionValue(
    SWRLDataPropertyExpressionBuiltInArgument dataPropertyExpressionArgument);

  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
    SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(IRI propertyIRI);

  @NonNull SQWRLDatatypeResultValue getDatatypeValue(SWRLDatatypeBuiltInArgument datatypeArgument);

  @NonNull SQWRLDatatypeResultValue getDatatypeValue(IRI propertyIRI);

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
    @NonNull List<@NonNull SQWRLLiteralResultValue> inputResultValues);
}
