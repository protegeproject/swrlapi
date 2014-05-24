package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.core.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SWRLAPIApplicationModel;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class OpenSWRLRuleEditorAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationController;

	public OpenSWRLRuleEditorAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		openSWRLRuleEditor();
	}

	public void openSWRLRuleEditor()
	{

	}

	private SWRLRulesTableModel getSWRLRulesModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesTableModel();
	}

	private SWRLAPIApplicationModel getApplicationModel()
	{
		return this.applicationController.getApplicationModel();
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
