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
	SWRLVariableBuiltInArgument getVariableBuiltInArgument(String variableName);

	SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(String variableName);

	SWRLClassBuiltInArgument getClassBuiltInArgument(IRI uri);

	SWRLClassBuiltInArgument getClassBuiltInArgument(OWLClass cls);

	SWRLIndividualBuiltInArgument getIndividualBuiltInArgument(IRI uri);

	SWRLIndividualBuiltInArgument getIndividualBuiltInArgument(OWLIndividual individual);

	SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(IRI uri);

	SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(OWLObjectProperty property);

	SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(IRI uri);

	SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(OWLDataProperty property);

	SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(IRI uri);

	SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property);

	SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(IRI uri);

	SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(OWLDatatype datatype);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(OWLLiteral literal);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(String s);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(boolean b);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(int i);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(long l);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(float f);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(double d);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(byte b);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(URI iri);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDate date);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDTime time);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDateTime datetime);

	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDuration duration);

	SWRLMultiArgument getMultiArgument();

	SWRLMultiArgument getMultiArgument(List<SWRLBuiltInArgument> arguments);

	SQWRLCollectionBuiltInArgument getSQWRLCollectionArgument(String queryName, String collectionName, String collectionID);
}
