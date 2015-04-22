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
 */
public class OWL2RLTablesControlView extends JPanel implements SWRLAPIView
{
	private static final long serialVersionUID = 1L;

	private final OWL2RLModel owl2RLModel;
	private final List<OWL2RLRuleTableActivationButton> ruleTableActivationButtons;

	public OWL2RLTablesControlView(OWL2RLModel owl2RLModel)
	{
		this.owl2RLModel = owl2RLModel;
		this.ruleTableActivationButtons = new ArrayList<>();

		initialize();
	}

	@Override
	public void update()
	{
		for (OWL2RLRuleTableActivationButton button : this.ruleTableActivationButtons)
			button.update();
	}

	private void initialize()
	{
		setLayout(new GridLayout(1, getOWL2RLModel().getOWL2RLEngine().getNumberOfTables()));

		for (OWL2RLNames.OWL2RLRuleTable ruleTable : getOWL2RLModel().getOWL2RLEngine().getRuleTables()) {
			OWL2RLRuleTableActivationButton button = new OWL2RLRuleTableActivationButton(getOWL2RLModel(), ruleTable);
			this.ruleTableActivationButtons.add(button);
			add(button);
		}
	}

	private OWL2RLModel getOWL2RLModel()
	{
		return this.owl2RLModel;
	}
}
