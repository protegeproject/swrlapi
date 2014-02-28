package org.protege.swrlapi.core.arguments;

import java.net.URI;
import java.util.List;

import org.protege.owl.portability.model.OWLAnnotationPropertyAdapter;
import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLIndividualAdapter;
import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;

public interface SWRLBuiltInArgumentFactory
{
	SWRLVariableBuiltInArgument createVariableArgument(String variableName);

	SWRLVariableBuiltInArgument createUnboundVariableArgument(String variableName);

	SWRLClassBuiltInArgument createClassArgument(URI uri, String prefixedName);

	SWRLClassBuiltInArgument createClassArgument(OWLClassAdapter cls);

	SWRLIndividualBuiltInArgument createIndividualArgument(URI uri, String prefixedName);

	SWRLIndividualBuiltInArgument createIndividualArgument(OWLIndividualAdapter individual);

	SWRLObjectPropertyBuiltInArgument createObjectPropertyArgument(URI uri, String prefixedName);

	SWRLObjectPropertyBuiltInArgument createObjectPropertyArgument(OWLObjectPropertyAdapter property);

	SWRLDataPropertyBuiltInArgument createDataPropertyArgument(URI uri, String prefixedName);

	SWRLDataPropertyBuiltInArgument createDataPropertyArgument(OWLDataPropertyAdapter property);

	SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyArgument(URI uri, String prefixedName);

	SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyArgument(OWLAnnotationPropertyAdapter property);

	SWRLDatatypeBuiltInArgument createDatatypeArgument(URI uri, String prefixedName);

	SWRLDatatypeBuiltInArgument createDatatypeArgument(OWLDatatypeAdapter datatype);

	SWRLLiteralBuiltInArgument createLiteralArgument(OWLLiteralAdapter literal);

	SWRLLiteralBuiltInArgument createLiteralArgument(String s);

	SWRLLiteralBuiltInArgument createLiteralArgument(boolean b);

	SWRLLiteralBuiltInArgument createLiteralArgument(int i);

	SWRLLiteralBuiltInArgument createLiteralArgument(long l);

	SWRLLiteralBuiltInArgument createLiteralArgument(float f);

	SWRLLiteralBuiltInArgument createLiteralArgument(double d);

	SWRLLiteralBuiltInArgument createLiteralArgument(byte b);

	SWRLLiteralBuiltInArgument createLiteralArgument(URI uri);

	SWRLLiteralBuiltInArgument createLiteralArgument(XSDDate date);

	SWRLLiteralBuiltInArgument createLiteralArgument(XSDTime time);

	SWRLLiteralBuiltInArgument createLiteralArgument(XSDDateTime datetime);

	SWRLLiteralBuiltInArgument createLiteralArgument(XSDDuration duration);

	SWRLMultiArgument createMultiArgument();

	SWRLMultiArgument createMultiArgument(List<SWRLBuiltInArgument> arguments);

	SQWRLCollectionBuiltInArgument createSQWRLCollectionArgument(String queryName, String collectionName,
			String collectionID);
}
