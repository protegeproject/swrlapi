package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.OWLClass;

/**
 * Interface representing OWL class arguments to SWRL built-ins
 *
 * @see org.semanticweb.owlapi.model.OWLClass
 */
public interface SWRLClassBuiltInArgument extends SWRLNamedBuiltInArgument
{
	/**
	 * @return An OWL class
	 */
	OWLClass getOWLClass();
}
