package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;

/**
 * Interface representing SQWRL collection arguments to SWRL built-ins
 */
public interface SQWRLCollectionVariableBuiltInArgument extends SWRLVariableBuiltInArgument
{
  /**
   * @return The name of the enclosing SQWRL query
   */
  @NonNull String getQueryName();

  /**
   * @return The collection name
   */
  @NonNull String getCollectionName();

  /**
   * @return The collection group ID
   */
  @NonNull String getGroupID();
}
