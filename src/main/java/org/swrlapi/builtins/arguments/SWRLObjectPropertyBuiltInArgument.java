package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Represents an OWL object property argument to a built-in atom.
 */
public interface SWRLObjectPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLObjectProperty getOWLObjectProperty();
}
