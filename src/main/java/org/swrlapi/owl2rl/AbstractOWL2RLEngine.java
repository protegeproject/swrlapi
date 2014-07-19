package org.swrlapi.owl2rl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class provides utility methods that can be used by implementations on an OWL 2 RL engine in the SWRLAPI.
 *
 * @see org.swrlapi.owl2rl.OWL2RLEngine
 */
public abstract class AbstractOWL2RLEngine implements OWL2RLEngine
{
	private final List<RuleTable> ruleTables;
	private final Set<Rule> rules;
	private final Map<RuleTable, List<Rule>> table2RulesMap;
	private final Set<Set<Rule>> groupedRuleSets;
	private final OWL2RLPersistenceLayer persistenceLayer;
	private final Set<Rule> unsupportedRules;
	private final Set<Rule> permanentlyOnRules;
	private final Set<Rule> switchableRules;
	private final Map<String, OWL2RLFalseArgumentsDescription> argumentsDescriptionMap;

	private Set<Rule> enabledRules;
	private boolean ruleSelectionChanged;

	public AbstractOWL2RLEngine(OWL2RLPersistenceLayer persistenceLayer, Set<Rule> unsupportedRules,
			Set<Rule> permanentlyOnRules, Set<Set<Rule>> groupedRuleSets)
	{
		this.persistenceLayer = persistenceLayer;
		this.ruleTables = new ArrayList<RuleTable>(Arrays.asList(RuleTable.values()));
		this.rules = EnumSet.allOf(Rule.class);
		this.table2RulesMap = new HashMap<RuleTable, List<Rule>>();
		this.table2RulesMap.put(RuleTable.RuleTable4, new ArrayList<Rule>(Arrays.asList(Table4Rules)));
		this.table2RulesMap.put(RuleTable.RuleTable5, new ArrayList<Rule>(Arrays.asList(Table5Rules)));
		this.table2RulesMap.put(RuleTable.RuleTable6, new ArrayList<Rule>(Arrays.asList(Table6Rules)));
		this.table2RulesMap.put(RuleTable.RuleTable7, new ArrayList<Rule>(Arrays.asList(Table7Rules)));
		this.table2RulesMap.put(RuleTable.RuleTable8, new ArrayList<Rule>(Arrays.asList(Table8Rules)));
		this.table2RulesMap.put(RuleTable.RuleTable9, new ArrayList<Rule>(Arrays.asList(Table9Rules)));
		this.unsupportedRules = unsupportedRules;
		this.permanentlyOnRules = permanentlyOnRules;
		this.groupedRuleSets = groupedRuleSets;

		// Switchable rules are the subset of rules that are not permanently and are not unsupported.
		this.switchableRules = new HashSet<Rule>(this.rules);
		this.switchableRules.removeAll(this.permanentlyOnRules);
		this.switchableRules.removeAll(this.unsupportedRules);

		// Enabled all switchable rules plus permanently on rules
		this.enabledRules = new HashSet<Rule>(this.switchableRules);
		this.enabledRules.addAll(this.permanentlyOnRules);

		// Persistence layer can indicate rule is disabled.
		this.enabledRules.retainAll(persistenceLayer.getEnabledRules());

		this.ruleSelectionChanged = false;

		this.argumentsDescriptionMap = new HashMap<String, OWL2RLFalseArgumentsDescription>();

		initializeArgumentsDescriptionMap();
	}

	@Override
	public void resetRuleSelectionChanged()
	{
		this.ruleSelectionChanged = false;
	}

	@Override
	public void setRuleSelectionChanged()
	{
		this.ruleSelectionChanged = true;
	}

	@Override
	public boolean hasRuleSelectionChanged()
	{
		return this.ruleSelectionChanged;
	}

	@Override
	public List<RuleTable> getRuleTables()
	{
		return this.ruleTables;
	}

	@Override
	public int getNumberOfRules()
	{
		return this.rules.size();
	}

	@Override
	public int getNumberOfTables()
	{
		return this.ruleTables.size();
	}

	@Override
	public List<Rule> getRules()
	{
		return new ArrayList<Rule>(this.rules);
	}

	@Override
	public List<Rule> getRules(RuleTable table)
	{
		return this.table2RulesMap.get(table);
	}

	@Override
	public Set<Rule> getEnabledRules()
	{
		return this.enabledRules;
	}

	@Override
	public Set<Rule> getUnsupportedRules()
	{
		return this.unsupportedRules;
	}

	@Override
	public Set<Rule> getPermanentlyOnRules()
	{
		return this.permanentlyOnRules;
	}

	@Override
	public Set<Rule> getSwitchableRules()
	{
		return this.switchableRules;
	}

	@Override
	public void enableAll()
	{
		this.enabledRules = this.switchableRules;

		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

	@Override
	public void disableAll()
	{
		this.enabledRules.clear();

		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().disableAll();
	}

	@Override
	public void enableTables(RuleTable... enabledTables)
	{
		for (RuleTable table : enabledTables) {
			for (Rule rule : this.table2RulesMap.get(table))
				this.enabledRules.add(rule);
		}
		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

	@Override
	public void disableTables(RuleTable... disabledTables)
	{
		Set<Rule> disabledRules = new HashSet<Rule>();

		for (RuleTable table : disabledTables)
			for (Rule rule : this.table2RulesMap.get(table))
				disabledRules.add(rule);

		this.enabledRules.removeAll(disabledRules);

		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setDisabledRules(disabledRules);
	}

	@Override
	public void enableRules(Rule... rulesToEnable)
	{
		for (Rule rule : rulesToEnable)
			enableRule(rule);

		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

	@Override
	public OWL2RLPersistenceLayer getOWL2RLPersistenceLayer()
	{
		return this.persistenceLayer;
	}

	private void enableRule(Rule rule)
	{
		for (Rule groupedRule : getGroup(rule))
			this.enabledRules.add(groupedRule);

		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

	@Override
	public void disableRules(Rule... rulesToDisable)
	{
		for (Rule rule : rulesToDisable)
			disableRule(rule);
	}

	private void disableRule(Rule rule)
	{
		for (Rule groupedRule : getGroup(rule)) {
			this.enabledRules.remove(groupedRule);
			setRuleSelectionChanged();
			getOWL2RLPersistenceLayer().setDisabledRule(rule);
		}
	}

	@Override
	public boolean isRuleEnabled(Rule rule)
	{
		return this.enabledRules.contains(rule);
	}

	public boolean isRuleSwitchable(Rule rule)
	{
		return this.switchableRules.contains(rule);
	}

	@Override
	public boolean hasEnabledRules(RuleTable table)
	{
		for (Rule rule : getRules(table)) {
			if (isRuleEnabled(rule))
				return true;
		}
		return false;
	}

	@Override
	public boolean hasSwitchableRules(RuleTable table)
	{
		for (Rule rule : getRules(table)) {
			if (isRuleSwitchable(rule))
				return true;
		}
		return false;
	}

	@Override
	public RuleStatus getRuleStatus(Rule rule)
	{
		if (this.unsupportedRules.contains(rule))
			return RuleStatus.Unsupported;
		else if (this.permanentlyOnRules.contains(rule))
			return RuleStatus.PermanentlyOn;
		else
			return RuleStatus.Switchable;
	}

	@Override
	public boolean hasFalseArgumentsDescription(String owl2RLRuleName)
	{
		return this.argumentsDescriptionMap.containsKey(owl2RLRuleName);
	}

	@Override
	public OWL2RLFalseArgumentsDescription getFalseArgumentsDescription(String owl2RLRuleName)
	{
		return this.argumentsDescriptionMap.get(owl2RLRuleName);
	}

	/**
	 * Find other rules that are grouped with this rule. Grouped rules are enabled and disabled together. The rule itself
	 * is added to the group and associated rules (if any) are then found and added.
	 */
	private Set<Rule> getGroup(Rule rule)
	{
		Set<Rule> result = new HashSet<Rule>();

		result.add(rule);

		for (Set<Rule> group : this.groupedRuleSets) {
			if (group.contains(rule))
				result.addAll(group);
		}
		return result;
	}

	private void initializeArgumentsDescriptionMap()
	{
		createArgumentDescription(OWL2RLNames.Rule.EQ_DIFF1.toString(), 0, 2, 0, 0);
		createArgumentDescription(OWL2RLNames.Rule.PRP_PDW.toString(), 0, 2, 2, 0);
		createArgumentDescription(OWL2RLNames.Rule.PRP_ASYP.toString(), 0, 2, 1, 0);
		createArgumentDescription(OWL2RLNames.Rule.CAX_DW.toString(), 2, 1, 0, 0);
	}

	private void createArgumentDescription(String owl2RLRuleName, int numberOfClassArguments,
			int numberOfIndividualArguments, int numberOfObjectPropertyArguments, int numberOfDataPropertyArguments)
	{
		this.argumentsDescriptionMap.put(owl2RLRuleName, new OWL2RLFalseArgumentsDescription(numberOfClassArguments,
				numberOfIndividualArguments, numberOfObjectPropertyArguments, numberOfDataPropertyArguments));
	}
}
