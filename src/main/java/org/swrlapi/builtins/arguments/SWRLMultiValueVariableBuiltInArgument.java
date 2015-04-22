package org.swrlapi.builtins.arguments;

import java.util.List;

/**
 * A class used to bind multiple arguments to a SWRL built-in argument.
 */
public interface SWRLMultiValueVariableBuiltInArgument extends SWRLVariableBuiltInArgument
{
	/**
	 * @return A list of SWRL built-in arguments
	 */
	List<SWRLBuiltInArgument> getArguments();

	/**
	 * @return The number of arguments
	 */
	int getNumberOfArguments();

	/**
	 * @return True of there are no arguments
	 */
	boolean hasNoArguments();

	/**
	 * @param argument A SWRL built-in argument
	 */
	void addArgument(SWRLBuiltInArgument argument);

	/**
	 * @param arguments A list of SWRL built-in arguments
	 */
	void setArguments(List<SWRLBuiltInArgument> arguments);
}
