package org.swrlapi.builtins.arguments;

/**
 * Interface representing SQWRL collection arguments to SWRL built-ins
 */
public interface SQWRLCollectionVariableBuiltInArgument extends SWRLVariableBuiltInArgument
{
	/**
	 * @return The name of the enclosing SQWRL query
	 */
	String getQueryName();

	/**
	 * @return The collection name
	 */
	String getCollectionName();

	/**
	 * @return The collection group ID
	 */
	String getGroupID();
}
