package org.swrlapi.builtins.arguments;

import java.util.List;

/**
 * A class used to bind multiple arguments to a SWRL built-in argument.
 */
public interface SWRLMultiValueVariableBuiltInArgument extends SWRLVariableBuiltInArgument
{
	void addArgument(SWRLBuiltInArgument argument);

	void setArguments(List<SWRLBuiltInArgument> arguments);

	List<SWRLBuiltInArgument> getArguments();

	int getNumberOfArguments();

	boolean hasNoArguments();
}
