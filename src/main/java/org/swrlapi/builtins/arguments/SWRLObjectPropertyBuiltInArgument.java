package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Represents an OWL object property argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectProperty
 */
public interface SWRLObjectPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLObjectProperty getOWLObjectProperty();
}
