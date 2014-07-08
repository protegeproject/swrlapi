package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class RunSQWRLQueriesAction implements ActionListener
{
	private static final String INVALID_QUERY_TITLE = "Invalid Query";
	private static final String NO_QUERIES_TITLE = "No SQWRL Queries";
	private static final String NO_QUERIES_MESSAGE = "No SQWRL queries in ontology";

	private final SWRLAPIApplicationModel applicationModel;
	private final SWRLAPIApplicationDialogManager applicationDialogManager;

	public RunSQWRLQueriesAction(SWRLAPIApplicationModel applicationModel,
			SWRLAPIApplicationDialogManager applicationDialogManager)
	{
		this.applicationModel = applicationModel;
		this.applicationDialogManager = applicationDialogManager;
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
		return getApplicationModel().getSWRLRulesTableModel();
	}

	private SWRLAPIApplicationModel getApplicationModel()
	{
		return this.applicationModel;
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return this.applicationDialogManager;
	}
}
