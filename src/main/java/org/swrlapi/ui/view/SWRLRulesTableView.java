package org.swrlapi.ui.view;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.action.DisableAllRulesAction;
import org.swrlapi.ui.action.EnableAllRulesAction;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Provides a model for graphical display of SWRL rules or SQWRL queries.
 *
 * @see org.swrlapi.ui.model.SWRLRulesTableModel
 */
public class SWRLRulesTableView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private static final int ACTIVE_COLUMN_PREFERRED_WIDTH = 30;
  private static final int ACTIVE_COLUMN_MAX_WIDTH = 50;
  private static final int RULE_NAME_COLUMN_PREFERRED_WIDTH = 150;
  private static final int RULE_NAME_COLUMN_MAX_WIDTH = 200;
  private static final int RULE_TEXT_COLUMN_PREFERRED_WIDTH = 500;
  private static final int RULE_TEXT_COLUMN_MAX_WIDTH = 1400;
  private static final int COMMENT_COLUMN_PREFERRED_WIDTH = 200;
  private static final int COMMENT_COLUMN_MAX_WIDTH = 300;

  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private final SWRLAPIDialogManager dialogManager;
  @NonNull private final SWRLRulesTableModel swrlRulesTableModel;
  @NonNull private final JTable swrlRulesTable;

  private JButton editButton, deleteButton;

  public SWRLRulesTableView(@NonNull SWRLRuleEngineModel swrlRuleEngineModel,
    @NonNull SWRLAPIDialogManager dialogManager)
  {
    this.swrlRuleEngineModel = swrlRuleEngineModel;
    this.dialogManager = dialogManager;
    this.swrlRulesTableModel = swrlRuleEngineModel.getSWRLRulesTableModel();
    this.swrlRulesTable = new JTable(this.swrlRulesTableModel);
    this.swrlRulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    addTableListeners();
    setPreferredColumnWidths();
    this.swrlRulesTableModel.setView(this);
    createComponents(dialogManager);
    createPopupMenu();
  }

  @Override public void update()
  {
    this.swrlRulesTableModel.fireTableDataChanged();
    validate();
  }

  @NonNull public String getSelectedSWRLRuleName()
  {
    int selectedRow = this.swrlRulesTable.getSelectedRow();

    if (selectedRow != -1)
      return this.swrlRulesTableModel.getSWRLRuleNameByIndex(selectedRow);
    else
      return "";
  }

  @NonNull private String getSelectedSWRLRuleText()
  {
    int selectedRow = this.swrlRulesTable.getSelectedRow();

    if (selectedRow != -1)
      return this.swrlRulesTableModel.getSWRLRuleTextByIndex(selectedRow);
    else
      return "";
  }

  @NonNull private String getSelectedSWRLRuleComment()
  {
    int selectedRow = this.swrlRulesTable.getSelectedRow();

    if (selectedRow != -1)
      return this.swrlRulesTableModel.getSWRLRuleCommentByIndex(selectedRow);
    else
      return "";
  }

  private void setPreferredColumnWidths()
  {
    TableColumnModel columnModel = this.swrlRulesTable.getColumnModel();

    columnModel.getColumn(SWRLRulesTableModel.ACTIVE_COLUMN).setPreferredWidth(ACTIVE_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.ACTIVE_COLUMN).setMaxWidth(ACTIVE_COLUMN_MAX_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.RULE_NAME_COLUMN).setPreferredWidth(RULE_NAME_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.RULE_NAME_COLUMN).setMaxWidth(RULE_NAME_COLUMN_MAX_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.RULE_TEXT_COLUMN).setPreferredWidth(RULE_TEXT_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.RULE_TEXT_COLUMN).setMaxWidth(RULE_TEXT_COLUMN_MAX_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.RULE_COMMENT_COLUMN).setPreferredWidth(COMMENT_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(SWRLRulesTableModel.RULE_COMMENT_COLUMN).setMaxWidth(COMMENT_COLUMN_MAX_WIDTH);
  }

  private void addTableListeners()
  {
    this.swrlRulesTable.addMouseListener(new MouseAdapter()
    {
      @Override public void mouseClicked(@NonNull MouseEvent e)
      {
        if (e.getClickCount() == 2) {
          if (e.getSource() == SWRLRulesTableView.this.swrlRulesTable) {
            editSelectedSWRLRule();
          }
        }
      }
    });

    this.swrlRulesTable.getSelectionModel().addListSelectionListener(e -> {
      if (hasSelectedRule())
        enableEditAndDelete();
      else
        disableEditAndDelete();
    });
  }

  private void editSelectedSWRLRule()
  {
    if (this.swrlRulesTable.getSelectedRow() != -1) {
      String ruleName = getSelectedSWRLRuleName();
      String ruleText = getSelectedSWRLRuleText();
      String ruleComment = getSelectedSWRLRuleComment();

      this.dialogManager.getSWRLRuleEditorDialog(this, ruleName, ruleText, ruleComment).setVisible(true);
    }
  }

  private void createComponents(SWRLAPIDialogManager applicationDialogManager)
  {
    JScrollPane scrollPane = new JScrollPane(this.swrlRulesTable);
    JViewport viewport = scrollPane.getViewport();

    setLayout(new BorderLayout());

    JPanel headingPanel = new JPanel(new BorderLayout());
    add(headingPanel, BorderLayout.SOUTH);

    viewport.setBackground(this.swrlRulesTable.getBackground());

    JPanel buttonPanel = new JPanel(new BorderLayout());
    headingPanel.add(buttonPanel, BorderLayout.EAST);

    JButton newButton = new JButton("New");
    newButton.addActionListener(new NewSWRLRuleActionListener(this, applicationDialogManager));
    buttonPanel.add(newButton, BorderLayout.WEST);

    this.editButton = new JButton("Edit");
    this.editButton.addActionListener(new EditSWRLRuleActionListener(this, applicationDialogManager));
    buttonPanel.add(this.editButton, BorderLayout.CENTER);

    this.deleteButton = new JButton("Delete");
    this.deleteButton.addActionListener(new DeleteSWRLRuleActionListener(this, applicationDialogManager));
    buttonPanel.add(this.deleteButton, BorderLayout.EAST);

    disableEditAndDelete(); // Will get enabled by listener on rule table if a rule is selected

    add(scrollPane, BorderLayout.CENTER);

    validate();
  }

  private void enableEditAndDelete()
  {
    this.editButton.setEnabled(true);
    this.deleteButton.setEnabled(true);
  }

  private void disableEditAndDelete()
  {
    this.editButton.setEnabled(false);
    this.deleteButton.setEnabled(false);
  }

  private boolean hasSelectedRule()
  {
    return this.swrlRulesTable.getSelectedRow() != -1;
  }

  @NonNull private SWRLRuleEngineModel getSWRLRuleEngineModel()
  {
    return this.swrlRuleEngineModel;
  }

  private abstract class ActionListenerBase implements ActionListener
  {
    @NonNull protected final SWRLAPIDialogManager applicationDialogManager;
    @NonNull protected final Component parent;

    protected ActionListenerBase(@NonNull Component parent, @NonNull SWRLAPIDialogManager applicationDialogManager)
    {
      this.parent = parent;
      this.applicationDialogManager = applicationDialogManager;
    }
  }

  private class NewSWRLRuleActionListener extends ActionListenerBase
  {
    public NewSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLAPIDialogManager applicationDialogManager)
    {
      super(parent, applicationDialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      this.applicationDialogManager.getSWRLRuleEditorDialog(this.parent).setVisible(true);
    }
  }

  private class EditSWRLRuleActionListener extends ActionListenerBase
  {
    public EditSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLAPIDialogManager applicationDialogManager)
    {
      super(parent, applicationDialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      editSelectedSWRLRule();
    }
  }

  private class DeleteSWRLRuleActionListener extends ActionListenerBase
  {
    public DeleteSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLAPIDialogManager applicationDialogManager)
    {
      super(parent, applicationDialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      deleteSelectedSWRLRule();
    }

    private void deleteSelectedSWRLRule()
    {
      String selectedRuleName = getSelectedSWRLRuleName();

      if (SWRLRulesTableView.this.swrlRulesTableModel.hasSWRLRule(selectedRuleName) && this.applicationDialogManager
        .showConfirmDialog(this.parent, "Do you really want to delete the rule?", "Delete Rule")) {
        SWRLRulesTableView.this.swrlRulesTableModel.removeSWRLRule(selectedRuleName);
        getSWRLRuleEngineModel().getSWRLRuleEngine().deleteSWRLRule(selectedRuleName);
      }
    }
  }

  private void createPopupMenu()
  {
    JPopupMenu popup = new JPopupMenu();
    popup.add(new EnableAllRulesAction());
    popup.add(new DisableAllRulesAction());
    addMouseListener(new PopupListener(popup));
  }

  private class PopupListener extends MouseAdapter
  {
    @NonNull final JPopupMenu popup;

    public PopupListener(@NonNull JPopupMenu popupMenu)
    {
      this.popup = popupMenu;
    }

    @Override public void mousePressed(@NonNull MouseEvent e)
    {
      maybeShowPopup(e);
    }

    @Override public void mouseReleased(@NonNull MouseEvent e)
    {
      maybeShowPopup(e);
    }

    private void maybeShowPopup(@NonNull MouseEvent e)
    {
      if (e.isPopupTrigger())
        this.popup.show(e.getComponent(), e.getX(), e.getY());
    }
  }
}
