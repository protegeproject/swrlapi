package org.swrlapi.ui.dialog;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.parser.SWRLIncompleteRuleException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesAndSQWRLQueriesTableModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Modal dialog providing a SWRL rule and SQWRL query editor.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class SWRLRuleEditorDialog extends JDialog implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(SWRLRuleEditorDialog.class);

  private static final String TITLE = "Edit";
  private static final String RULE_NAME_TITLE = "Name";
  private static final String COMMENT_LABEL_TITLE = "Comment";
  private static final String STATUS_LABEL_TITLE = "Status";
  private static final String OK_BUTTON_TITLE = "Ok";
  private static final String CANCEL_BUTTON_TITLE = "Cancel";
  private static final String STATUS_OK = "Ok";
  private static final String STATUS_NO_RULE_TEXT =
    "Use Tab key to cycle through auto-completions;" + " use Escape key to remove auto-complete expansion";
  private static final String INVALID_RULE_TITLE = "Invalid";
  private static final String MISSING_RULE = "Nothing to save!";
  private static final String MISSING_RULE_NAME_TITLE = "Empty Name";
  private static final String MISSING_RULE_NAME = "A name must be supplied!";
  private static final String QUIT_CONFIRM_TITLE = "Unsaved Changes";
  private static final String QUIT_CONFIRM_MESSAGE = "Are you sure you want discard your changes?";
  private static final String DUPLICATE_RULE_TEXT = "Name already in use - please pick another name.";
  private static final String DUPLICATE_RULE_TITLE = "Duplicate Name";
  private static final String INTERNAL_ERROR_TITLE = "Internal Error";

  private static final int BUTTON_PREFERRED_WIDTH = 100;
  private static final int BUTTON_PREFERRED_HEIGHT = 30;
  private static final int RULE_EDIT_AREA_COLUMNS = 20;
  private static final int RULE_EDIT_AREA_ROWS = 60;

  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private final SWRLRuleEngineDialogManager dialogManager;
  @NonNull private final SWRLRuleEditorInitialDialogState initialDialogState = new SWRLRuleEditorInitialDialogState();
  @NonNull private final JTextField ruleNameTextField, commentTextField, statusTextField;
  @NonNull private final JTextArea ruleTextTextArea;
  @NonNull private final JButton saveButton, cancelButton;
  @NonNull private final Border loweredBevelBorder;
  @NonNull private final Border yellowBorder;

  @NonNull private Optional<@NonNull SWRLRuleEditorAutoCompleteState> autoCompleteState = Optional.<@NonNull SWRLRuleEditorAutoCompleteState>empty(); // Present if auto-complete
  private boolean editMode = false;

  public SWRLRuleEditorDialog(@NonNull SWRLRuleEngineModel swrlRuleEngineModel,
    @NonNull SWRLRuleEngineDialogManager dialogManager)
  {
    this.swrlRuleEngineModel = swrlRuleEngineModel;
    this.dialogManager = dialogManager;
    this.loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
    this.yellowBorder = BorderFactory.createLineBorder(Color.YELLOW);
    this.ruleTextTextArea = new JTextArea("", RULE_EDIT_AREA_COLUMNS, RULE_EDIT_AREA_ROWS);
    this.saveButton = new JButton(OK_BUTTON_TITLE);
    this.cancelButton = new JButton(CANCEL_BUTTON_TITLE);
    this.ruleNameTextField = new JTextField("");
    this.commentTextField = new JTextField("");
    this.statusTextField = new JTextField("");
  }

  @Override public void initialize()
  {
    Container contentPane = getContentPane();
    setTitle(TITLE);
    setModal(true);

    initializeComponents();

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    addWindowListener(new CloseWindowListener(contentPane));

    this.ruleTextTextArea.addKeyListener(new SWRLRuleEditorKeyAdapter());
    this.cancelButton.addActionListener(new CancelSWRLRuleEditActionListener(contentPane));
    this.saveButton.addActionListener(new SaveSWRLRuleActionListener(contentPane));
  }

  @Override public void setVisible(boolean b)
  {
    if (b) {
      setInitialDialogState();
      disableAutoCompleteModeIfNecessary();
      updateStatus();
    }
    super.setVisible(b);
  }

  public void setCreateMode()
  {
    cancelEditMode();

    Optional<String> autogeneratedRuleName = this.swrlRuleEngineModel.getNextRuleName();
    if (autogeneratedRuleName.isPresent()) {
      this.ruleNameTextField.setText(autogeneratedRuleName.get());
      this.ruleNameTextField.selectAll();
    }
    this.statusTextField.setText(""); // setVisible will set appropriate initial text
  }

  public void setEditMode(@NonNull String ruleName, @NonNull String ruleText, @NonNull String comment)
  {
    cancelEditMode();

    this.ruleNameTextField.setText(ruleName);
    //    this.ruleNameTextField.setCaretPosition(this.ruleNameTextField.getText().length());
    this.ruleTextTextArea.setText(ruleText);
    this.commentTextField.setText(comment);
    this.statusTextField.setText(""); // setVisible will set appropriate initial text

    this.editMode = true;
  }

  @Override public void update() { updateStatus();}

  private void updateStatus()
  {
    String ruleText = getRuleText();

    if (ruleText.isEmpty()) {
      setInformationalStatusText(STATUS_NO_RULE_TEXT);
      disableSave();
    } else {
      try {
        createSWRLParser().parseSWRLRule(ruleText, true, getRuleName(), getComment());
        this.ruleTextTextArea.requestFocus();
        setInformationalStatusText(STATUS_OK);
        enableSave();
      } catch (SWRLIncompleteRuleException e) {
        setIncompleteStatusText(e.getMessage() == null ? "" : e.getMessage());
        disableSave();
      } catch (SWRLParseException e) {
        setErrorStatusText(e.getMessage() == null ? "" : e.getMessage());
        disableSave();
      } catch (RuntimeException e) {
        setInformationalStatusText(e.getMessage() == null ? "" : e.getMessage());
        disableSave();
      }
    }
  }

  private void cancelEditMode()
  {
    this.ruleNameTextField.setText("");
    this.ruleNameTextField.setEnabled(true);
    this.ruleTextTextArea.setText("");
    this.ruleTextTextArea.setEnabled(true);
    this.ruleTextTextArea.setText("");
    this.commentTextField.setText("");
    this.statusTextField.setText("");

    this.editMode = false;
  }

  private void initializeComponents()
  {
    JLabel ruleNameLabel = new JLabel(RULE_NAME_TITLE);
    JLabel commentLabel = new JLabel(COMMENT_LABEL_TITLE);
    JLabel statusLabel = new JLabel(STATUS_LABEL_TITLE);
    JPanel upperPanel = new JPanel(new GridLayout(6, 2));
    JPanel rulePanel = new JPanel(new GridLayout(1, 1));
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel surroundPanel = new JPanel(new BorderLayout());
    Container contentPane = getContentPane();

    this.ruleNameTextField.setBorder(this.loweredBevelBorder);

    this.ruleTextTextArea.setLineWrap(true);
    this.ruleTextTextArea.setWrapStyleWord(true);
    this.ruleTextTextArea.setBorder(this.loweredBevelBorder);
    this.ruleTextTextArea.setPreferredSize(new Dimension(300, 300));

    this.commentTextField.setDisabledTextColor(Color.BLACK);
    this.commentTextField.setBorder(this.loweredBevelBorder);

    this.statusTextField.setDisabledTextColor(Color.BLACK);
    this.statusTextField.setEnabled(false);
    this.statusTextField.setBorder(this.loweredBevelBorder);

    this.cancelButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
    this.saveButton.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));

    contentPane.setLayout(new BorderLayout());

    upperPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
    upperPanel.add(ruleNameLabel);
    upperPanel.add(this.ruleNameTextField);
    upperPanel.add(commentLabel);
    upperPanel.add(this.commentTextField);
    upperPanel.add(statusLabel);
    upperPanel.add(this.statusTextField);

    rulePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    rulePanel.add(this.ruleTextTextArea);

    buttonPanel.add(cancelButton);
    buttonPanel.add(this.saveButton);

    surroundPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    contentPane.add(surroundPanel, BorderLayout.CENTER);
    surroundPanel.add(upperPanel, BorderLayout.NORTH);
    surroundPanel.add(rulePanel, BorderLayout.CENTER);
    surroundPanel.add(buttonPanel, BorderLayout.SOUTH);

    pack();
  }

  private void autoComplete()
  {
    if (!isInAutoCompleteMode()) {
      String ruleText = getRuleText();
      int textPosition = this.ruleTextTextArea.getCaretPosition();
      int i = SWRLParser.findSplittingPoint(ruleText.substring(0, textPosition));
      String prefix = ruleText.substring(i, textPosition);
      if (!prefix.equals("")) {
        List<@NonNull String> expansions = getExpansions(prefix); // All expansions will start with the empty string.

        if (expansions.size() > 1) { // More than the empty string expansion; if not, do not enter autoComplete mode
          SWRLRuleEditorAutoCompleteState state = new SWRLRuleEditorAutoCompleteState(textPosition, prefix, expansions);
          insertExpansion(textPosition, prefix, state.getNextExpansion()); // Skip the empty string
          enableAutoCompleteMode(state);
        }
      }
    } else { // Already in auto-complete mode
      int textPosition = this.autoCompleteState.get().getTextPosition();
      String prefix = this.autoCompleteState.get().getPrefix();
      String currentExpansion = this.autoCompleteState.get().getCurrentExpansion();
      String nextExpansion = this.autoCompleteState.get().getNextExpansion();

      replaceExpansion(textPosition, prefix, currentExpansion, nextExpansion);
    }
  }

  private boolean isInAutoCompleteMode()
  {
    return this.autoCompleteState.isPresent();
  }

  private void enableAutoCompleteMode(@NonNull SWRLRuleEditorAutoCompleteState autoCompleteState)
  {
    this.autoCompleteState = Optional.of(autoCompleteState);
  }

  private void disableAutoCompleteModeIfNecessary()
  {
    if (this.autoCompleteState.isPresent())
      disableAutoCompleteMode();
  }

  private void disableAutoCompleteMode()
  {
    this.autoCompleteState = Optional.<@NonNull SWRLRuleEditorAutoCompleteState>empty();
  }

  private void cancelAutoCompleteIfNecessary()
  {
    if (isInAutoCompleteMode()) {
      int textPosition = this.autoCompleteState.get().getTextPosition();
      String prefix = this.autoCompleteState.get().getPrefix();
      String currentExpansion = this.autoCompleteState.get().getCurrentExpansion();

      replaceExpansion(textPosition, prefix, currentExpansion, "");
      disableAutoCompleteMode();
    }
  }

  private void insertExpansion(int textPosition, @NonNull String prefix, @NonNull String expansion)
  {
    String expansionTail = expansion.substring(prefix.length());

    try {
      Document document = this.ruleTextTextArea.getDocument();
      if (document != null)
        document.insertString(textPosition, expansionTail, SimpleAttributeSet.EMPTY);
      else
        disableAutoCompleteMode();
    } catch (BadLocationException e) {
      disableAutoCompleteMode();
    }
  }

  private void replaceExpansion(int textPosition, @NonNull String prefix, @NonNull String currentExpansion,
    @NonNull String nextExpansion)
  {
    String currentExpansionTail = currentExpansion.isEmpty() ? "" : currentExpansion.substring(prefix.length());
    String nextExpansionTail = nextExpansion.isEmpty() ? "" : nextExpansion.substring(prefix.length());

    try {
      if (!currentExpansionTail.isEmpty())
        this.ruleTextTextArea.getDocument().remove(textPosition, currentExpansionTail.length());

      if (!nextExpansionTail.isEmpty())
        this.ruleTextTextArea.getDocument().insertString(textPosition, nextExpansionTail, SimpleAttributeSet.EMPTY);
    } catch (BadLocationException e) {
      disableAutoCompleteMode();
    }
  }

  @NonNull private List<@NonNull String> getExpansions(@NonNull String prefix)
  {
    List<@NonNull String> expansions = new ArrayList<>();

    expansions.add(""); // Add empty expansion that we can cycle back to
    expansions.addAll(createSWRLAutoCompleter().getCompletions(prefix));

    return expansions;
  }

  @NonNull private SWRLAutoCompleter createSWRLAutoCompleter()
  {
    return this.swrlRuleEngineModel.createSWRLAutoCompleter();
  }

  private void disableSave()
  {
    this.saveButton.setEnabled(false);
  }

  private void enableSave()
  {
    this.saveButton.setEnabled(true);
  }

  private void setInformationalStatusText(@NonNull String status)
  {
    this.statusTextField.setBorder(loweredBevelBorder);
    this.statusTextField.setDisabledTextColor(Color.BLACK);
    this.statusTextField.setText(status);
  }

  private void setIncompleteStatusText(@NonNull String status)
  {
    this.statusTextField.setBorder(yellowBorder);
    this.statusTextField.setDisabledTextColor(Color.BLACK);
    this.statusTextField.setText(status);
  }

  private void setErrorStatusText(@NonNull String status)
  {
    this.statusTextField.setDisabledTextColor(Color.RED);
    this.statusTextField.setText(status);
  }

  @NonNull private String getRuleName()
  {
    return this.ruleNameTextField.getText().trim();
  }

  @NonNull private String getComment()
  {
    return this.commentTextField.getText().trim();
  }

  @NonNull private String getRuleText()
  { // We replace the Unicode characters when parsing
    return this.ruleTextTextArea.getText().replaceAll(Character.toString(SWRLParser.RING_CHAR), ".");
  }

  @NonNull private SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.swrlRuleEngineModel.getSWRLRuleEngine();
  }

  @NonNull private SWRLRuleEngineModel getSWRLRuleEngineModel()
  {
    return this.swrlRuleEngineModel;
  }

  @NonNull private SWRLRuleEditorInitialDialogState getInitialDialogState() { return this.initialDialogState; }

  @NonNull private SWRLParser createSWRLParser()
  {
    return this.swrlRuleEngineModel.createSWRLParser();
  }

  private @NonNull SWRLRulesAndSQWRLQueriesTableModel getSWRLRulesTableModel()
  {
    return this.swrlRuleEngineModel.getSWRLRulesTableModel();
  }

  @NonNull private SWRLRuleEngineDialogManager getDialogManager()
  {
    return this.dialogManager;
  }

  private void setInitialDialogState()
  {
    this.initialDialogState.setState(getRuleName(), getComment(), getRuleText());
  }

  private boolean hasDialogStateChanged()
  {
    return this.initialDialogState.hasStateChanged(getRuleName(), getComment(), getRuleText());
  }

  private void closeIfOk(@NonNull Component parent)
  {
    boolean okToQuit = !hasDialogStateChanged() || getDialogManager()
      .showConfirmDialog(parent, QUIT_CONFIRM_MESSAGE, QUIT_CONFIRM_TITLE);

    if (okToQuit) {
      cancelEditMode();
      setVisible(false);
    }
  }

  private class SWRLRuleEditorKeyAdapter extends KeyAdapter
  {
    @Override public void keyPressed(@NonNull KeyEvent event)
    {
      int code = event.getKeyCode();
      if ((code == KeyEvent.VK_TAB) || (code == KeyEvent.VK_SPACE && event.isControlDown())) {
        autoComplete();
        event.consume();
      } else if (code == KeyEvent.VK_ESCAPE) {
        cancelAutoCompleteIfNecessary();
        event.consume();
      } else if (code == KeyEvent.VK_DELETE) {
        cancelAutoCompleteIfNecessary();
      } else { // Any other key will disable auto-complete mode if it is active
        disableAutoCompleteModeIfNecessary();
        super.keyPressed(event);
      }
    }

    @Override public void keyReleased(@NonNull KeyEvent event)
    {
      updateStatus();
    }
  }

  class CloseWindowListener extends WindowAdapter
  {
    @NonNull private final Component parent;

    public CloseWindowListener(@NonNull Component parent)
    {
      this.parent = parent;
    }

    @Override public void windowClosing(WindowEvent e)
    {
      closeIfOk(this.parent);
    }
  }

  private class CancelSWRLRuleEditActionListener implements ActionListener
  {
    @NonNull private final Component parent;

    public CancelSWRLRuleEditActionListener(@NonNull Component parent)
    {
      this.parent = parent;
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      closeIfOk(this.parent);
    }
  }

  private class SaveSWRLRuleActionListener implements ActionListener
  {
    @NonNull private final Component parent;

    public SaveSWRLRuleActionListener(@NonNull Component parent)
    {
      this.parent = parent;
    }

    @Override public void actionPerformed(@NonNull ActionEvent e)
    {
      String ruleName = getRuleName();
      String ruleText = getRuleText();
      String comment = getComment();
      boolean errorOccurred;

      if (ruleName.trim().length() == 0) {
        getDialogManager().showErrorMessageDialog(this.parent, MISSING_RULE_NAME, MISSING_RULE_NAME_TITLE);
        errorOccurred = true;
      } else if (ruleText.trim().length() == 0) {
        getDialogManager().showErrorMessageDialog(this.parent, MISSING_RULE, MISSING_RULE);
        errorOccurred = true;
      } else if (getSWRLRulesTableModel().hasSWRLRule(ruleName) && !SWRLRuleEditorDialog.this.editMode) {
        getDialogManager().showErrorMessageDialog(this.parent, DUPLICATE_RULE_TEXT, DUPLICATE_RULE_TITLE);
        errorOccurred = true;
      } else {
        try {
          if (SWRLRuleEditorDialog.this.editMode) {
            getSWRLRuleEngineModel().getSWRLRuleEngine()
              .replaceSWRLRule(getInitialDialogState().getRuleName(), ruleName, ruleText, comment, true);
            getSWRLRulesTableModel().updateView();
            errorOccurred = false;
          } else {
            if (getSWRLRulesTableModel().hasSWRLRule(ruleName)) {
              getDialogManager().showErrorMessageDialog(this.parent, DUPLICATE_RULE_TEXT, DUPLICATE_RULE_TITLE);
              errorOccurred = true;
            } else {
              getSWRLRuleEngine().createSWRLRule(ruleName, ruleText, comment, true);
              getSWRLRulesTableModel().updateView();
              errorOccurred = false;
            }
          }
        } catch (SWRLParseException | SWRLBuiltInException pe) {
          getDialogManager()
            .showErrorMessageDialog(this.parent, (pe.getMessage() != null ? pe.getMessage() : ""), INVALID_RULE_TITLE);
          errorOccurred = true;
        } catch (RuntimeException pe) {
          getDialogManager().showErrorMessageDialog(this.parent, (pe.getMessage() != null ? pe.getMessage() : ""),
            INTERNAL_ERROR_TITLE);
          errorOccurred = true;
          StringWriter sw = new StringWriter();
          PrintWriter pw = new PrintWriter(sw);
          pe.printStackTrace(pw);
          log.warn(sw.toString());
        }
      }

      if (!errorOccurred) {
        setVisible(false);
        cancelEditMode();
      } else
        updateStatus();
    }
  }
}
