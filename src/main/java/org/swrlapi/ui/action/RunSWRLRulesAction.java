package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class RunSWRLRulesAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationController;

	public RunSWRLRulesAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!getSWRLRulesTableModel().hasSWRLRules())
			getApplicationDialogManager().showMessageDialog(getApplicationView(), "No rules!");
		else {
			try {
				// TODO Run the query; get it from getSWRLRulesTableModel()
			} catch (Exception ex) {
				ex.printStackTrace();
				getApplicationDialogManager().showErrorMessageDialog(getApplicationView(),
						"Error running SQWRL query: " + ex.getMessage());
			}
		}
	}

	private SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesTableModel();
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
