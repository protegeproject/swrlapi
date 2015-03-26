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
import org.swrlapi.exceptions.SWRLBuiltInException;

/**
 * Utility methods that can be used by built-ins to create SWRL built-in result arguments.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 */
public interface SWRLBuiltInArgumentCreator
{
	SWRLClassBuiltInArgument createClassBuiltInArgument(OWLClass cls) throws SWRLBuiltInException;

	SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(OWLNamedIndividual individual)
			throws SWRLBuiltInException;

	SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(OWLObjectProperty property)
			throws SWRLBuiltInException;

	SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(OWLDataProperty property)
			throws SWRLBuiltInException;

	SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
			throws SWRLBuiltInException;

	SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(OWLDatatype datatype) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(String s) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(byte b) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime dateTime) throws SWRLBuiltInException;

	SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration) throws SWRLBuiltInException;

	SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI)
			throws SWRLBuiltInException;

	SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI,
			List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;
}
