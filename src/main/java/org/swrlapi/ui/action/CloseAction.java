package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseAction implements ActionListener
{
  public static final String CLOSE_TITLE = "Close";

  private static final String MESSAGE = "Do you really want to close the ontology?";
  private static final String ERROR_TITLE = "Error";

  @NonNull private final Component parent;
  @NonNull private final FileBackedOntologyModel ontologyModel;
  @NonNull private final SWRLRuleEngineDialogManager dialogManager;

  public CloseAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
      @NonNull SWRLRuleEngineDialogManager dialogManager)
  {
    this.parent = parent;
    this.ontologyModel = ontologyModel;
    this.dialogManager = dialogManager;
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
    confirmClose();
  }

  private void confirmClose()
  {
    if (!this.ontologyModel.hasOntologyChanged() || this.dialogManager
        .showConfirmDialog(parent, MESSAGE, CLOSE_TITLE)) {
      close();
    }
  }

  private void close()
  {
    try {
      this.ontologyModel.close();
    } catch (OWLOntologyCreationException e) {
      this.dialogManager.showErrorMessageDialog(this.parent, e.getMessage() != null ? e.getMessage() : "", ERROR_TITLE);
    }
  }
}
