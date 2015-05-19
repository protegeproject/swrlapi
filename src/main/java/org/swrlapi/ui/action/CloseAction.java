package org.swrlapi.ui.action;

import org.swrlapi.ui.dialog.SWRLAPIDialogManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseAction implements ActionListener
{
  private final Component parent;
  private final SWRLAPIDialogManager dialogManager;

  public static final String TITLE = "Close";
  private static final String MESSAGE = "Do you really want to close the ontology?";

  public CloseAction(Component parent, SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
  }

  @Override public void actionPerformed(ActionEvent e)
  {
    confirmClose();
  }

  private void confirmClose()
  {
    // if modified
    if (dialogManager.showConfirmDialog(parent, TITLE, MESSAGE)) {
      close();
    }
  }

  private static void close()
  {
  }
}
