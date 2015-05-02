package org.swrlapi.ui.action;

import org.swrlapi.ui.dialog.SWRLAPIDialogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveAction implements ActionListener
{
  final Component parent;
  final SWRLAPIDialogManager dialogManager;

  public static final String TITLE = "Save";
  private static final String MESSAGE = "Save Ontology";

  public SaveAction(Component parent, SWRLAPIDialogManager dialogManager)
  {
    this.parent = parent;
    this.dialogManager = dialogManager;
  }

  @Override public void actionPerformed(ActionEvent e)
  {
    save();
  }

  public void save()
  {
      // clear modified status

      //https://github.com/owlcs/owlapi/blob/version4/contract/src/test/java/org/semanticweb/owlapi/examples/Examples.java#L167
    //this.dialogManager().showErrorMessageDialog(parent, ex.getMessage());
  }
}
