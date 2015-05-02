package org.swrlapi.ui.action;

import org.swrlapi.ui.dialog.SWRLAPIDialogManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitAction implements ActionListener
{
  final Component parent;
  final SWRLAPIDialogManager dialogManager;

  public static final String TITLE = "Quit";
  private static final String MESSAGE = "Do you really want to quit?";

  public QuitAction(Component parent, SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
  }

  @Override public void actionPerformed(ActionEvent e)
  {
    confirmQuit();
  }

  public void confirmQuit()
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
