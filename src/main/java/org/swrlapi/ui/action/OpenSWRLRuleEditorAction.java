package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swrlapi.ui.controller.SWRLAPIApplicationController;

public class OpenSWRLRuleEditorAction implements ActionListener
{
	@SuppressWarnings("unused")
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
		// TODO
	}
}
