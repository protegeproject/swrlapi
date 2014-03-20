package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.SWRLLiteralArgument;

/**
 * Represents an OWL literal argument to a non built-in atom. Distinct from a {@link SWRLLiteralBuiltInArgument}, which
 * represents an OWL literal argument to a built-in atom.
 */
public interface SWRLLiteralAtomArgument extends SWRLAtomArgument, SWRLLiteralArgument
{
}
