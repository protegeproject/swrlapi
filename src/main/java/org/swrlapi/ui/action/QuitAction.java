package org.swrlapi.ui.action;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitAction implements ActionListener
{
  public static final String QUIT_TITLE = "Quit";

  private static final String MESSAGE = "Do you really want to quit?";

  @NonNull private final Component parent;
  @NonNull private final FileBackedOntologyModel ontologyModel;
  @NonNull private final SWRLRuleEngineDialogManager dialogManager;

  public QuitAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
    @NonNull SWRLRuleEngineDialogManager dialogManager)
  {
    this.parent = parent;
    this.ontologyModel = ontologyModel;
    this.dialogManager = dialogManager;
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
    confirmQuit();
  }

  private void confirmQuit()
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
