package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveAction implements ActionListener
{
	public SaveAction()
	{
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		save();
	}

	public void save()
	{
		// if (getApplicationModel().hasMappingFile()) {
		// String fileName = getApplicationModel().getMappingFileName();
		// Set<MappingExpression> mappingExpressions = mappingsExpressionsModel.getMappingExpressions();
		//
		// try {
		// getApplicationModel().getMappingExpressionsPersistenceLayer().putMappingExpressions(mappingExpressions,
		// fileName);
		//
		// getApplicationModel().setMappingFileName(fileName);
		// getApplicationModel().clearModifiedStatus();
		// } catch (MappingMasterException ex) {
		// getApplicationDialogManager().showErrorMessageDialog(getApplicationView(), ex.getMessage());
		// }
		// }
	}
}
