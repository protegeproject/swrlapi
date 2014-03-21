package org.swrlapi.sqwrl.values;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public interface SQWRLResultValueFactory
{
	SQWRLClassValue getClassValue(SWRLClassBuiltInArgument classArgument);

	SQWRLClassValue getClassValue(IRI classIRI);

	SQWRLIndividualValue getIndividualValue(SWRLNamedIndividualBuiltInArgument individualArgument);

	SQWRLIndividualValue getIndividualValue(IRI individualIRI);

	SQWRLObjectPropertyValue getObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument);

	SQWRLObjectPropertyValue getObjectPropertyValue(IRI propertyIRI);

	SQWRLDataPropertyValue getDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument);

	SQWRLDataPropertyValue getDataPropertyValue(IRI propertyIRI);

	SQWRLAnnotationPropertyValue getAnnotationPropertyValue(SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

	SQWRLAnnotationPropertyValue getAnnotationPropertyValue(IRI propertyIRI);

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
