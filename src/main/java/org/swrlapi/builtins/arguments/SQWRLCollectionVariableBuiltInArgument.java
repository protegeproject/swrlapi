package org.swrlapi.builtins.arguments;

/**
 * Interface representing SQWRL collection arguments to SWRL built-ins
 */
public interface SQWRLCollectionVariableBuiltInArgument extends SWRLVariableBuiltInArgument
{
	String getQueryName();

	String getCollectionName();

	String getGroupID();
}
