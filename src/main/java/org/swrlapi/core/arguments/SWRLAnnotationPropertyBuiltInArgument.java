package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;

/**
 * Represents an OWL annotation property argument to a SWRL built-in atom.
 */
public interface SWRLAnnotationPropertyBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLAnnotationProperty getOWLAnnotationProperty();
}
