package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * Represents an OWL data property argument to a built-in atom.
 *
 * @see org.semanticweb.owlapi.model.OWLDataProperty
 */
public interface SWRLDataPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLDataProperty getOWLDataProperty();
}
