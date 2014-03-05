package org.protege.swrlapi.core.arguments;

/**
 * Interface representing an argument to a SWRL built-in.
 */
public interface SWRLBuiltInArgument extends SWRLAtomArgument
{
	// TODO Why are all these here?
	void setBuiltInResult(SWRLBuiltInArgument builtInResult);

	SWRLBuiltInArgument getBuiltInResult();

	boolean hasBuiltInResult();

	String getVariableName();

	void setVariableName(String variableName);

	boolean isVariable();

	boolean isUnbound();

	boolean isBound();

	void setUnbound();

	void setBound();
}
