package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.OWLClass;

/**
 * Interface representing OWL class arguments to SWRL built-ins
 */
public interface SWRLClassBuiltInArgument extends SWRLNamedBuiltInArgument
{
	OWLClass getOWLClass();
}
