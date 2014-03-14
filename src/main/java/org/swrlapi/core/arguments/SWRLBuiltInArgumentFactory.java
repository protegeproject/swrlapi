package org.swrlapi.core.arguments;

import java.net.URI;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public interface SWRLBuiltInArgumentFactory
{
	SWRLVariableBuiltInArgument getVariableArgument(String variableName);

	SWRLVariableBuiltInArgument getUnboundVariableArgument(String variableName);

	SWRLClassBuiltInArgument getClassArgument(IRI uri);

	SWRLClassBuiltInArgument getClassArgument(OWLClass cls);

	SWRLIndividualBuiltInArgument getIndividualArgument(IRI uri);

	SWRLIndividualBuiltInArgument getIndividualArgument(OWLIndividual individual);

	SWRLObjectPropertyBuiltInArgument getObjectPropertyArgument(IRI uri);

	SWRLObjectPropertyBuiltInArgument getObjectPropertyArgument(OWLObjectProperty property);

	SWRLDataPropertyBuiltInArgument getDataPropertyArgument(IRI uri);

	SWRLDataPropertyBuiltInArgument getDataPropertyArgument(OWLDataProperty property);

	SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyArgument(IRI uri);

	SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyArgument(OWLAnnotationProperty property);

	SWRLDatatypeBuiltInArgument getDatatypeArgument(IRI uri);

	SWRLDatatypeBuiltInArgument getDatatypeArgument(OWLDatatype datatype);

	SWRLLiteralBuiltInArgument getLiteralArgument(OWLLiteral literal);

	SWRLLiteralBuiltInArgument getLiteralArgument(String s);

	SWRLLiteralBuiltInArgument getLiteralArgument(boolean b);

	SWRLLiteralBuiltInArgument getLiteralArgument(int i);

	SWRLLiteralBuiltInArgument getLiteralArgument(long l);

	SWRLLiteralBuiltInArgument getLiteralArgument(float f);

	SWRLLiteralBuiltInArgument getLiteralArgument(double d);

	SWRLLiteralBuiltInArgument getLiteralArgument(byte b);

	SWRLLiteralBuiltInArgument getLiteralArgument(URI iri);

	SWRLLiteralBuiltInArgument getLiteralArgument(XSDDate date);

	SWRLLiteralBuiltInArgument getLiteralArgument(XSDTime time);

	SWRLLiteralBuiltInArgument getLiteralArgument(XSDDateTime datetime);

	SWRLLiteralBuiltInArgument getLiteralArgument(XSDDuration duration);

	SWRLMultiArgument getMultiArgument();

	SWRLMultiArgument getMultiArgument(List<SWRLBuiltInArgument> arguments);

	SQWRLCollectionBuiltInArgument getSQWRLCollectionArgument(String queryName, String collectionName,
			String collectionID);
}
