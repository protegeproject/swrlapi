package org.swrlapi.core.arguments;

/**
 * Interface representing SQWRL collection arguments to SWRL built-ins
 */
public interface SQWRLCollectionBuiltInArgument extends SWRLBuiltInArgument
{
	String getQueryName();

	String getCollectionName();

	String getGroupID();
}
