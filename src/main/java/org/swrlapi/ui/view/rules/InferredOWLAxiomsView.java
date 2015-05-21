package org.swrlapi.ui.view.rules;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class InferredOWLAxiomsView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SWRLRuleEngine ruleEngine;
  @NonNull private final InferredAxiomsTableModel inferredAxiomsTableModel;

  public InferredOWLAxiomsView(@NonNull SWRLRuleEngine ruleEngine)
  {
    this.ruleEngine = ruleEngine;
    this.inferredAxiomsTableModel = new InferredAxiomsTableModel();

    JTable inferredAxiomsTable = new JTable(this.inferredAxiomsTableModel);
    JScrollPane scrollPane = new JScrollPane(inferredAxiomsTable);
    JViewport viewPort = scrollPane.getViewport();
    setLayout(new BorderLayout());
    viewPort.setBackground(inferredAxiomsTable.getBackground());
    add(BorderLayout.CENTER, scrollPane);
  }

  @Override
  public void validate()
  {
    this.inferredAxiomsTableModel.fireTableDataChanged();
    super.validate();
  }

  @Override
  public void update()
  {
    validate();
  }

  private class InferredAxiomsTableModel extends AbstractTableModel
  {
    private static final long serialVersionUID = 1L;

    @Override
    public int getRowCount()
    {
      return InferredOWLAxiomsView.this.ruleEngine.getNumberOfInferredOWLAxioms();
    }

    @Override
    public int getColumnCount()
    {
      return 1;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
      if (row < 0 || row >= getRowCount())
        return "OUT OF BOUNDS";
      else {
        return InferredOWLAxiomsView.this.ruleEngine.getInferredOWLAxioms().toArray()[row];
      }
    }
  }
}
