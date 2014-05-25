package org.swrlapi.core;

import java.util.Set;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetRuleEngine;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface defines a manager to create and manage instances of SWRL rule engines.
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
		TargetRuleEngine create(SWRLRuleEngineBridge bridge) throws TargetRuleEngineException;

		String getRuleEngineName();
	}
}
