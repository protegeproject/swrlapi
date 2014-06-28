package org.swrlapi.builtins;

import java.net.URI;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.BuiltInException;

/**
 * Methods that can be used by built-ins to create result arguments.
 */
public interface SWRLBuiltInArgumentCreator
{
	SWRLClassBuiltInArgument createClassBuiltInArgument(OWLClass cls) throws BuiltInException;

	SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(OWLNamedIndividual individual)
			throws BuiltInException;

	SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(OWLObjectProperty property)
			throws BuiltInException;

	SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(OWLDataProperty property) throws BuiltInException;

	SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
			throws BuiltInException;

	SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(OWLDatatype datatype) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(String s) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(Byte b) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime dateTime) throws BuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration) throws BuiltInException;

	SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI)
			throws BuiltInException;

	SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI,
			List<SWRLBuiltInArgument> arguments) throws BuiltInException;
}
