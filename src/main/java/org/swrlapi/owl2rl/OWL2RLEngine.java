package org.swrlapi.owl2rl;

import java.util.List;
import java.util.Set;

/**
 * The SWRLAPI uses an OWL 2 RL-based reasoner. Each back end for a particular rule engine will provide an
 * implementation of such a reasoner. This interface can be used to determine the level of support provided by a
 * particular rule engine and to control the set of enabled rules.
 */
public interface OWL2RLEngine extends OWL2RLNames
{
	enum RuleStatus {
		Unsupported, PermanentlyOn, Switchable
	};

	/**
	 * See if the rule selection has changed since last call to {@link OWL2RLEngine#resetRuleSelectionChanged()).
	 */
	boolean hasRuleSelectionChanged();

	void resetRuleSelectionChanged();

	void setRuleSelectionChanged();

	List<Table> getTables();

	int getNumberOfRules();

	int getNumberOfTables();

	List<Rule> getRules();

	List<Rule> getRules(Table table);

	Set<Rule> getEnabledRules();

	Set<Rule> getUnsupportedRules();

	Set<Rule> getPermanentlyOnRules();

	Set<Rule> getSwitchableRules();

	void enableAll();

	void disableAll();

	void enableTables(Table... tables);

	void disableTables(Table... table);

	void enableRules(Rule... rules);

	void disableRules(Rule... rules);

	boolean hasEnabledRules(Table table);

	boolean hasSwitchableRules(Table table);

	boolean isRuleEnabled(Rule rule);

	RuleStatus getRuleStatus(Rule rule);

	OWL2RLPersistenceLayer getOWL2RLPersistenceLayer();

	boolean hasFalseArgumentsDescription(String owl2RLRuleName);

	OWL2RLFalseArgumentsDescription getFalseArgumentsDescription(String owl2RLRuleName);

	public class OWL2RLFalseArgumentsDescription
	{
		private final int numberOfClassArguments;
		private final int numberOfIndividualArguments;
		private final int numberOfObjectPropertyArguments;
		private final int numberOfDataPropertyArguments;

		public OWL2RLFalseArgumentsDescription(int numberOfClassArguments, int numberOfIndividualArguments,
				int numberOfObjectPropertyArguments, int numberOfDataPropertyArguments)
		{
			this.numberOfClassArguments = numberOfClassArguments;
			this.numberOfIndividualArguments = numberOfIndividualArguments;
			this.numberOfObjectPropertyArguments = numberOfObjectPropertyArguments;
			this.numberOfDataPropertyArguments = numberOfDataPropertyArguments;
		}

		public boolean hasClassArguments()
		{
			return getNumberOfClassArguments() != 0;
		}

		public boolean hasIndividualArguments()
		{
			return getNumberOfIndividualArguments() != 0;
		}

		public boolean hasObjectPropertyArguments()
		{
			return getNumberOfObjectPropertyArguments() != 0;
		}

		public boolean hasDataPropertyArguments()
		{
			return getNumberOfDataPropertyArguments() != 0;
		}

		public int getNumberOfClassArguments()
		{
			return numberOfClassArguments;
		}

		public int getNumberOfIndividualArguments()
		{
			return numberOfIndividualArguments;
		}

		public int getNumberOfDataPropertyArguments()
		{
			return numberOfDataPropertyArguments;
		}

		public int getNumberOfObjectPropertyArguments()
		{
			return numberOfObjectPropertyArguments;
		}
	}
}
