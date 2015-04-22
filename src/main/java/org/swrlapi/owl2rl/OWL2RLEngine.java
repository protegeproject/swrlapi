package org.swrlapi.owl2rl;

import java.util.List;
import java.util.Set;

/**
 * The SWRLAPI expects that each implementation of a SWRL rule engine will provide an OWL 2 RL-based reasoner.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 */
public interface OWL2RLEngine extends OWL2RLNames
{
	enum OWL2RLRuleStatus {
		Unsupported, PermanentlyOn, Switchable
	};

	/**
	 * @return True if the rule selection has changed since last call to {@link OWL2RLEngine#resetRuleSelectionChanged()}.
	 */
	boolean hasRuleSelectionChanged();

	/**
	 * Reset rule selection changed status
	 */
	void resetRuleSelectionChanged();

	/**
	 * Set rule selection changed status
	 */
	void setRuleSelectionChanged();

	/**
	 * @return A list of OWL 2 RL rule tables
	 */
	List<OWL2RLRuleTable> getRuleTables();

	/**
	 * @return The number of OWL 2 RL rules
	 */
	int getNumberOfRules();

	/**
	 * @return The number of OWL 2 RL tables
	 */
	int getNumberOfTables();

	/**
	 * @return A list of OWL 2 RL rules
	 */
	List<OWL2RLRule> getRules();

	/**
	 * @param table An OWL 2 RL rule table
	 * @return A list of OWL 2 RL rules
	 */
	List<OWL2RLRule> getRules(OWL2RLRuleTable table);

	/**
	 * @return A list of OWL 2 RL rules
	 */
	Set<OWL2RLRule> getEnabledRules();

	/**
	 * @return A list of OWL 2 RL rules
	 */
	Set<OWL2RLRule> getUnsupportedRules();

	/**
	 * @return A list of OWL 2 RL rules
	 */
	Set<OWL2RLRule> getPermanentlyOnRules();

	/**
	 * @return A list of OWL 2 RL rules
	 */
	Set<OWL2RLRule> getSwitchableRules();

	/**
	 * Enable all OWL 2 RL rules
	 */
	void enableAll();

	/**
	 * Disable all OWL 2 RL rules
	 */
	void disableAll();

	/**
	 * Enable selected OWL 2 RL rule tables
	 * 
	 * @param tables A list of OWL 2 RL tables
	 */
	void enableTables(OWL2RLRuleTable... tables);

	/**
	 * Disable selected OWL 2 RL rule tables
	 * 
	 * @param table An OWL 2 RL table
	 */
	void disableTables(OWL2RLRuleTable... table);

	/**
	 * Enable selected OWL 2 RL rules
	 * 
	 * @param rules A collection of OWL 2 RL rules
	 */
	void enableRules(OWL2RLRule... rules);

	/**
	 * Disable selected OWL 2 RL rules
	 * 
	 * @param rules A collection of OWL 2 RL rules
	 */
	void disableRules(OWL2RLRule... rules);

	/**
	 * @param table A collection of OWL 2 RL rule tables
	 * @return True if the specified table has enabled rules
	 */
	boolean hasEnabledRules(OWL2RLRuleTable table);

	/**
	 * @param table A collection of OWL 2 RL rule tables
	 * @return True if the specified table has switchable rules
	 */
	boolean hasSwitchableRules(OWL2RLRuleTable table);

	/**
	 * @param rule An OWL 2 RL rule
	 * @return True if the rule is enables
	 */
	boolean isRuleEnabled(OWL2RLRule rule);

	/**
	 * @param rule An OWL 2 RL rule
	 * @return The status of the rule
	 */
	OWL2RLRuleStatus getRuleStatus(OWL2RLRule rule);

	/**
	 * @return An OWL 2 RL persistence layer
	 */
	OWL2RLPersistenceLayer getOWL2RLPersistenceLayer();
}
