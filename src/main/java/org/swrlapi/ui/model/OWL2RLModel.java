package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView;

import java.util.Optional;

/**
 * A model that reflects the rules in an OWL 2 RL rule table.
 *
 * @see org.swrlapi.owl2rl.OWL2RLEngine
 * @see org.swrlapi.ui.view.owl2rl.OWL2RLRuleTablesView
 */
public class OWL2RLModel implements SWRLAPIModel
{
  @NonNull private OWL2RLEngine owl2RLEngine;

  @NonNull private Optional<@NonNull OWL2RLRuleTablesView> view = Optional.<@NonNull OWL2RLRuleTablesView>empty();

  public OWL2RLModel(@NonNull OWL2RLEngine owl2RLEngine)
  {
    this.owl2RLEngine = owl2RLEngine;
  }

  public void updateModel(@NonNull OWL2RLEngine owl2RLEngine)
  {
    this.owl2RLEngine = owl2RLEngine;
    updateView();
  }

  public void setView(@NonNull OWL2RLRuleTablesView view)
  {
    this.view = Optional.of(view);
  }

  @NonNull public OWL2RLEngine getOWL2RLEngine()
  {
    return this.owl2RLEngine;
  }

  @Override public void updateView()
  {
    if (view.isPresent())
      this.view.get().update();
  }
}
