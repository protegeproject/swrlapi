package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Represents an OWL named individual argument to a built-in atom.
 */
public interface SWRLNamedIndividualBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLNamedIndividual getOWLNamedIndividual();
}
