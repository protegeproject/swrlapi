package org.protege.swrlapi.sqwrl;

import org.protege.swrlapi.converters.TargetRuleEngineConverter;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Interface for defining a native rule engine representation of a SQWRL query.
 */
public interface TargetRuleEngineSQWRLQueryConverter extends TargetRuleEngineConverter
{
	public void convert(SQWRLQuery query) throws TargetRuleEngineException, BuiltInException;
}
