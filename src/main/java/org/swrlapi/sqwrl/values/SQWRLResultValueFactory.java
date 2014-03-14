package org.swrlapi.sqwrl.values;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public interface SQWRLResultValueFactory
{
	SQWRLClassValue createClassValue(SWRLClassBuiltInArgument classArgument);

	SQWRLClassValue createClassValue(IRI classIRI, String prefixedName);

	SQWRLIndividualValue createIndividualValue(SWRLIndividualBuiltInArgument individualArgument);

	SQWRLIndividualValue createIndividualValue(IRI individualIRI, String prefixedName);

	SQWRLObjectPropertyValue createObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument);

	SQWRLObjectPropertyValue createObjectPropertyValue(IRI propertyIRI, String prefixedName);

	SQWRLDataPropertyValue createDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument);

	SQWRLDataPropertyValue createDataPropertyValue(IRI propertyIRI, String prefixedName);

	SQWRLAnnotationPropertyValue createAnnotationPropertyValue(SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

	SQWRLAnnotationPropertyValue createAnnotationPropertyValue(IRI propertyIRI, String prefixedName);

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

	SQWRLLiteralResultValue createLiteralValue(XSDDuration duration);
}
