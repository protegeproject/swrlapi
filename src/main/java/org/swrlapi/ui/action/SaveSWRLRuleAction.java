package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;

public class SaveSWRLRuleAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationController;

	public SaveSWRLRuleAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		saveSWRLRule();
	}

	public void saveSWRLRule()
	{
		// SWRLRulesTableModel swrlRulesTableModel = applicationController.getApplicationModel().getSWRLRulesTableModel();
		// SQWRLApplicationView applicationView = applicationController.getApplicationView();
		// SWRLAPIApplicationModel applicationModel = applicationController.getApplicationModel();
		// SQWRLQueryControllerView sqwrlQueryControllerView = applicationView.getSQWRLQueryControllerView();

		// sqwrlQueryControllerView.statusWindowAppend("Saving mappings ontology '" + fileName + "'...\n");
		// applicationController.getSWRLRulesPersistenceLayer().saveSWRLRules(mappingExpressions, fileName);
		// applicationModel.clearModifiedStatus();
	}

	private SQWRLApplicationView getApplicationView()
	{
		return applicationController.getApplicationView();
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
