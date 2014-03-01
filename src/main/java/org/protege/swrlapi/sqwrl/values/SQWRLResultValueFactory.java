package org.protege.swrlapi.sqwrl.values;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.OWLLiteral;

public interface SQWRLResultValueFactory
{
	SQWRLClassValue createClassValue(SWRLClassBuiltInArgument classArgument);

	SQWRLClassValue createClassValue(URI classURI, String prefixedName);

	SQWRLIndividualValue createIndividualValue(SWRLIndividualBuiltInArgument individualArgument);

	SQWRLIndividualValue createIndividualValue(URI individualURI, String prefixedName);

	SQWRLObjectPropertyValue createObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument);

	SQWRLObjectPropertyValue createObjectPropertyValue(URI propertyURI, String prefixedName);

	SQWRLDataPropertyValue createDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument);

	SQWRLDataPropertyValue createDataPropertyValue(URI propertyURI, String prefixedName);

	SQWRLAnnotationPropertyValue createAnnotationPropertyValue(SWRLAnnotationPropertyBuiltInArgument dataPropertyArgument);

	SQWRLAnnotationPropertyValue createAnnotationPropertyValue(URI propertyURI, String prefixedName);

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
