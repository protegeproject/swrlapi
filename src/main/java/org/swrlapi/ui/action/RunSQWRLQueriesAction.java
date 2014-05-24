package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class RunSQWRLQueriesAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationController;

	public RunSQWRLQueriesAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!getSWRLRulesTableModel().hasSWRLRules())
			getApplicationDialogManager().showMessageDialog("No rules!");
		else {
			try {
				// TODO Run the query; get it from getSWRLRulesTableModel()
			} catch (Exception ex) {
				ex.printStackTrace();
				getApplicationDialogManager().showErrorMessageDialog("Error running SQWRL query: " + ex.getMessage());
			}
		}
	}

	private SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesTableModel();
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return applicationController.getApplicationDialogManager();
	}
}
