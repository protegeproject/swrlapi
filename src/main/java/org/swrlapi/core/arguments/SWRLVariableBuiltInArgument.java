package org.swrlapi.core.arguments;

/**
 * Represents a variable argument to a built-in atom. Distinct from a {@link SWRLVariableAtomArgument}, which represents
 * a variable argument to a non built-in argument.
 */
public interface SWRLVariableBuiltInArgument extends SWRLBuiltInArgument
{
	@Override
	String getVariableName();
}
