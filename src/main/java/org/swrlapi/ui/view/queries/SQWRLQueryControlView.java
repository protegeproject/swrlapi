package org.swrlapi.ui.view.queries;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @see org.swrlapi.sqwrl.SQWRLQueryEngine
 */
public class SQWRLQueryControlView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  private static final int VIEW_PREFERRED_WIDTH = 900;
  private static final int VIEW_PREFERRED_HEIGHT = 300;
  private static final int TOOLTIP_PREFERRED_WIDTH = 160;
  private static final int TOOLTIP_PREFERRED_HEIGHT = 30;
  private static final int CONSOLE_ROWS = 10;
  private static final int CONSOLE_COLUMNS = 80;
  private static final int MAXIMUM_OPEN_RESULT_VIEWS = 12;

  @NonNull private final SQWRLQueryEngineModel queryEngineModel;
  @NonNull private final SQWRLQuerySelector sqwrlQuerySelector;
  @NonNull private final JTextArea console;
  @NonNull private final JScrollPane consoleScrollPane;
  @NonNull private final Map<@NonNull String, SQWRLResultView> sqwrlResultViews = new HashMap<>();

  public SQWRLQueryControlView(@NonNull SQWRLQueryEngineModel queryEngineModel,
    @NonNull SQWRLQuerySelector sqwrlQuerySelector)
  {
    this.queryEngineModel = queryEngineModel;
    this.sqwrlQuerySelector = sqwrlQuerySelector;
    this.console = new JTextArea(CONSOLE_ROWS, CONSOLE_COLUMNS);
    this.consoleScrollPane = new JScrollPane(this.console);
  }

  @Override public void initialize()
  {
    setLayout(new BorderLayout());
    console.setLineWrap(true);
    console.setBackground(Color.WHITE);
    console.setEditable(false);
    consoleScrollPane.setPreferredSize(new Dimension(VIEW_PREFERRED_WIDTH, VIEW_PREFERRED_HEIGHT));
    add(BorderLayout.CENTER, consoleScrollPane);

    JPanel controlPanel = new JPanel(new FlowLayout());
    JButton runSQWRLQueryButton = createButton("Run", "Run a SQWRL query",
      new RunSQWRLQueryActionListener(this.console, this));
    controlPanel.add(runSQWRLQueryButton);
    add(BorderLayout.SOUTH, controlPanel);

    console.append("Select a SQWRL query from the list above and press the 'Run' button.\n");
    console.append("If the selected query generates a result, the result will appear in a new sub tab.\n\n");
    console.append(
      "The SWRLAPI supports an OWL profile called OWL 2 RL and uses an OWL 2 RL-based reasoner to perform querying.\n");
    console.append("See the 'OWL 2 RL' subtab for more information on this reasoner.\n\n");
    console.append("Executing queries in this tab does not modify the ontology.\n\n");
    console
      .append("Using " + this.queryEngineModel.getSQWRLQueryEngine().getRuleEngineName() + " for query execution.\n\n");
  }

  @Override public void update()
  {
    validate();
  }

  public void appendToConsole(@NonNull String text)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        console.append(text);
        console.setCaretPosition(console.getDocument().getLength());
      }
    });
  }

  public void removeSQWRLResultView(@NonNull String queryName)
  {
    if (this.sqwrlResultViews.containsKey(queryName)) {
      SQWRLResultView sqwrlResultView = this.sqwrlResultViews.get(queryName);
      this.sqwrlResultViews.remove(queryName);
      getParent().remove(sqwrlResultView);
      ((JTabbedPane)getParent()).setSelectedIndex(0);
    }
  }

  public void removeAllSQWRLResultViews()
  {
    for (SQWRLResultView sqwrlResultView : this.sqwrlResultViews.values())
      getParent().remove(sqwrlResultView);
    this.sqwrlResultViews.clear();
  }

  @NonNull private JButton createButton(@NonNull String text, @NonNull String toolTipText,
    @NonNull ActionListener listener)
  {
    JButton button = new JButton(text);

    button.setToolTipText(toolTipText);
    button.setPreferredSize(new Dimension(TOOLTIP_PREFERRED_WIDTH, TOOLTIP_PREFERRED_HEIGHT));
    button.addActionListener(listener);

    return button;
  }

  @NonNull private SQWRLQueryEngine getSQWRLQueryEngine()
  {
    return this.queryEngineModel.getSQWRLQueryEngine();
  }

  private class ListenerBase
  {
    @NonNull protected final SQWRLQueryControlView sqwrlQueryControlView;
    @NonNull protected final JTextArea console;

    public ListenerBase(@NonNull JTextArea console, @NonNull SQWRLQueryControlView sqwrlQueryControlView)
    {
      this.console = console;
      this.sqwrlQueryControlView = sqwrlQueryControlView;
    }
  }

  private class RunSQWRLQueryActionListener extends ListenerBase implements ActionListener
  {
    public RunSQWRLQueryActionListener(@NonNull JTextArea console, @NonNull SQWRLQueryControlView sqwrlQueryControlView)
    {
      super(console, sqwrlQueryControlView);
    }

    @Override public void actionPerformed(@NonNull ActionEvent event)
    {
      runSQWRLQuery();
    }

    private void runSQWRLQuery()
    {
      Optional<@NonNull String> queryName = Optional.empty();

      if (SQWRLQueryControlView.this.sqwrlResultViews.size() == SQWRLQueryControlView.MAXIMUM_OPEN_RESULT_VIEWS) {
        appendToConsole(
          "A maximum of " + SQWRLQueryControlView.MAXIMUM_OPEN_RESULT_VIEWS + " result tabs may be open at once. ");
        appendToConsole("Please close an existing tab to display results for the selected query.\n");
      } else {
        try {
          SQWRLQuerySelector querySelector = SQWRLQueryControlView.this.sqwrlQuerySelector;

          if (querySelector == null) {
            appendToConsole("Configuration error: no query selector supplied. No queries can be executed!\n");
          } else {
            queryName = SQWRLQueryControlView.this.sqwrlQuerySelector.getSelectedQueryName();

            if (queryName.isPresent()) {
              long startTime = System.currentTimeMillis();
              SQWRLResult sqwrlResult = SQWRLQueryControlView.this.getSQWRLQueryEngine().runSQWRLQuery(queryName.get());

              if (sqwrlResult == null || sqwrlResult.getNumberOfRows() == 0)
                indicateEmptySQWRLResult(queryName.get());
              else
                displaySQWRLResult(queryName.get(), sqwrlResult, startTime);
            } else
              appendToConsole("No enabled SQWRL query selected.\n");
          }
        } catch (SQWRLInvalidQueryNameException e) {
          appendToConsole(queryName + " is not a valid SQWRL query or is not enabled.\n");
        } catch (SQWRLException | RuntimeException e) {
          if (queryName.isPresent())
            appendToConsole("Exception when running SQWRL query " + queryName.get() + ": " + (e.getMessage() != null ?
              e.getMessage() :
              "") + "\n");
          else
            appendToConsole(
              "Exception running SQWRL queries: " + (e.getMessage() != null ? e.getMessage() : "") + "\n");
        }
      }
    }

    private void indicateEmptySQWRLResult(@NonNull String queryName)
    {
      appendToConsole("SQWRL query " + queryName + " did not generate any result.\n");

      if (SQWRLQueryControlView.this.sqwrlResultViews.containsKey(queryName)) {
        SQWRLResultView queryResultsView = SQWRLQueryControlView.this.sqwrlResultViews.get(queryName);
        SQWRLQueryControlView.this.sqwrlResultViews.remove(queryName);
        getParent().remove(queryResultsView);
      }
    }

    private void displaySQWRLResult(@NonNull String queryName, @NonNull SQWRLResult sqwrlResult, long startTime)
      throws SQWRLException
    {
      appendToConsole("See the " + queryName + " tab to review results of the SQWRL query.\n");
      appendToConsole("The query took " + (System.currentTimeMillis() - startTime) + " milliseconds. ");

      if (sqwrlResult.getNumberOfRows() == 1)
        appendToConsole("1 row was returned.\n");
      else
        appendToConsole("" + sqwrlResult.getNumberOfRows() + " rows were returned.\n");

      SQWRLResultView sqwrlResultView;

      if (SQWRLQueryControlView.this.sqwrlResultViews.containsKey(queryName)) // Existing result tab found
        sqwrlResultView = SQWRLQueryControlView.this.sqwrlResultViews.get(queryName);
      else { // Create new result tab
        sqwrlResultView = new SQWRLResultView(SQWRLQueryControlView.this.queryEngineModel, queryName, sqwrlResult,
          this.sqwrlQueryControlView);
        sqwrlResultView.initialize();
        SQWRLQueryControlView.this.sqwrlResultViews.put(queryName, sqwrlResultView);
        ((JTabbedPane)getParent())
          .addTab(queryName, null, sqwrlResultView, "SQWRL Result for query '" + queryName + "'");
      }

      sqwrlResultView.validate();
      this.sqwrlQueryControlView.getParent().validate();
      this.console.validate();
    }
  }
}
