package org.swrlapi.ui.view.rules;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SWRLRulesControlView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private static final int VIEW_PREFERRED_WIDTH = 900;
  private static final int VIEW_PREFERRED_HEIGHT = 300;
  private static final int TOOLTIP_PREFERRED_WIDTH = 160;
  private static final int TOOLTIP_PREFERRED_HEIGHT = 30;
  private static final int CONSOLE_ROWS = 10;
  private static final int CONSOLE_COLUMNS = 80;

  private final SWRLRuleEngine swrlRuleEngine;

  public SWRLRulesControlView(SWRLRuleEngine swrlRuleEngine)
  {
    String ruleEngineName = swrlRuleEngine.getRuleEngineName();
    String ruleEngineVersion = swrlRuleEngine.getRuleEngineVersion();

    this.swrlRuleEngine = swrlRuleEngine;

    JTextArea console = createConsole();
    JScrollPane scrollPane = new JScrollPane(console);
    scrollPane.setPreferredSize(new Dimension(VIEW_PREFERRED_WIDTH, VIEW_PREFERRED_HEIGHT));
    setLayout(new BorderLayout());
    add(BorderLayout.CENTER, scrollPane);

    JPanel buttonsPanel = new JPanel(new FlowLayout());
    JButton button = createButton("OWL+SWRL->" + ruleEngineName,
        "Translate SWRL rules and relevant OWL knowledge to rule engine", new ImportActionListener(swrlRuleEngine,
            console, this));
    buttonsPanel.add(button);
    button = createButton("Run " + ruleEngineName, "Run the rule engine", new RunActionListener(swrlRuleEngine,
        console, this));
    buttonsPanel.add(button);
    button = createButton(ruleEngineName + "->OWL", "Translate asserted rule engine knowledge to OWL knowledge",
        new ExportActionListener(this.swrlRuleEngine, console, this));
    buttonsPanel.add(button);
    add(BorderLayout.SOUTH, buttonsPanel);

    console.append("Using the " + ruleEngineName + " rule engine, " + ruleEngineVersion + ".\n\n");
    console.append("Press the 'OWL+SWRL->" + ruleEngineName
        + "' button to transfer SWRL rules and relevant OWL knowledge to the rule engine.\n");
    console.append("Press the 'Run " + ruleEngineName + "' button to run the rule engine.\n");
    console.append("Press the '" + ruleEngineName
        + "->OWL' button to transfer the inferred rule engine knowledge to OWL knowledge.\n\n");
    console
        .append("The SWRLAPI supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform reasoning.\n");
    console.append("See the 'OWL 2 RL' subtab for more information on this reasoner.");
  }

  @Override
  public void update()
  {
    validate();
  }

  private JButton createButton(String text, String toolTipText, ActionListener listener)
  {
    JButton button = new JButton(text);

    button.setToolTipText(toolTipText);
    button.setPreferredSize(new Dimension(TOOLTIP_PREFERRED_WIDTH, TOOLTIP_PREFERRED_HEIGHT));
    button.addActionListener(listener);

    return button;
  }

  private JTextArea createConsole()
  {
    JTextArea textArea = new JTextArea(CONSOLE_ROWS, CONSOLE_COLUMNS);

    textArea.setLineWrap(true);
    textArea.setBackground(Color.WHITE);
    textArea.setEditable(false);

    return textArea;
  }

  private class ListenerBase
  {
    protected final SWRLRuleEngine ruleEngine;
    protected final JTextArea console;
    protected final SWRLRulesControlView controlPanel;

    public ListenerBase(SWRLRuleEngine ruleEngine, JTextArea console, SWRLRulesControlView controlPanel)
    {
      this.ruleEngine = ruleEngine;
      this.console = console;
      this.controlPanel = controlPanel;
    }

    protected void clearConsole()
    {
      this.console.setText("");
    }

    protected void appendToConsole(String text)
    {
      this.console.append(text);
    }
  }

  private class ImportActionListener extends ListenerBase implements ActionListener
  {
    public ImportActionListener(SWRLRuleEngine ruleEngine, JTextArea console, SWRLRulesControlView controlPanel)
    {
      super(ruleEngine, console, controlPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
      try {
        long startTime = System.currentTimeMillis();
        this.ruleEngine.importAssertedOWLAxioms();

        clearConsole();
        appendToConsole("OWL axioms successfully transferred to rule engine.\n");
        appendToConsole("Number of SWRL rules exported to rule engine: "
            + this.ruleEngine.getNumberOfImportedSWRLRules() + "\n");
        appendToConsole("Number of OWL class declarations exported to rule engine: "
            + this.ruleEngine.getNumberOfAssertedOWLClassDeclarationAxioms() + "\n");
        appendToConsole("Number of OWL individual declarations exported to rule engine: "
            + this.ruleEngine.getNumberOfAssertedOWLIndividualDeclarationsAxioms() + "\n");
        appendToConsole("Number of OWL object property declarations exported to rule engine: "
            + this.ruleEngine.getNumberOfAssertedOWLObjectPropertyDeclarationAxioms() + "\n");
        appendToConsole("Number of OWL data property declarations exported to rule engine: "
            + this.ruleEngine.getNumberOfAssertedOWLDataPropertyDeclarationAxioms() + "\n");
        appendToConsole("Total number of OWL axioms exported to rule engine: "
            + this.ruleEngine.getNumberOfAssertedOWLAxioms() + "\n");
        appendToConsole("The transfer took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
        appendToConsole("Press the 'Run " + SWRLRulesControlView.this.swrlRuleEngine.getRuleEngineName()
            + "' button to run the rule engine.\n");
      } catch (SWRLRuleEngineException e) {
        appendToConsole("Exception importing SWRL rules and OWL knowledge: " + e.toString() + "\n");
      }
      this.controlPanel.getParent().validate();
    }
  }

  private class RunActionListener extends ListenerBase implements ActionListener
  {
    public RunActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLRulesControlView controlPanel)
    {
      super(ruleEngine, textArea, controlPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
      displayRunResults();
    }

    private void displayRunResults()
    {
      try {
        long startTime = System.currentTimeMillis();
        this.ruleEngine.run();

        appendToConsole("Successful execution of rule engine.\n");
        appendToConsole("Number of inferred axioms: " + this.ruleEngine.getNumberOfInferredOWLAxioms() + "\n");
        if (this.ruleEngine.getNumberOfInjectedOWLAxioms() != 0)
          appendToConsole("Number of axioms injected by built-ins: " + this.ruleEngine.getNumberOfInjectedOWLAxioms()
              + "\n");
        appendToConsole("The process took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
        appendToConsole("Look at the 'Inferred Axioms' tab to see the inferred axioms.\n");
        appendToConsole("Press the '" + SWRLRulesControlView.this.swrlRuleEngine.getRuleEngineName()
            + "->OWL' button to translate the inferred axioms to OWL knowledge.\n");
      } catch (SWRLRuleEngineException e) {
        appendToConsole("Exception running rule engine: " + e.getMessage() + "\n");
      }
      this.controlPanel.getParent().validate();
    }
  }

  private class ExportActionListener extends ListenerBase implements ActionListener
  {
    public ExportActionListener(SWRLRuleEngine ruleEngine, JTextArea textArea, SWRLRulesControlView controlPanel)
    {
      super(ruleEngine, textArea, controlPanel);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
      displayExportResults();
    }

    private void displayExportResults()
    {
      try {
        long startTime = System.currentTimeMillis();
        this.ruleEngine.exportInferredOWLAxioms();

        appendToConsole("Successfully transferred inferred axioms to OWL model.\n");
        appendToConsole("The process took " + (System.currentTimeMillis() - startTime) + " millisecond(s).\n");
      } catch (SWRLRuleEngineException e) {
        appendToConsole("Exception exporting knowledge to OWL: " + e.toString() + "\n");
      }
      this.controlPanel.getParent().validate();
    }
  }
}
