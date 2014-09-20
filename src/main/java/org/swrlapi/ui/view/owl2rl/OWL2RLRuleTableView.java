package org.swrlapi.ui.view.owl2rl;

import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @see org.swrlapi.ui.model.OWL2RLModel
 * @see org.swrlapi.owl2rl.OWL2RLNames
 */
public class OWL2RLRuleTableView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final OWL2RLModel owl2RLModel;
	private final OWL2RLNames.RuleTable owl2RLRuleTable;
	private final List<OWL2RLRuleActivationButton> ruleActivationButtons;

	public OWL2RLRuleTableView(OWL2RLModel owl2RLModel, OWL2RLNames.RuleTable owl2RLRuleTable)
	{
		this.owl2RLModel = owl2RLModel;
		this.owl2RLRuleTable = owl2RLRuleTable;
		this.ruleActivationButtons = new ArrayList<>();

		initialize();
	}

	@Override
	public void update()
	{
		for (OWL2RLRuleActivationButton button : this.ruleActivationButtons)
			button.update();
	}

	private void initialize()
	{
		int numberOfButtons = getOWL2RLModel().getOWL2RLEngine().getRules(this.owl2RLRuleTable).size();
		int n = 1 + (int)java.lang.Math.sqrt(numberOfButtons);

		setLayout(new GridLayout(n, n));

		for (OWL2RLNames.Rule rule : getOWL2RLModel().getOWL2RLEngine().getRules(this.owl2RLRuleTable)) {
			OWL2RLRuleActivationButton button = new OWL2RLRuleActivationButton(getOWL2RLModel(), rule);
			this.ruleActivationButtons.add(button);
			add(button);
		}
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
