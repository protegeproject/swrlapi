package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRuleModel;
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
		if (!getSWRLRulesModel().hasSWRLRules())
			getApplicationDialogManager().showMessageDialog(getApplicationView(), "No rule!");
		else if (hasSWRLRulesDataSource())
			getApplicationDialogManager().showMessageDialog(getApplicationView(), "No data source loaded!");
		else {
			try {
				@SuppressWarnings("unused")
				Set<SWRLRuleModel> swrlRuleModels = getSWRLRulesModel().getSWRLRuleModels(true);

				// TODO Run the query

				getApplicationDialogManager().showMessageDialog(getApplicationView(), "Mappings performed successfully.");
			} catch (Exception ex) {
				ex.printStackTrace();
				getApplicationDialogManager().showErrorMessageDialog(getApplicationView(), "Mapping error: " + ex.getMessage());
			}
		}
	}

	private SWRLRulesTableModel getSWRLRulesModel()
	{
		return applicationController.getApplicationModel().getSWRLRulesTableModel();
	}

	private boolean hasSWRLRulesDataSource()
	{
		return applicationController.getApplicationModel().getSWRLRulesDataSource() != null;
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
