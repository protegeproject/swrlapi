package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitAction implements ActionListener
{
  @NonNull private final Component parent;
  @NonNull private final FileBackedOntologyModel ontologyModel;
  @NonNull private final SWRLAPIDialogManager dialogManager;

  public static final String QUIT_TITLE = "Quit";
  private static final String MESSAGE = "Do you really want to quit?";

  public QuitAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
    @NonNull SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.ontologyModel = ontologyModel;
    this.dialogManager = dialogManager;
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
    confirmQuit();
  }

  public void confirmQuit()
  {
    if (!this.ontologyModel.hasOntologyChanged() || this.dialogManager.showConfirmDialog(parent, QUIT_TITLE, MESSAGE)) {
      quit();
    }
  }

  private void quit()
  {
    // TODO
  }
}
