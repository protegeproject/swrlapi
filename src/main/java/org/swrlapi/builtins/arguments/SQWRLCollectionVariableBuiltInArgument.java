package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;

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
