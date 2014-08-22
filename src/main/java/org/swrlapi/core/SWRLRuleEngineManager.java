package org.swrlapi.core;

import java.util.Set;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface defines a manager to create and manage instances of SWRL rule engines.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 */
public interface SWRLRuleEngineManager
{
	boolean hasRegisteredRuleEngines();

	String getAnyRegisteredRuleEngineName();

	void registerRuleEngine(String ruleEngineName, TargetSWRLRuleEngineCreator ruleEngineCreator);

	boolean isRuleEngineRegistered(String ruleEngineName);

	Set<String> getRegisteredRuleEngineNames();

	TargetSWRLRuleEngineCreator getRegisteredRuleEngineCreator(String ruleEngineName);

	void unregisterSWRLRuleEngine(String ruleEngineName);

	public interface TargetSWRLRuleEngineCreator
	{
		TargetSWRLRuleEngine create(SWRLRuleEngineBridge bridge) throws TargetRuleEngineException;

		String getRuleEngineName();
	}
}
