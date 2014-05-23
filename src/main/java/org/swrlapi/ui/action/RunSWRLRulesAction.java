package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import org.swrlapi.ui.ApplicationController;
import org.swrlapi.ui.core.ApplicationModel;
import org.swrlapi.ui.core.ApplicationView;
import org.swrlapi.ui.core.SWRLRuleModel;
import org.swrlapi.ui.dialog.ApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesDataSourceModel;
import org.swrlapi.ui.model.SWRLRulesModel;

public class RunSWRLRulesAction implements ActionListener
{
	private final ApplicationController application;

	public RunSWRLRulesAction(ApplicationController application)
	{
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!getMappingExpressionsModel().hasSWRLRuleModels())
			getApplicationDialogManager().showMessageDialog(getApplicationView(), "No mappings defined!");
		else if (!getDataSourceModel().hasSWRLRulesDataSource())
			getApplicationDialogManager().showMessageDialog(getApplicationView(), "No data source loaded!");
		else {
			try {
				@SuppressWarnings("unused")
				Set<SWRLRuleModel> mappingExpressions = getMappingExpressionsModel().getSWRLRuleModels(true);

				// TODO Run the query

				getApplicationDialogManager().showMessageDialog(getApplicationView(), "Mappings performed successfully.");
			} catch (Exception ex) {
				ex.printStackTrace();
				getApplicationDialogManager().showErrorMessageDialog(getApplicationView(), "Mapping error: " + ex.getMessage());
			}
		}
	}

	private SWRLRulesModel getMappingExpressionsModel()
	{
		return application.getApplicationModel().getSWRLRulesModel();
	}

	private SWRLRulesDataSourceModel getDataSourceModel()
	{
		return application.getApplicationModel().getDataSourceModel();
	}

	private ApplicationView getApplicationView()
	{
		return application.getApplicationViewController();
	}

	@SuppressWarnings("unused")
	private ApplicationModel getApplicationModel()
	{
		return application.getApplicationModel();
	}

	private ApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}
