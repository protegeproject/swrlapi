package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public interface SQWRLQueryEngineModel extends SWRLRuleEngineModel
{
  /**
   * @return A SQWRL query engine
   */
  @NonNull SQWRLQueryEngine getSQWRLQueryEngine();
}
