package org.swrlapi.sqwrl;

import org.swrlapi.bridge.converters.TargetRuleEngineConverter;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Interface for defining a native rule engine representation of a SQWRL query.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public interface TargetRuleEngineSQWRLQueryConverter extends TargetRuleEngineConverter
{
  /**
   * @param query A SQWRL query
   * @throws TargetSWRLRuleEngineException If an error occurs in the target rule engine
   * @throws SWRLBuiltInException If an error occurs during built-in processing
   */
  void convert(SQWRLQuery query) throws TargetSWRLRuleEngineException;
}
