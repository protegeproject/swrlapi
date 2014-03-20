package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.SWRLLiteralArgument;

/**
 * Represents an OWL literal argument to a built-in atom. Distinct from a {@link SWRLLiteralAtomArgument}, which
 * represents an OWL literal argument to a non built-in atom.
 */
public interface SWRLLiteralBuiltInArgument extends SWRLBuiltInArgument, SWRLLiteralArgument
{
}