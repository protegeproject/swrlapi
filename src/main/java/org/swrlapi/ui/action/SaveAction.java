package org.swrlapi.ui.action;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedOntologyModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveAction implements ActionListener
{
  @NonNull private final Component parent;
  private final @NonNull SWRLRuleEngineDialogManager dialogManager;
  private final @NonNull FileBackedOntologyModel ontologyModel;

  public static final String SAVE_TITLE = "Save";
  private static final String ERROR_TITLE = "Error";

  public SaveAction(@NonNull Component parent, @NonNull FileBackedOntologyModel ontologyModel,
    @NonNull SWRLRuleEngineDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
    this.ontologyModel = ontologyModel;
  }

  @Override public void actionPerformed(@NonNull ActionEvent e)
  {
    save();
  }

  private void save()
  {
    try {
      this.ontologyModel.save();
    } catch (OWLOntologyStorageException e) {
      this.dialogManager.showErrorMessageDialog(this.parent, e.getMessage(), ERROR_TITLE);
    }
  }
}
