package org.swrlapi.ui.model;

import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

/**
 * A model that reflects the rules in an OWL 2 RL rule table.
 *
 * @see org.swrlapi.owl2rl.OWL2RLEngine
 * @see org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView
 */
public class OWL2RLModel implements SWRLAPIModel
{
  private final OWL2RLRuleTablesView owl2RLRuleTablesView;
  private final OWL2RLEngine owl2RLEngine;

  public OWL2RLModel(OWL2RLRuleTablesView owl2RLRuleTablesView, OWL2RLEngine owl2RLEngine)
  {
    this.owl2RLRuleTablesView = owl2RLRuleTablesView;
    this.owl2RLEngine = owl2RLEngine;
  }

  public OWL2RLRuleTablesView getOWL2RLRuleTablesView()
  {
    return this.owl2RLRuleTablesView;
  }

  public OWL2RLEngine getOWL2RLEngine()
  {
    return this.owl2RLEngine;
  }

  public void fireUpdate()
  {
    this.owl2RLRuleTablesView.update();
  }
}
