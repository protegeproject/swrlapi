package org.swrlapi.core;

import java.util.Set;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This interface defines a manager to create and manage instances of SWRL rule engines.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 */
public interface SWRLRuleEngineManager
{
	/**
	 * @return True if at least one rule engine is registered
	 */
	boolean hasRegisteredRuleEngines();

	/**
	 * @return Any registered rule engine name; an empty string if no engines are registered
	 */
	String getAnyRegisteredRuleEngineName();

	/**
	 * @param ruleEngineName The name of the rule engine
	 * @param ruleEngineCreator A creator for the rule engine
	 */
	void registerRuleEngine(String ruleEngineName, TargetSWRLRuleEngineCreator ruleEngineCreator);

	/**
	 * @param ruleEngineName A rule engine name
	 * @return True if an engine with the specified name is registered
	 */
	boolean isRuleEngineRegistered(String ruleEngineName);

	/**
	 * @return A list of registered rule engine names
	 */
	Set<String> getRegisteredRuleEngineNames();

	/**
	 * @param ruleEngineName A rule engine name
	 * @return A creator for the specified rule engine; null if it is not registered
	 */
	TargetSWRLRuleEngineCreator getRegisteredRuleEngineCreator(String ruleEngineName);

	/**
	 * @param ruleEngineName A rule engine name
	 */
	void unregisterSWRLRuleEngine(String ruleEngineName);

	/**
	 * A creator for a SWRL rule engine
	 */
	public interface TargetSWRLRuleEngineCreator
	{
		/**
		 * @param bridge A SWRL rule engine bridge associated with the engine
		 * @return A target SWRL rule engine
		 * @throws TargetSWRLRuleEngineException If an exception occurs during creation
		 */
		TargetSWRLRuleEngine create(SWRLRuleEngineBridge bridge) throws TargetSWRLRuleEngineException;

		/**
		 * @return The name of the target rule engine
		 */
		String getRuleEngineName();
	}
}
