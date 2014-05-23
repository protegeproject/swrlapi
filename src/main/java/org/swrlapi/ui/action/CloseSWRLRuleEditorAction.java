package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.ApplicationModel;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.dialog.ApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesModel;

public class CloseSWRLRuleEditorAction implements ActionListener
{
	private final ApplicationController application;

	public CloseSWRLRuleEditorAction(ApplicationController application)
	{
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		closeSWRLRuleEditor(application);
	}

	public void closeSWRLRuleEditor(ApplicationController application)
	{
		SWRLRulesModel mappingExpressionsModel = application.getApplicationModel().getSWRLRulesModel();
		ApplicationView applicationViewController = application.getApplicationViewController();
		ApplicationModel applicationModel = application.getApplicationModel();

		if (mappingExpressionsModel.hasSWRLRuleModels()
				&& applicationModel.areMappingsModified()
				&& getApplicationDialogManager().showConfirmDialog(applicationViewController, "Close Mappings",
						"Do you really want to close the mappings?")) {
			close(application);
		} else
			close(application);
	}

	private static void close(ApplicationController application)
	{
		SWRLRulesModel mappingsExpressionsModel = application.getApplicationModel().getSWRLRulesModel();
		ApplicationModel applicationModel = application.getApplicationModel();

		mappingsExpressionsModel.clearSWRLRuleModels();
		applicationModel.clearMappingFileName();
		applicationModel.clearModifiedStatus();
	}

	private ApplicationView getApplicationView()
	{
		return application.getApplicationViewController();
	}

	private ApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
