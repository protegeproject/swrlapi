package org.swrlapi.core.arguments;

import java.net.URI;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public interface SWRLAtomArgumentFactory
{
	SWRLVariableAtomArgument getVariableArgument(String variableName);

	SWRLClassAtomArgument getClassArgument(IRI uri);

	SWRLClassAtomArgument getClassArgument(OWLClass cls);

	SWRLIndividualAtomArgument getIndividualArgument(IRI uri);

	SWRLIndividualAtomArgument getIndividualArgument(OWLIndividual individual);

	SWRLObjectPropertyAtomArgument getObjectPropertyArgument(IRI iri);

	SWRLObjectPropertyAtomArgument getObjectPropertyArgument(OWLObjectProperty property);

	SWRLDataPropertyAtomArgument getDataPropertyArgument(IRI iri);

	SWRLDataPropertyAtomArgument getDataPropertyArgument(OWLDataProperty property);

	SWRLLiteralAtomArgument getLiteralArgument(OWLLiteral literal);

	SWRLLiteralAtomArgument getLiteralArgument(String s);

	SWRLLiteralAtomArgument getLiteralArgument(boolean b);

	SWRLLiteralAtomArgument getLiteralArgument(byte b);

	SWRLLiteralAtomArgument getLiteralArgument(short s);

	SWRLLiteralAtomArgument getLiteralArgument(URI uri);

	SWRLLiteralAtomArgument getLiteralArgument(int i);

	SWRLLiteralAtomArgument getLiteralArgument(long l);

	SWRLLiteralAtomArgument getLiteralArgument(float f);

	SWRLLiteralAtomArgument getLiteralArgument(double d);

	SWRLLiteralAtomArgument getLiteralArgument(XSDDate date);

	SWRLLiteralAtomArgument getLiteralArgument(XSDDateTime date);

	SWRLLiteralAtomArgument getLiteralArgument(XSDTime date);

	SWRLLiteralAtomArgument getLiteralArgument(XSDDuration date);
}
