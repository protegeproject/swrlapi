package org.swrlapi.factory;

import checkers.nullness.quals.KeyFor;
import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;
import org.swrlapi.core.SWRLRuleEngineManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class DefaultSWRLRuleEngineManager implements SWRLRuleEngineManager
{
  @NonNull private final Map<@NonNull String, @NonNull TargetSWRLRuleEngineCreator> registeredSWRLRuleEngines;

  public DefaultSWRLRuleEngineManager()
  {
    this.registeredSWRLRuleEngines = new HashMap<>();
  }

  @Override public void registerRuleEngine(@NonNull TargetSWRLRuleEngineCreator ruleEngineCreator)
  {
    String ruleEngineName = ruleEngineCreator.getRuleEngineName();

    if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName)) {
      this.registeredSWRLRuleEngines.remove(ruleEngineName);
      this.registeredSWRLRuleEngines.put(ruleEngineName, ruleEngineCreator);
    } else
      this.registeredSWRLRuleEngines.put(ruleEngineName, ruleEngineCreator);
  }

  @Override public boolean isRuleEngineRegistered(@NonNull String ruleEngineName)
  {
    return this.registeredSWRLRuleEngines.containsKey(ruleEngineName);
  }

  @Override public boolean hasRegisteredRuleEngines()
  {
    return !this.registeredSWRLRuleEngines.isEmpty();
  }

  @Override public Optional<@NonNull String> getAnyRegisteredRuleEngineName()
  {
    if (hasRegisteredRuleEngines())
      return Optional.of(this.registeredSWRLRuleEngines.keySet().iterator().next());
    else
      return Optional.<@NonNull String>empty();
  }

  @Override public Optional<@NonNull TargetSWRLRuleEngineCreator> getRegisteredRuleEngineCreator(
    @NonNull String ruleEngineName)
  {
    if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName))
      return Optional.of(this.registeredSWRLRuleEngines.get(ruleEngineName));
    else
      return Optional.<@NonNull TargetSWRLRuleEngineCreator>empty();
  }

  @NonNull @Override public Set<@NonNull @KeyFor("this.registeredSWRLRuleEngines") String> getRegisteredRuleEngineNames()
  {
    return this.registeredSWRLRuleEngines.keySet();
  }

  @Override public void unregisterSWRLRuleEngine(@NonNull String ruleEngineName)
  {
    if (this.registeredSWRLRuleEngines.containsKey(ruleEngineName))
      this.registeredSWRLRuleEngines.remove(ruleEngineName);
  }
}
