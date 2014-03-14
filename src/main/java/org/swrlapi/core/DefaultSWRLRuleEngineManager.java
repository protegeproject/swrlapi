
package org.swrlapi.core;

import java.util.HashMap;
import java.util.Set;

public class DefaultSWRLRuleEngineManager implements SWRLRuleEngineManager
{
	private final HashMap<String, TargetSWRLRuleEngineCreator> registeredSWRLRuleEngines;

	public DefaultSWRLRuleEngineManager()
	{
		this.registeredSWRLRuleEngines = new HashMap<String, TargetSWRLRuleEngineCreator>();
	}

	public void registerRuleEngine(String ruleEngineName, TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName)) {
			this.registeredSWRLRuleEngines.remove(ruleEngineName);
			this.registeredSWRLRuleEngines.put(ruleEngineName, ruleEngineCreator);
		} else
			this.registeredSWRLRuleEngines.put(ruleEngineName, ruleEngineCreator);
	}

	public boolean isRuleEngineRegistered(String ruleEngineName)
	{
		return this.registeredSWRLRuleEngines.containsKey(ruleEngineName);
	}

	public boolean hasRegisteredRuleEngines()
	{
		return !this.registeredSWRLRuleEngines.isEmpty();
	}

	public String getAnyRegisteredRuleEngineName()
	{
		if (hasRegisteredRuleEngines())
			return this.registeredSWRLRuleEngines.keySet().iterator().next();
		else
			return null;
	}

	public SWRLRuleEngineManager.TargetSWRLRuleEngineCreator getRegisteredRuleEngineCreator(String ruleEngineName)
	{
		return this.registeredSWRLRuleEngines.get(ruleEngineName);
	}

	public Set<String> getRegisteredRuleEngineNames()
	{
		return this.registeredSWRLRuleEngines.keySet();
	}

	public void unregisterSWRLRuleEngine(String ruleEngineName)
	{
		if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName))
			this.registeredSWRLRuleEngines.remove(ruleEngineName);
	}
}
