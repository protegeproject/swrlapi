package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.core.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SWRLAPIApplicationModel;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class CloseSWRLRuleEditorAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationController;

	public CloseSWRLRuleEditorAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		confirmCloseSWRLRuleEditor();
	}

	public void confirmCloseSWRLRuleEditor()
	{
		if (hasSWRLRulesModel()
				&& getApplicationModel().areRulesModified()
				&& getApplicationDialogManager().showConfirmDialog(getApplicationView(), "Close Editor",
						"Do you really want to close the editor?")) {
			closeSWRLRuleEditor();
		} else
			closeSWRLRuleEditor();
	}

	private void closeSWRLRuleEditor()
	{
		getSWRLRulesModel().clearSWRLRules();
		getApplicationModel().clearModifiedStatus();
	}

	private SWRLAPIApplicationModel getApplicationModel()
	{
		return applicationController.getApplicationModel();
	}

	private SWRLRulesTableModel getSWRLRulesModel()
	{
		return getApplicationModel().getSWRLRulesTableModel();
	}

	private boolean hasSWRLRulesModel()
	{
		return getApplicationModel().getSWRLRulesTableModel() != null;
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
