package org.swrlapi.sqwrl.values;

import java.net.URI;
import java.util.List;

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

/**
 * A factory for creating {@link org.swrlapi.sqwrl.values.SQWRLResultValue} objects.
 *
 * @see org.swrlapi.sqwrl.values.SQWRLResultValue
 */
public interface SQWRLResultValueFactory
{
  SQWRLClassResultValue getClassValue(SWRLClassBuiltInArgument classArgument);

  SQWRLClassResultValue getClassValue(IRI classIRI);

  SQWRLIndividualResultValue getIndividualValue(SWRLNamedIndividualBuiltInArgument individualArgument);

  SQWRLIndividualResultValue getIndividualValue(IRI individualIRI);

  SQWRLObjectPropertyResultValue getObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument);

  SQWRLObjectPropertyResultValue getObjectPropertyValue(IRI propertyIRI);

  SQWRLDataPropertyResultValue getDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument);

  SQWRLDataPropertyResultValue getDataPropertyValue(IRI propertyIRI);

  SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
      SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

  SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(IRI propertyIRI);

  SQWRLLiteralResultValue getLiteralValue(byte b);

  SQWRLLiteralResultValue getLiteralValue(short s);

  SQWRLLiteralResultValue getLiteralValue(int i);

  SQWRLLiteralResultValue getLiteralValue(long l);

  SQWRLLiteralResultValue getLiteralValue(float f);

  SQWRLLiteralResultValue getLiteralValue(double d);

  SQWRLLiteralResultValue getLiteralValue(String s);

  SQWRLLiteralResultValue getLiteralValue(boolean b);

  SQWRLLiteralResultValue getLiteralValue(URI uri);

  SQWRLLiteralResultValue getLiteralValue(XSDTime time);

  SQWRLLiteralResultValue getLiteralValue(XSDDate date);

  SQWRLLiteralResultValue getLiteralValue(XSDDateTime dateTime);

  SQWRLLiteralResultValue getLiteralValue(XSDDuration duration);

  SQWRLLiteralResultValue getLiteralValue(OWLLiteral literal);

  SQWRLLiteralResultValue createLeastNarrowNumericLiteralValue(double value,
      List<SQWRLLiteralResultValue> inputResultValues);
}
