package org.protege.swrlapi.core.arguments;

/**
 * Interface representing an argument to a SWRL built-in.
 */
public interface SWRLBuiltInArgument extends SWRLDAtomArgument
{
	// TODO These methods should really be in SWRLVariableBuiltInArgument
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

	@Override
	String toDisplayText();
}
