package org.swrlapi.owl2rl;

import checkers.nullness.quals.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class provides utility methods that can be used by implementations on an OWL 2 RL engine in the SWRLAPI.
 *
 * @see org.swrlapi.owl2rl.OWL2RLEngine
 */
public abstract class AbstractOWL2RLEngine implements OWL2RLEngine
{
  @NonNull private final Set<OWL2RLRule> rules;
  @NonNull private final List<OWL2RLRuleTable> ruleTables;
  @NonNull private final Map<OWL2RLRuleTable, List<OWL2RLRule>> table2RulesMap;
  @NonNull private final Set<Set<OWL2RLRule>> groupedRuleSets;
  @NonNull private final OWL2RLPersistenceLayer persistenceLayer;
  @NonNull private final Set<OWL2RLRule> unsupportedRules;
  @NonNull private final Set<OWL2RLRule> permanentlyOnRules;
  @NonNull private final Set<OWL2RLRule> switchableRules;

  private Set<OWL2RLRule> enabledRules;
  private boolean ruleSelectionChanged;

  protected AbstractOWL2RLEngine(@NonNull OWL2RLPersistenceLayer persistenceLayer,
    @NonNull Set<OWL2RLRule> unsupportedRules, @NonNull Set<OWL2RLRule> permanentlyOnRules,
    @NonNull Set<Set<OWL2RLRule>> groupedRuleSets)
  {
    this.persistenceLayer = persistenceLayer;

    this.rules = EnumSet.allOf(OWL2RLRule.class);
    this.ruleTables = new ArrayList<>(Arrays.asList(OWL2RLRuleTable.values()));

    this.table2RulesMap = new HashMap<>();
    this.table2RulesMap.put(OWL2RLRuleTable.RuleTable4, new ArrayList<>(Arrays.asList(Table4Rules)));
    this.table2RulesMap.put(OWL2RLRuleTable.RuleTable5, new ArrayList<>(Arrays.asList(Table5Rules)));
    this.table2RulesMap.put(OWL2RLRuleTable.RuleTable6, new ArrayList<>(Arrays.asList(Table6Rules)));
    this.table2RulesMap.put(OWL2RLRuleTable.RuleTable7, new ArrayList<>(Arrays.asList(Table7Rules)));
    this.table2RulesMap.put(OWL2RLRuleTable.RuleTable8, new ArrayList<>(Arrays.asList(Table8Rules)));
    this.table2RulesMap.put(OWL2RLRuleTable.RuleTable9, new ArrayList<>(Arrays.asList(Table9Rules)));

    this.unsupportedRules = unsupportedRules;
    this.permanentlyOnRules = permanentlyOnRules;
    this.groupedRuleSets = groupedRuleSets;

    // Switchable rules are the subset of rules that are not permanently and are not unsupported.
    this.switchableRules = new HashSet<>(this.rules);
    this.switchableRules.removeAll(this.permanentlyOnRules);
    this.switchableRules.removeAll(this.unsupportedRules);

    // Enabled all switchable rules plus permanently on rules
    this.enabledRules = new HashSet<>(this.switchableRules);
    this.enabledRules.addAll(this.permanentlyOnRules);

    // Persistence layer can indicate rule is disabled.
    this.enabledRules.retainAll(persistenceLayer.getEnabledRules());

    this.ruleSelectionChanged = false;
  }

  @Override public void resetRuleSelectionChanged()
  {
    this.ruleSelectionChanged = false;
  }

  @Override public void setRuleSelectionChanged()
  {
    this.ruleSelectionChanged = true;
  }

  @Override public boolean hasRuleSelectionChanged()
  {
    return this.ruleSelectionChanged;
  }

  @NonNull @Override public List<OWL2RLRuleTable> getRuleTables()
  {
    return this.ruleTables;
  }

  @Override public int getNumberOfRules()
  {
    return this.rules.size();
  }

  @Override public int getNumberOfTables()
  {
    return this.ruleTables.size();
  }

  @NonNull @Override public List<OWL2RLRule> getRules()
  {
    return new ArrayList<>(this.rules);
  }

  @NonNull @Override public List<OWL2RLRule> getRules(OWL2RLRuleTable table)
  {
    return this.table2RulesMap.get(table);
  }

  @NonNull @Override public Set<OWL2RLRule> getEnabledRules()
  {
    return this.enabledRules;
  }

  @NonNull @Override public Set<OWL2RLRule> getUnsupportedRules()
  {
    return this.unsupportedRules;
  }

  @NonNull @Override public Set<OWL2RLRule> getPermanentlyOnRules()
  {
    return this.permanentlyOnRules;
  }

  @NonNull @Override public Set<OWL2RLRule> getSwitchableRules()
  {
    return this.switchableRules;
  }

  @Override public void enableAll()
  {
    this.enabledRules = this.switchableRules;

    setRuleSelectionChanged();
    getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
  }

  @Override public void disableAll()
  {
    this.enabledRules.clear();

    setRuleSelectionChanged();
    getOWL2RLPersistenceLayer().disableAll();
  }

  @Override public void enableTables(@NonNull OWL2RLRuleTable... enabledTables)
  {
    for (OWL2RLRuleTable table : enabledTables) {
      this.enabledRules.addAll(this.table2RulesMap.get(table).stream().collect(Collectors.toList()));
    }
    setRuleSelectionChanged();
    getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
  }

  @Override public void disableTables(@NonNull OWL2RLRuleTable... disabledTables)
  {
    Set<OWL2RLRule> disabledRules = new HashSet<>();

    for (OWL2RLRuleTable table : disabledTables)
      disabledRules.addAll(this.table2RulesMap.get(table).stream().collect(Collectors.toList()));

    this.enabledRules.removeAll(disabledRules);

    setRuleSelectionChanged();
    getOWL2RLPersistenceLayer().setDisabledRules(disabledRules);
  }

  @Override public void enableRules(@NonNull OWL2RLRule... rulesToEnable)
  {
    for (OWL2RLRule rule : rulesToEnable)
      enableRule(rule);

    setRuleSelectionChanged();
    getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
  }

  @NonNull @Override public OWL2RLPersistenceLayer getOWL2RLPersistenceLayer()
  {
    return this.persistenceLayer;
  }

  private void enableRule(OWL2RLRule rule)
  {
    this.enabledRules.addAll(getGroup(rule).stream().collect(Collectors.toList()));

    setRuleSelectionChanged();
    getOWL2RLPersistenceLayer().setEnabledRules(this.enabledRules);
  }

  @Override public void disableRules(@NonNull OWL2RLRule... rulesToDisable)
  {
    for (OWL2RLRule rule : rulesToDisable)
      disableRule(rule);
  }

  private void disableRule(@NonNull OWL2RLRule rule)
  {
    for (OWL2RLRule groupedRule : getGroup(rule)) {
      this.enabledRules.remove(groupedRule);
      setRuleSelectionChanged();
      getOWL2RLPersistenceLayer().setDisabledRule(rule);
    }
  }

  @Override public boolean isRuleEnabled(@NonNull OWL2RLRule rule)
  {
    return this.enabledRules.contains(rule);
  }

  @Override public boolean hasEnabledRules(@NonNull OWL2RLRuleTable table)
  {
    for (OWL2RLRule rule : getRules(table)) {
      if (isRuleEnabled(rule))
        return true;
    }
    return false;
  }

  @Override public boolean hasSwitchableRules(@NonNull OWL2RLRuleTable table)
  {
    for (OWL2RLRule rule : getRules(table)) {
      if (isRuleSwitchable(rule))
        return true;
    }
    return false;
  }

  @NonNull @Override public OWL2RLRuleStatus getRuleStatus(@NonNull OWL2RLRule rule)
  {
    if (this.unsupportedRules.contains(rule))
      return OWL2RLRuleStatus.Unsupported;
    else if (this.permanentlyOnRules.contains(rule))
      return OWL2RLRuleStatus.PermanentlyOn;
    else
      return OWL2RLRuleStatus.Switchable;
  }

  /**
   * Find other rules that are grouped with this rule. Grouped rules are enabled and disabled together. The rule itself
   * is added to the group and associated rules (if any) are then found and added.
   */
  @NonNull private Set<OWL2RLRule> getGroup(@NonNull OWL2RLRule rule)
  {
    Set<OWL2RLRule> result = new HashSet<>();

    result.add(rule);

    this.groupedRuleSets.stream().filter(group -> group.contains(rule)).forEach(result::addAll);

    return result;
  }

  private boolean isRuleSwitchable(@NonNull OWL2RLRule rule)
  {
    return this.switchableRules.contains(rule);
  }
}
