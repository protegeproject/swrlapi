package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class DefaultSWRLRuleEngineModel implements SWRLRuleEngineModel
{
  @NonNull private final SWRLRulesTableModel swrlRulesTableModel;
  @NonNull private final SWRLRuleEngine ruleEngine;
  @NonNull private final SWRLParser swrlParser;
  @NonNull private final SWRLRuleRenderer swrlRuleRenderer;
  @NonNull private final SWRLAutoCompleter swrlAutoCompleter;

  public DefaultSWRLRuleEngineModel(@NonNull SWRLRuleEngine ruleEngine)
  {
    this.ruleEngine = ruleEngine;
    this.swrlRuleRenderer = this.ruleEngine.createSWRLRuleRenderer();
    this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, this.swrlRuleRenderer);
    this.swrlParser = this.ruleEngine.createSWRLParser();
    this.swrlAutoCompleter = this.ruleEngine.createSWRLAutoCompleter();
  }

  @NonNull @Override
  public SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.ruleEngine;
  }

  @NonNull @Override
  public SWRLParser getSWRLParser()
  {
    return this.swrlParser;
  }

  @NonNull @Override
  public SWRLAutoCompleter getSWRLAutoCompleter()
  {
    return this.swrlAutoCompleter;
  }

  @NonNull @Override
  public SWRLRuleRenderer getSWRLRuleRenderer()
  {
    return this.swrlRuleRenderer;
  }

  @NonNull @Override
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
