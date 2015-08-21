package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public interface SQWRLQueryEngineModel extends SWRLRuleEngineModel
{
  /**
   * Update the model's  query engine
   *
   * @param queryEngine A new SQWRL query engine
   */
  void updateModel(@NonNull SQWRLQueryEngine queryEngine);

  /**
   * @return A SQWRL query engine
   */
  @NonNull SQWRLQueryEngine getSQWRLQueryEngine();
}
