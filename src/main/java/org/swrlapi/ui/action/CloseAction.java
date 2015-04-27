package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseAction implements ActionListener
{
	public CloseAction()
	{
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	}

	public void confirmClose()
	{
		// if (mappingExpressionsModel.hasMappingExpressions()
		// && applicationModel.areMappingsModified()
		// && getApplicationDialogManager().showConfirmDialog(applicationView, "Close Mappings",
		// "Do you really want to close the mappings?")) {
		// close();
		// } else
		close();
	}

	private static void close()
	{
	}
}
