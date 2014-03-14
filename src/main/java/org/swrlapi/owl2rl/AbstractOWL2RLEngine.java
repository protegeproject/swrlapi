
package org.swrlapi.owl2rl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractOWL2RLEngine implements OWL2RLEngine
{
	private final List<Table> tables;
	private final Set<Rule> rules;
	private final Map<Table, List<Rule>> table2RulesMap;
	private final Set<Set<Rule>> groupedRuleSets;
	private final OWL2RLPersistenceLayer persistenceLayer;
	private final Set<Rule> unsupportedRules;
	private final Set<Rule> permanentlyOnRules;
	private final Set<Rule> switchableRules;
	
	private Set<Rule> enabledRules;
	private boolean ruleSelectionChanged;
	private Map<String, OWL2RLFalseArgumentsDescription> argumentsDescriptionMap;

	public AbstractOWL2RLEngine(OWL2RLPersistenceLayer persistenceLayer, Set<Rule> unsupportedRules, Set<Rule> permanentlyOnRules,
			Set<Set<Rule>> groupedRuleSets)
	{
		this.persistenceLayer = persistenceLayer;
		this.tables = new ArrayList<Table>(Arrays.asList(Table.values()));
		this.rules = EnumSet.allOf(Rule.class);
		this.table2RulesMap = new HashMap<Table, List<Rule>>();
		this.table2RulesMap.put(Table.Table4, new ArrayList<Rule>(Arrays.asList(Table4Rules)));
		this.table2RulesMap.put(Table.Table5, new ArrayList<Rule>(Arrays.asList(Table5Rules)));
		this.table2RulesMap.put(Table.Table6, new ArrayList<Rule>(Arrays.asList(Table6Rules)));
		this.table2RulesMap.put(Table.Table7, new ArrayList<Rule>(Arrays.asList(Table7Rules)));
		this.table2RulesMap.put(Table.Table8, new ArrayList<Rule>(Arrays.asList(Table8Rules)));
		this.table2RulesMap.put(Table.Table9, new ArrayList<Rule>(Arrays.asList(Table9Rules)));
		this.unsupportedRules = unsupportedRules;
		this.permanentlyOnRules = permanentlyOnRules;
		this.groupedRuleSets = groupedRuleSets;

		// Switchable rules are the subset of rules that are not permanently on or unsupported.
		this.switchableRules = new HashSet<Rule>(this.rules);
		this.switchableRules.removeAll(this.permanentlyOnRules);
		this.switchableRules.removeAll(this.unsupportedRules);

		// Enabled rule all switchable rules plus permanently on rules
		this.enabledRules = new HashSet<Rule>(this.switchableRules);
		this.enabledRules.addAll(this.permanentlyOnRules);

		// Persistence layer can indicate rule is disabled.
		this.enabledRules.retainAll(persistenceLayer.getEnabledRules());
		
		this.ruleSelectionChanged = false;

		initializeArgumentsDescriptionMap();
	}

	public void resetRuleSelectionChanged()
	{
		this.ruleSelectionChanged = false;
	}
	
	public void setRuleSelectionChanged()
	{
		this.ruleSelectionChanged = true;
	}
	
	public boolean hasRuleSelectionChanged()
	{
		return this.ruleSelectionChanged;
	}

	public List<Table> getTables()
	{
		return this.tables;
	}

	public int getNumberOfRules()
	{
		return this.rules.size();
	}

	public int getNumberOfTables()
	{
		return this.tables.size();
	}

	public List<Rule> getRules()
	{
		return new ArrayList<Rule>(this.rules);
	}

	public List<Rule> getRules(Table table)
	{
		return this.table2RulesMap.get(table);
	}

	public Set<Rule> getEnabledRules()
	{
		return this.enabledRules;
	}

	public Set<Rule> getUnsupportedRules()
	{
		return this.unsupportedRules;
	}

	public Set<Rule> getPermanentlyOnRules()
	{
		return this.permanentlyOnRules;
	}

	public Set<Rule> getSwitchableRules()
	{
		return this.switchableRules;
	}

	public void enableAll()
	{
		this.enabledRules = this.switchableRules;
		
		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

	public void disableAll()
	{
		this.enabledRules = new HashSet<Rule>();
		
		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().disableAll();
	}

	public void enableTables(Table... enabledTables)
	{
		for (Table table : enabledTables) {
			for (Rule rule : this.table2RulesMap.get(table))
				this.enabledRules.add(rule);
		}
		setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

	public void disableTables(Table... disabledTables)
	{
		Set<Rule> disabledRules = new HashSet<Rule>();

		for (Table table : disabledTables)
			for (Rule rule : this.table2RulesMap.get(table))
				disabledRules.add(rule);

		this.enabledRules.removeAll(disabledRules);
					
					setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setDisabledRules(disabledRules);
	}

	public void enableRules(Rule... rulesToEnable)
	{
		for (Rule rule : rulesToEnable)
			enableRule(rule);

				setRuleSelectionChanged();
		getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
	}

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

	public boolean isRuleEnabled(Rule rule)
	{
		return this.enabledRules.contains(rule);
	}

	public boolean isRuleSwitchable(Rule rule)
	{
		return this.switchableRules.contains(rule);
	}

	public boolean hasEnabledRules(Table table)
	{
		for (Rule rule : getRules(table)) {
			if (isRuleEnabled(rule))
				return true;
		}
		return false;
	}

	public boolean hasSwitchableRules(Table table)
	{
		for (Rule rule : getRules(table)) {
			if (isRuleSwitchable(rule))
				return true;
		}
		return false;
	}

	public RuleStatus getRuleStatus(Rule rule)
	{
		if (this.unsupportedRules.contains(rule))
			return RuleStatus.Unsupported;
		else if (this.permanentlyOnRules.contains(rule))
			return RuleStatus.PermanentlyOn;
		else
			return RuleStatus.Switchable;
	}

	public boolean hasFalseArgumentsDescription(String owl2RLRuleName)
	{
		return this.argumentsDescriptionMap.containsKey(owl2RLRuleName);
	}

	public OWL2RLFalseArgumentsDescription getFalseArgumentsDescription(String owl2RLRuleName)
	{
		return this.argumentsDescriptionMap.get(owl2RLRuleName);
	}

	/**
	 * Final other rules that are grouped with this rule. Grouped rules are enabled and disabled together. The rule itself is added to the group and associated
	 * rules (if any) are then found and added.
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
		this.argumentsDescriptionMap = new HashMap<String, OWL2RLFalseArgumentsDescription>();

		createArgumentDescription(OWL2RLNames.Rule.EQ_DIFF1.toString(), 0, 2, 0, 0);
		createArgumentDescription(OWL2RLNames.Rule.PRP_PDW.toString(), 0, 2, 2, 0);
		createArgumentDescription(OWL2RLNames.Rule.PRP_ASYP.toString(), 0, 2, 1, 0);
		createArgumentDescription(OWL2RLNames.Rule.CAX_DW.toString(), 2, 1, 0, 0);
	}

	private void createArgumentDescription(String owl2RLRuleName, int numberOfClassArguments, int numberOfIndividualArguments,
																					int numberOfObjectPropertyArguments, int numberOfDataPropertyArguments)
	{
		this.argumentsDescriptionMap.put(owl2RLRuleName, new OWL2RLFalseArgumentsDescription(numberOfClassArguments, numberOfIndividualArguments,
				numberOfObjectPropertyArguments, numberOfDataPropertyArguments));
	}
}
