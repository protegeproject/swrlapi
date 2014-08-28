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
	public void convert(SQWRLQuery query) throws TargetSWRLRuleEngineException, SWRLBuiltInException;
}
