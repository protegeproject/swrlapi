package org.swrlapi.ui.view.rules;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class InferredOWLAxiomsView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SWRLRuleEngineModel ruleEngineModel;
  @NonNull private final InferredAxiomsTableModel inferredAxiomsTableModel;

  public InferredOWLAxiomsView(@NonNull SWRLRuleEngineModel ruleEngineModel)
  {
    this.ruleEngineModel = ruleEngineModel;
    this.inferredAxiomsTableModel = new InferredAxiomsTableModel();
  }

  @Override public void initialize()
  {
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

  @NonNull private SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.ruleEngineModel.getSWRLRuleEngine();
  }

  private class InferredAxiomsTableModel extends AbstractTableModel
  {
    private static final long serialVersionUID = 1L;

    @Override
    public int getRowCount()
    {
      return InferredOWLAxiomsView.this.getSWRLRuleEngine().getNumberOfInferredOWLAxioms();
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
        return InferredOWLAxiomsView.this.getSWRLRuleEngine().getInferredOWLAxioms().toArray()[row];
      }
    }
  }
}
