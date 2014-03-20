package org.swrlapi.core.arguments;

/**
 * Represents a variable argument to a non built-in atom. Distinct from a {@link SWRLVariableBuiltInArgument}, which
 * represents a variable argument to a built-in.
 */
public interface SWRLVariableAtomArgument extends SWRLAtomArgument
{
	String getVariableName();
}
