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
	enum RuleStatus {
		Unsupported, PermanentlyOn, Switchable
	};

	/**
	 * @return True if the rule selection has changed since last call to {@link OWL2RLEngine#resetRuleSelectionChanged()}.
	 */
	boolean hasRuleSelectionChanged();

	void resetRuleSelectionChanged();

	void setRuleSelectionChanged();

	List<RuleTable> getRuleTables();

	int getNumberOfRules();

	int getNumberOfTables();

	List<Rule> getRules();

	List<Rule> getRules(RuleTable table);

	Set<Rule> getEnabledRules();

	Set<Rule> getUnsupportedRules();

	Set<Rule> getPermanentlyOnRules();

	Set<Rule> getSwitchableRules();

	void enableAll();

	void disableAll();

	void enableTables(RuleTable... tables);

	void disableTables(RuleTable... table);

	void enableRules(Rule... rules);

	void disableRules(Rule... rules);

	boolean hasEnabledRules(RuleTable table);

	boolean hasSwitchableRules(RuleTable table);

	boolean isRuleEnabled(Rule rule);

	RuleStatus getRuleStatus(Rule rule);

	OWL2RLPersistenceLayer getOWL2RLPersistenceLayer();
}
