package org.protege.swrlapi.core.arguments;

import java.net.URI;

import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.owl.portability.model.OWLIndividualAdapter;
import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyAdapter;

public interface SWRLAtomArgumentFactory
{
	SWRLVariableAtomArgument createVariableArgument(String variableName);

	SWRLClassAtomArgument createClassArgument(URI uri, String prefixedName);

	SWRLClassAtomArgument createClassArgument(OWLClassAdapter cls);

	SWRLIndividualAtomArgument createIndividualArgument(URI uri, String prefixedName);

	SWRLIndividualAtomArgument createIndividualArgument(OWLIndividualAdapter individual);

	SWRLObjectPropertyAtomArgument createObjectPropertyArgument(URI uri, String prefixedName);

	SWRLObjectPropertyAtomArgument createObjectPropertyArgument(OWLObjectPropertyAdapter property);

	SWRLDataPropertyAtomArgument createDataPropertyArgument(URI uri, String prefixedName);

	SWRLDataPropertyAtomArgument createDataPropertyArgument(OWLDataPropertyAdapter property);

	SWRLLiteralAtomArgument createLiteralArgument(OWLLiteralAdapter literal);

	SWRLLiteralAtomArgument createLiteralArgument(String s);

	SWRLLiteralAtomArgument createLiteralArgument(boolean b);

	SWRLLiteralAtomArgument createLiteralArgument(int i);

	SWRLLiteralAtomArgument createLiteralArgument(long l);

	SWRLLiteralAtomArgument createLiteralArgument(float f);

	SWRLLiteralAtomArgument createLiteralArgument(double d);
}
