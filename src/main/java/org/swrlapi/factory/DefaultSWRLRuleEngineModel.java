package org.swrlapi.factory;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

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
  public SWRLRuleRenderer getSWRLRuleRenderer()
  {
    return this.swrlRuleRenderer;
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

	@Override
	public void updateView() { this.swrlRulesTableModel.updateView(); }
}
