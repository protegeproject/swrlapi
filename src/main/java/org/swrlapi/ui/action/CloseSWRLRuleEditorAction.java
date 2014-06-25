package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class CloseSWRLRuleEditorAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationDialogManager;

	public CloseSWRLRuleEditorAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationDialogManager = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		confirmCloseSWRLRuleEditor();
	}

	public void confirmCloseSWRLRuleEditor()
	{
		if (hasSWRLRulesTableModel() && getApplicationModel().areSWRLRulesModified()
				&& getApplicationDialogManager().showConfirmDialog("Close Editor", "Do you really want to close the editor?")) {
			closeSWRLRuleEditor();
		} else
			closeSWRLRuleEditor();
	}

	private void closeSWRLRuleEditor()
	{
		getSWRLRulesTableModel().clearSWRLRules();
		getApplicationModel().clearSWRLRulesModified();
	}

	private SWRLAPIApplicationModel getApplicationModel()
	{
		return applicationDialogManager.getApplicationModel();
	}

	private SWRLRulesTableModel getSWRLRulesTableModel()
	{
		return getApplicationModel().getSWRLRulesTableModel();
	}

	private boolean hasSWRLRulesTableModel()
	{
		return getApplicationModel().getSWRLRulesTableModel() != null;
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return applicationDialogManager.getApplicationDialogManager();
	}
}
