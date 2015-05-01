package org.swrlapi.ui.view.rules;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class AssertedOWLAxiomsView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private final SWRLRuleEngine ruleEngine;
  private final AssertedAxiomsTableModel assertedAxiomsTableModel;

  public AssertedOWLAxiomsView(SWRLRuleEngine ruleEngine)
  {
    this.ruleEngine = ruleEngine;
    this.assertedAxiomsTableModel = new AssertedAxiomsTableModel();
    JTable table = new JTable(this.assertedAxiomsTableModel);

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

  private class AssertedAxiomsTableModel extends AbstractTableModel
  {
    private static final long serialVersionUID = 1L;

    @Override
    public int getRowCount()
    {
      return AssertedOWLAxiomsView.this.ruleEngine.getNumberOfAssertedOWLAxioms();
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
        return AssertedOWLAxiomsView.this.ruleEngine.getAssertedOWLAxioms().toArray()[row];
    }
  }
}
