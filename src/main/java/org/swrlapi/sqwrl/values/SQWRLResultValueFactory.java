package org.swrlapi.sqwrl.values;

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

	SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

	SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(IRI propertyIRI);

	SQWRLLiteralResultValue getLiteral(OWLLiteral literal);

	SQWRLLiteralResultValue getLiteral(String s);

	SQWRLLiteralResultValue getLiteral(boolean b);

	SQWRLLiteralResultValue getLiteral(int i);

	SQWRLLiteralResultValue getLiteral(long l);

	SQWRLLiteralResultValue getLiteral(float f);

	SQWRLLiteralResultValue getLiteral(double d);

	SQWRLLiteralResultValue getLiteral(short s);

	SQWRLLiteralResultValue getLiteral(XSDTime time);

	SQWRLLiteralResultValue getLiteral(XSDDate date);

	SQWRLLiteralResultValue getLiteral(XSDDateTime dateTime);

	SQWRLLiteralResultValue getLiteralValue(XSDDuration duration);
}
