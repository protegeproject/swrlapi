package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public interface SWRLAtomArgumentFactory
{
	SWRLVariableAtomArgument createVariableArgument(String variableName);

	SWRLClassAtomArgument createClassArgument(IRI uri);

	SWRLClassAtomArgument createClassArgument(OWLClass cls);

	SWRLIndividualAtomArgument createIndividualArgument(IRI uri);

	SWRLIndividualAtomArgument createIndividualArgument(OWLIndividual individual);

	SWRLObjectPropertyAtomArgument createObjectPropertyArgument(IRI iri);

	SWRLObjectPropertyAtomArgument createObjectPropertyArgument(OWLObjectProperty property);

	SWRLDataPropertyAtomArgument createDataPropertyArgument(IRI iri);

	SWRLDataPropertyAtomArgument createDataPropertyArgument(OWLDataProperty property);

	SWRLLiteralAtomArgument createLiteralArgument(OWLLiteral literal);

	SWRLLiteralAtomArgument createLiteralArgument(String s);

	SWRLLiteralAtomArgument createLiteralArgument(boolean b);

	SWRLLiteralAtomArgument createLiteralArgument(int i);

	SWRLLiteralAtomArgument createLiteralArgument(long l);

	SWRLLiteralAtomArgument createLiteralArgument(float f);

	SWRLLiteralAtomArgument createLiteralArgument(double d);
}
