package org.swrlapi.ui.model;

import org.checkerframework.checker.nullness.qual.NonNull;
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
