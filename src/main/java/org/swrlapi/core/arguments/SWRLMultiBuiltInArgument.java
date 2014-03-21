package org.swrlapi.core.arguments;

import java.util.List;

/**
 * A class used to bind multiple arguments to a SWRL built-in argument.
 * <p>
 * See <a href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLBuiltInBridge#nid8LH">here</a> for details.
 */
public interface SWRLMultiBuiltInArgument extends SWRLBuiltInArgument
{
	void addArgument(SWRLBuiltInArgument argument);

	void setArguments(List<SWRLBuiltInArgument> arguments);

	List<SWRLBuiltInArgument> getArguments();

	int getNumberOfArguments();

	boolean hasNoArguments();
}
