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
 * Factory for creating {@link SWRLBuiltInArgument} objects.
 * 
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface SWRLBuiltInArgumentFactory
{
	/**
	 * @param variableIRI The IRI of a SWRL variable
	 * @return
	 */
	SWRLVariableBuiltInArgument getVariableBuiltInArgument(IRI variableIRI);

	/**
	 * @param variableIRI The IRI of a SWRL variable
	 * @return
	 */
	SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(IRI variableIRI);

	/**
	 * @param cls
	 * @return
	 */
	SWRLClassBuiltInArgument getClassBuiltInArgument(OWLClass cls);

	/**
	 * @param individual
	 * @return
	 */
	SWRLNamedIndividualBuiltInArgument getNamedIndividualBuiltInArgument(OWLNamedIndividual individual);

	/**
	 * @param property
	 * @return
	 */
	SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(OWLObjectProperty property);

	/**
	 * @param property
	 * @return
	 */
	SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(OWLDataProperty property);

	/**
	 * @param property
	 * @return
	 */
	SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property);

	/**
	 * @param datatype
	 * @return
	 */
	SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(OWLDatatype datatype);

	/**
	 * @param literal
	 * @return
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(OWLLiteral literal);

	/**
	 * @param s
	 * @return
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(String s);

	/**
	 * @param b A boolean
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(boolean b);

	/**
	 * @param s A String
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(short s);

	/**
	 * @param i An int
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(int i);

	/**
	 * @param l A long
	 * @return
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(long l);

	/**
	 * @param f A float
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(float f);

	/**
	 * @param d A double
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(double d);

	/**
	 * @param b A byte
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(byte b);

	/**
	 * @param iri An IRI
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(URI iri);

	/**
	 * @param date A date
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDate date);

	/**
	 * @param time A time
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDTime time);

	/**
	 * @param datetime A datetime
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDateTime datetime);

	/**
	 * @param duration A duration
	 * @return A literal built-in argument
	 */
	SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDuration duration);

	/**
	 * @param variableIRI The IRI of a SWRL variable
	 * @return A built-in argument
	 */
	SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI);

	/**
	 * @param variableIRI The IRI of a SWRL variable
	 * @param arguments Built-in arguments
	 * @return A built-in argument
	 */
	SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI,
			List<SWRLBuiltInArgument> arguments);

	/**
	 * @param variableIRI The IRI of a SWRL variable
	 * @param queryName The name of the query containing the collection
	 * @param collectionName The name of the collection
	 * @param collectionID The collection ID
	 * @return A built-in argument
	 */
	SQWRLCollectionVariableBuiltInArgument getSQWRLCollectionVariableBuiltInArgument(IRI variableIRI, String queryName,
			String collectionName, String collectionID);
}
