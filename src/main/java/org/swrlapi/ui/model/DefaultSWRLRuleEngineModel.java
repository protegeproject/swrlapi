package org.swrlapi.ui.model;

import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;

public class DefaultSWRLRuleEngineModel implements SWRLRuleEngineModel
{
  private final SWRLRulesTableModel swrlRulesTableModel;
  private final SWRLRuleEngine ruleEngine;
  private final SWRLParser swrlParser;
  private final SWRLRuleRenderer swrlRuleRenderer;
  private final SWRLAutoCompleter swrlAutoCompleter;

  public DefaultSWRLRuleEngineModel(SWRLRuleEngine ruleEngine)
  {
    this.ruleEngine = ruleEngine;
    this.swrlRuleRenderer = this.ruleEngine.createSWRLRuleRenderer();
    this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, this.swrlRuleRenderer);
    this.swrlParser = this.ruleEngine.createSWRLParser();
    this.swrlAutoCompleter = this.ruleEngine.createSWRLAutoCompleter();
  }

  @Override
  public SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.ruleEngine;
  }

  @Override
  public SWRLParser getSWRLParser()
  {
    return this.swrlParser;
  }

  @Override
  public SWRLAutoCompleter getSWRLAutoCompleter()
  {
    return this.swrlAutoCompleter;
  }

  @Override
  public SWRLRulesTableModel getSWRLRulesTableModel()
  {
    return this.swrlRulesTableModel;
  }

  @Override
  public boolean areSWRLRulesModified()
  {
    return this.swrlRulesTableModel.hasBeenModified();
  }

  @Override
  public void clearSWRLRulesModified()
  {
    this.swrlRulesTableModel.clearModifiedStatus();
  }
}
