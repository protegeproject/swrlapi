package org.swrlapi.ui.view.rules;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class AssertedOWLAxiomsView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SWRLRuleEngineModel ruleEngineModel;
  @NonNull private final AssertedAxiomsTableModel assertedAxiomsTableModel;
  @NonNull private final JTable table;

  public AssertedOWLAxiomsView(@NonNull SWRLRuleEngineModel ruleEngineModel)
  {
    this.ruleEngineModel = ruleEngineModel;
    this.assertedAxiomsTableModel = new AssertedAxiomsTableModel();
    this.table = new JTable(this.assertedAxiomsTableModel);
  }

  @Override public void initialize()
  {
    setLayout(new BorderLayout());

    JScrollPane scrollPane = new JScrollPane(table);
    JViewport viewPort = scrollPane.getViewport();
    viewPort.setBackground(table.getBackground());

    add(BorderLayout.CENTER, scrollPane);
  }

  @Override
  public void validate()
  {
    this.assertedAxiomsTableModel.fireTableDataChanged();
    super.validate();
  }

  @Override
  public void update()
  {
    validate();
  }

  @NonNull private SWRLRuleEngine getSWRLRuleEngine() { return this.ruleEngineModel.getSWRLRuleEngine(); }

  private class AssertedAxiomsTableModel extends AbstractTableModel
  {
    private static final long serialVersionUID = 1L;

    @Override
    public int getRowCount()
    {
      return AssertedOWLAxiomsView.this.getSWRLRuleEngine().getNumberOfAssertedOWLAxioms();
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
      else
        return AssertedOWLAxiomsView.this.getSWRLRuleEngine().getAssertedOWLAxioms().toArray()[row];
    }
  }
}
