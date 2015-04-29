package org.swrlapi.core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.swrlapi.core.SWRLRuleEngineManager;

public class DefaultSWRLRuleEngineManager implements SWRLRuleEngineManager
{
	private final Map<String, TargetSWRLRuleEngineCreator> registeredSWRLRuleEngines;

	public DefaultSWRLRuleEngineManager()
	{
		this.registeredSWRLRuleEngines = new HashMap<>();
	}

	@Override
	public void registerRuleEngine(TargetSWRLRuleEngineCreator ruleEngineCreator)
	{
		String ruleEngineName = ruleEngineCreator.getRuleEngineName();

		if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName)) {
			this.registeredSWRLRuleEngines.remove(ruleEngineName);
			this.registeredSWRLRuleEngines.put(ruleEngineName, ruleEngineCreator);
		} else
			this.registeredSWRLRuleEngines.put(ruleEngineName, ruleEngineCreator);
	}

	@Override
	public boolean isRuleEngineRegistered(String ruleEngineName)
	{
		return this.registeredSWRLRuleEngines.containsKey(ruleEngineName);
	}

	@Override
	public boolean hasRegisteredRuleEngines()
	{
		return !this.registeredSWRLRuleEngines.isEmpty();
	}

	@Override
	public Optional<String> getAnyRegisteredRuleEngineName()
	{
		if (hasRegisteredRuleEngines())
			return Optional.of(this.registeredSWRLRuleEngines.keySet().iterator().next());
		else
			return Optional.empty();
	}

	@Override
	public SWRLRuleEngineManager.TargetSWRLRuleEngineCreator getRegisteredRuleEngineCreator(String ruleEngineName)
	{
		return this.registeredSWRLRuleEngines.get(ruleEngineName);
	}

	@Override
	public Set<String> getRegisteredRuleEngineNames()
	{
		return this.registeredSWRLRuleEngines.keySet();
	}

	@Override
	public void unregisterSWRLRuleEngine(String ruleEngineName)
	{
		if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName))
			this.registeredSWRLRuleEngines.remove(ruleEngineName);
	}
}
