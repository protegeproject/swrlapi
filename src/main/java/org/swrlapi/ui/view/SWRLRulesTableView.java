package org.swrlapi.ui.view;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.action.DisableAllRulesAction;
import org.swrlapi.ui.action.EnableAllRulesAction;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesAndSQWRLQueriesTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 * Provides a model for graphical display of SWRL rules or SQWRL queries.
 *
 * @see SWRLRulesAndSQWRLQueriesTableModel
 */
public class SWRLRulesTableView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(SWRLRulesTableView.class);

  private static final String NEW_BUTTON_TITLE = "New";
  private static final String EDIT_BUTTON_TITLE = "Edit";
  private static final String DELETE_BUTTON_TITLE = "Delete";
  private static final String CLONE_BUTTON_TITLE = "Clone";

  private static final int ACTIVE_COLUMN_PREFERRED_WIDTH = 30;
  private static final int ACTIVE_COLUMN_MAX_WIDTH = 50;
  private static final int RULE_NAME_COLUMN_PREFERRED_WIDTH = 150;
  private static final int RULE_NAME_COLUMN_MAX_WIDTH = 200;
  private static final int RULE_TEXT_COLUMN_PREFERRED_WIDTH = 500;
  private static final int RULE_TEXT_COLUMN_MAX_WIDTH = 1400;
  private static final int COMMENT_COLUMN_PREFERRED_WIDTH = 200;
  private static final int COMMENT_COLUMN_MAX_WIDTH = 300;

  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private final SWRLRulesAndSQWRLQueriesTableModel tableModel;
  @NonNull private final SWRLRuleEngineDialogManager dialogManager;
  @NonNull private final JTable swrlRulesTable;
  @NonNull private final JButton newButton, cloneButton, editButton, deleteButton;

  public SWRLRulesTableView(@NonNull SWRLRuleEngineModel swrlRuleEngineModel,
    @NonNull SWRLRuleEngineDialogManager dialogManager)
  {
    this.swrlRuleEngineModel = swrlRuleEngineModel;
    this.dialogManager = dialogManager;
    this.tableModel = swrlRuleEngineModel.getSWRLRulesTableModel();
    this.swrlRulesTable = new JTable(tableModel);
    this.swrlRulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.deleteButton = new JButton(DELETE_BUTTON_TITLE);
    this.editButton = new JButton(EDIT_BUTTON_TITLE);
    this.cloneButton = new JButton(CLONE_BUTTON_TITLE);
    this.newButton = new JButton(NEW_BUTTON_TITLE);
  }

  @Override public void initialize()
  {
    this.swrlRuleEngineModel.getSWRLRulesTableModel().setView(this);

    addTableListeners();

    setPreferredColumnWidths();

    createComponents(this.dialogManager);

    createPopupMenu();
  }

  @Override public void update()
  {
    getSWRLRulesTableModel().fireTableDataChanged();
    validate();
  }

  public Optional<@NonNull String> getSelectedSWRLRuleName()
  {
    int selectedRow = this.swrlRulesTable.getSelectedRow();

    if (selectedRow != -1)
      return Optional.of(getSWRLRulesTableModel().getSWRLRuleNameByIndex(selectedRow));
    else
      return Optional.<@NonNull String>empty();
  }

  private Optional<@NonNull String> getSelectedSWRLRuleText()
  {
    int selectedRow = this.swrlRulesTable.getSelectedRow();

    if (selectedRow != -1) {
      String ruleText = getSWRLRulesTableModel().getSWRLRuleTextByIndex(selectedRow);
      return Optional.of(ruleText.replaceAll(Character.toString(SWRLParser.RING_CHAR), " . "));
    } else
      return Optional.<@NonNull String>empty();
  }

  private Optional<@NonNull String> getSelectedSWRLRuleComment()
  {
    int selectedRow = this.swrlRulesTable.getSelectedRow();

    if (selectedRow != -1)
      return Optional.of(getSWRLRulesTableModel().getSWRLRuleCommentByIndex(selectedRow));
    else
      return Optional.<@NonNull String>empty();
  }

  private void setPreferredColumnWidths()
  {
    TableColumnModel columnModel = this.swrlRulesTable.getColumnModel();

    if (this.tableModel.hasRuleActiveColumn()) {
      columnModel.getColumn(this.tableModel.getRuleActiveColumnNumber())
        .setPreferredWidth(ACTIVE_COLUMN_PREFERRED_WIDTH);
      columnModel.getColumn(this.tableModel.getRuleActiveColumnNumber()).setMaxWidth(ACTIVE_COLUMN_MAX_WIDTH);
    }
    columnModel.getColumn(this.tableModel.getRuleNameColumnNumber())
      .setPreferredWidth(RULE_NAME_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(this.tableModel.getRuleNameColumnNumber()).setMaxWidth(RULE_NAME_COLUMN_MAX_WIDTH);
    columnModel.getColumn(this.tableModel.getRuleTextColumnNumber())
      .setPreferredWidth(RULE_TEXT_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(this.tableModel.getRuleTextColumnNumber()).setMaxWidth(RULE_TEXT_COLUMN_MAX_WIDTH);
    columnModel.getColumn(this.tableModel.getRuleCommentColumnNumber())
      .setPreferredWidth(COMMENT_COLUMN_PREFERRED_WIDTH);
    columnModel.getColumn(this.tableModel.getRuleCommentColumnNumber()).setMaxWidth(COMMENT_COLUMN_MAX_WIDTH);
  }

  private void addTableListeners()
  {
    this.swrlRulesTable.addMouseListener(new MouseAdapter()
    {
      @Override public void mouseClicked(@NonNull MouseEvent e)
      {
        if (e.getClickCount() == 2) {
          if (e.getSource() == SWRLRulesTableView.this.swrlRulesTable)
            editSelectedSWRLRule();
        }
      }
    });

    this.swrlRulesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
    {
      @Override public void valueChanged(ListSelectionEvent e)
      {
        if (hasSelectedRule())
          enableEditAndCloneAndDelete();
        else
          disableEditAndCloneAndDelete();
      }
    });
  }

  private void editSelectedSWRLRule()
  {
    if (this.swrlRulesTable.getSelectedRow() != -1) {
      String ruleName = getSelectedSWRLRuleName().get();
      String ruleText = getSelectedSWRLRuleText().get();
      String ruleComment = getSelectedSWRLRuleComment().get();

      this.dialogManager.getSWRLRuleEditorDialog(this, ruleName, ruleText, ruleComment).setVisible(true);
    }
  }

  private void cloneSelectedSWRLRule()
  {
    if (this.swrlRulesTable.getSelectedRow() != -1) {
      Optional<String> ruleName = getSWRLRuleEngineModel().getNextRuleName();
      if (ruleName.isPresent()) {
        String ruleText = getSelectedSWRLRuleText().get();
        String ruleComment = getSelectedSWRLRuleComment().get();

        try {
          getSWRLRuleEngineModel().getSWRLRuleEngine().createSWRLRule(ruleName.get(), ruleText, ruleComment, true);
          getSWRLRuleEngineModel().updateView();
        } catch (SWRLParseException | SWRLBuiltInException e) {
          log.warn("Error cloning rule " + ruleName + ": " + e.getMessage());
        }
      }
    }
  }

  private void createComponents(SWRLRuleEngineDialogManager dialogManager)
  {
    JScrollPane scrollPane = new JScrollPane(this.swrlRulesTable);
    JViewport viewport = scrollPane.getViewport();

    setLayout(new BorderLayout());

    JPanel headingPanel = new JPanel(new BorderLayout());
    add(headingPanel, BorderLayout.SOUTH);

    viewport.setBackground(this.swrlRulesTable.getBackground());

    JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
    headingPanel.add(buttonPanel, BorderLayout.EAST);

    this.newButton.addActionListener(new NewSWRLRuleActionListener(this, dialogManager));
    buttonPanel.add(newButton);

    this.editButton.addActionListener(new EditSWRLRuleActionListener(this, dialogManager));
    buttonPanel.add(this.editButton);

    this.cloneButton.addActionListener(new CloneSWRLRuleActionListener(this, dialogManager));
    buttonPanel.add(this.cloneButton);

    this.deleteButton.addActionListener(new DeleteSWRLRuleActionListener(this, dialogManager));
    buttonPanel.add(this.deleteButton);

    disableEditAndCloneAndDelete(); // Will get enabled by listener on rule table if a rule is selected

    add(scrollPane, BorderLayout.CENTER);

    validate();
  }

  private void enableEditAndCloneAndDelete()
  {
    this.editButton.setEnabled(true);
    this.cloneButton.setEnabled(true);
    this.deleteButton.setEnabled(true);
  }

  private void disableEditAndCloneAndDelete()
  {
    this.editButton.setEnabled(false);
    this.cloneButton.setEnabled(false);
    this.deleteButton.setEnabled(false);
  }

  private boolean hasSelectedRule()
  {
    return this.swrlRulesTable.getSelectedRow() != -1;
  }

  private @NonNull SWRLRulesAndSQWRLQueriesTableModel getSWRLRulesTableModel()
  {
    return this.swrlRuleEngineModel.getSWRLRulesTableModel();
  }

  @NonNull private SWRLRuleEngineModel getSWRLRuleEngineModel()
  {
    return this.swrlRuleEngineModel;
  }

  private abstract class ActionListenerBase implements ActionListener
  {
    @NonNull protected final SWRLRuleEngineDialogManager dialogManager;
    @NonNull protected final Component parent;

    protected ActionListenerBase(@NonNull Component parent, @NonNull SWRLRuleEngineDialogManager dialogManager)
    {
      this.parent = parent;
      this.dialogManager = dialogManager;
    }
  }

  private class NewSWRLRuleActionListener extends ActionListenerBase
  {
    public NewSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLRuleEngineDialogManager dialogManager)
    {
      super(parent, dialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      this.dialogManager.getSWRLRuleEditorDialog(this.parent).setVisible(true);
    }
  }

  private class EditSWRLRuleActionListener extends ActionListenerBase
  {
    public EditSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLRuleEngineDialogManager dialogManager)
    {
      super(parent, dialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      editSelectedSWRLRule();
    }
  }

  private class CloneSWRLRuleActionListener extends ActionListenerBase
  {
    public CloneSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLRuleEngineDialogManager dialogManager)
    {
      super(parent, dialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      cloneSelectedSWRLRule();
    }
  }

  private class DeleteSWRLRuleActionListener extends ActionListenerBase
  {
    public DeleteSWRLRuleActionListener(@NonNull Component parent, @NonNull SWRLRuleEngineDialogManager dialogManager)
    {
      super(parent, dialogManager);
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      deleteSelectedSWRLRule();
    }

    private void deleteSelectedSWRLRule()
    {
      Optional<@NonNull String> selectedRuleName = getSelectedSWRLRuleName();

      if (selectedRuleName.isPresent()) {
        if (SWRLRulesTableView.this.getSWRLRulesTableModel().hasSWRLRule(selectedRuleName.get()) && this.dialogManager
          .showConfirmDialog(this.parent, "Do you really want to delete the rule?", "Delete Rule")) {
          getSWRLRuleEngineModel().getSWRLRuleEngine().deleteSWRLRule(selectedRuleName.get());
          getSWRLRuleEngineModel().updateView();
        }
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
    @NonNull private final JPopupMenu popup;

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
