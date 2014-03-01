package org.protege.swrlapi.core.arguments;

import java.net.URI;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public interface SWRLAtomArgumentFactory
{
	SWRLVariableAtomArgument createVariableArgument(String variableName);

	SWRLClassAtomArgument createClassArgument(URI uri, String prefixedName);

	SWRLClassAtomArgument createClassArgument(OWLClass cls);

	SWRLIndividualAtomArgument createIndividualArgument(URI uri, String prefixedName);

	SWRLIndividualAtomArgument createIndividualArgument(OWLIndividual individual);

	SWRLObjectPropertyAtomArgument createObjectPropertyArgument(URI uri, String prefixedName);

	SWRLObjectPropertyAtomArgument createObjectPropertyArgument(OWLObjectProperty property);

	SWRLDataPropertyAtomArgument createDataPropertyArgument(URI uri, String prefixedName);

	SWRLDataPropertyAtomArgument createDataPropertyArgument(OWLDataProperty property);

	SWRLLiteralAtomArgument createLiteralArgument(OWLLiteral literal);

	SWRLLiteralAtomArgument createLiteralArgument(String s);

	SWRLLiteralAtomArgument createLiteralArgument(boolean b);

	SWRLLiteralAtomArgument createLiteralArgument(int i);

	SWRLLiteralAtomArgument createLiteralArgument(long l);

	SWRLLiteralAtomArgument createLiteralArgument(float f);

	SWRLLiteralAtomArgument createLiteralArgument(double d);
}
