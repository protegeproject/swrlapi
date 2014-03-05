package org.protege.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.SWRLArgument;

/**
 * The SWRLAPI's base interface representing arguments to atoms. It extends the OWLAPI's SWRLArgument interface and
 * specializes it.
 */
public interface SWRLAtomArgument extends SWRLArgument
{
	String toDisplayText();
}
