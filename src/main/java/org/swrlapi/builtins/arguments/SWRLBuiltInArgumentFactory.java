package org.swrlapi.builtins.arguments;

import java.net.URI;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

/**
 * Factory for creating SWRLAPI {@link SWRLBuiltInArgument} objects.
 * 
 * @see SWRLBuiltInArgument
 */
public interface SWRLBuiltInArgumentFactory
{
	SWRLVariableBuiltInArgument getVariableBuiltInArgument(IRI variableIRI);

	SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(IRI variableIRI);

	SWRLClassBuiltInArgument getClassBuiltInArgument(OWLClass cls);

	SWRLNamedIndividualBuiltInArgument getNamedIndividualBuiltInArgument(OWLNamedIndividual individual);

	SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(OWLObjectProperty property);

	SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(OWLDataProperty property);

	SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property);

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

	SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI);

	SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI,
			List<SWRLBuiltInArgument> arguments);

	SQWRLCollectionVariableBuiltInArgument getSQWRLCollectionVariableBuiltInArgument(IRI variableIRI, String queryName,
			String collectionName, String collectionID);
}
