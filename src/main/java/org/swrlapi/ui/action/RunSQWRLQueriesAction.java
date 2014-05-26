package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class RunSQWRLQueriesAction implements ActionListener
{
	private final SWRLAPIApplicationController applicationController;

	private static final String INVALID_QUERY_TITLE = "Invalid Query";
	private static final String NO_QUERIES_TITLE = "No SQWRL Queries";
	private static final String NO_QUERIES_MESSAGE = "No SQWRL queries in ontology";

	public RunSQWRLQueriesAction(SWRLAPIApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!getSWRLRulesTableModel().hasSWRLRules())
			getApplicationDialogManager().showMessageDialog(NO_QUERIES_MESSAGE, NO_QUERIES_TITLE);
		else {
			try {
				// TODO Run the query; get it from getSWRLRulesTableModel()
			} catch (Exception ex) {
				ex.printStackTrace();
				getApplicationDialogManager().showErrorMessageDialog(ex.getMessage(), INVALID_QUERY_TITLE);
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
