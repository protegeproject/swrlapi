package org.swrlapi.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveAsAction implements ActionListener
{
  public SaveAsAction()
  {
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    save();
  }

  public void save()
  {
    // JFileChooser fileChooser = getApplicationDialogManager()
    // .createSaveFileChooser("Save Mapping Ontology", "owl", true);
    //
    // if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    // File file = fileChooser.getSelectedFile();
    // String fileName = file.getAbsolutePath();
    //
    // if (!fileName.endsWith(".owl"))
    // fileName = fileName.concat(".owl");
    //
    // try {
    // application.getMappingExpressionsPersistenceLayer().putMappingExpressions(mappingExpressions, fileName);
    // applicationModel.setMappingFileName(fileName);
    // applicationModel.clearModifiedStatus();
    // } catch (MappingMasterException ex) {
    // getApplicationDialogManager().showErrorMessageDialog(applicationView, ex.getMessage());
    // }
    // }
  }
}
