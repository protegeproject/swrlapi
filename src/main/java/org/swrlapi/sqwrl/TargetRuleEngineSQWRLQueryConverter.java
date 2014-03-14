package org.swrlapi.sqwrl;

import org.swrlapi.converters.TargetRuleEngineConverter;
import org.swrlapi.exceptions.BuiltInException;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Interface for defining a native rule engine representation of a SQWRL query.
 */
public interface TargetRuleEngineSQWRLQueryConverter extends TargetRuleEngineConverter
{
	public void convert(SQWRLQuery query) throws TargetRuleEngineException, BuiltInException;
}
