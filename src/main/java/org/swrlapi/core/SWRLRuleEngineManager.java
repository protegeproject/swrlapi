package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;

import java.util.Optional;
import java.util.Set;

/**
 * This interface defines a manager to create and manage instances of SWRL rule engines.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 * @see TargetSWRLRuleEngineCreator
 */
public interface SWRLRuleEngineManager
{
  /**
   * @return True if at least one rule engine is registered
   */
  boolean hasRegisteredRuleEngines();

  /**
   * @return Any registered rule engine name - if any.
   */
  Optional<String> getAnyRegisteredRuleEngineName();

  /**
   * @param ruleEngineCreator A creator for the rule engine
   */
  void registerRuleEngine(@NonNull TargetSWRLRuleEngineCreator ruleEngineCreator);

  /**
   * @param ruleEngineName A rule engine name
   * @return True if an engine with the specified name is registered
   */
  boolean isRuleEngineRegistered(@NonNull String ruleEngineName);

  /**
   * @return A list of registered rule engine names
   */
  @NonNull Set<String> getRegisteredRuleEngineNames();

  /**
   * @param ruleEngineName A rule engine name
   * @return A creator for the specified rule engine; null if it is not registered
   */
  Optional<TargetSWRLRuleEngineCreator> getRegisteredRuleEngineCreator(@NonNull String ruleEngineName);

  /**
   * @param ruleEngineName A rule engine name
   */
  void unregisterSWRLRuleEngine(@NonNull String ruleEngineName);
}
