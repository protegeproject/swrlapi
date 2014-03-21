package org.swrlapi.core.arguments;

import java.net.URI;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

/**
 * Factory for creating SWRLAPI {@link SWRLAtomArgument} objects.
 * 
 * 
 * @author martin
 */
public interface SWRLAtomArgumentFactory
{
	SWRLVariableAtomArgument getVariableAtomArgument(String variableName);

	SWRLClassAtomArgument getClassAtomArgument(IRI uri);

	SWRLClassAtomArgument getClassAtomArgument(OWLClass cls);

	SWRLNamedIndividualAtomArgument getNamedIndividualAtomArgument(IRI uri);

	SWRLNamedIndividualAtomArgument getNamedIndividualAtomArgument(OWLIndividual individual);

	SWRLObjectPropertyAtomArgument getObjectPropertyAtomArgument(IRI iri);

	SWRLObjectPropertyAtomArgument getObjectPropertyAtomArgument(OWLObjectProperty property);

	SWRLDataPropertyAtomArgument getDataPropertyAtomArgument(IRI iri);

	SWRLDataPropertyAtomArgument getDataPropertyAtomArgument(OWLDataProperty property);

	SWRLAnnotationPropertyAtomArgument getAnnotationPropertyAtomArgument(IRI uri);

	SWRLAnnotationPropertyAtomArgument getAnnotationPropertyAtomArgument(OWLAnnotationProperty property);

	SWRLLiteralAtomArgument getLiteralAtomArgument(OWLLiteral literal);

	SWRLLiteralAtomArgument getLiteralAtomArgument(String s);

	SWRLLiteralAtomArgument getLiteralAtomArgument(boolean b);

	SWRLLiteralAtomArgument getLiteralAtomArgument(byte b);

	SWRLLiteralAtomArgument getLiteralAtomArgument(short s);

	SWRLLiteralAtomArgument getLiteralAtomArgument(URI uri);

	SWRLLiteralAtomArgument getLiteralAtomArgument(int i);

	SWRLLiteralAtomArgument getLiteralAtomArgument(long l);

	SWRLLiteralAtomArgument getLiteralAtomArgument(float f);

	SWRLLiteralAtomArgument getLiteralAtomArgument(double d);

	SWRLLiteralAtomArgument getLiteralAtomArgument(XSDDate date);

	SWRLLiteralAtomArgument getLiteralAtomArgument(XSDDateTime date);

	SWRLLiteralAtomArgument getLiteralAtomArgument(XSDTime date);

	SWRLLiteralAtomArgument getLiteralAtomArgument(XSDDuration date);
}
