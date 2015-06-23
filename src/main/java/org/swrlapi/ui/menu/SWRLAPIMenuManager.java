package org.swrlapi.ui.menu;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.action.CloseAction;
import org.swrlapi.ui.action.OpenAction;
import org.swrlapi.ui.action.QuitAction;
import org.swrlapi.ui.action.SaveAction;
import org.swrlapi.ui.action.SaveAsAction;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;

import javax.swing.*;

public class SWRLAPIMenuManager
{
  private static final String FILE_MENU_TITLE = "File";

  public static void createApplicationMenus(@NonNull JFrame applicationFrame,
    @NonNull FileBackedSWRLRuleEngineModel ruleEngineModel, @NonNull SWRLAPIDialogManager dialogManager)
  {
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu(FILE_MENU_TITLE);

    JMenuItem openItem = new JMenuItem(OpenAction.OPEN_TITLE);
    JMenuItem saveItem = new JMenuItem(SaveAction.SAVE_TITLE);
    JMenuItem saveAsItem = new JMenuItem(SaveAsAction.SAVE_AS_TITLE);
    JMenuItem closeItem = new JMenuItem(CloseAction.CLOSE_TITLE);
    JMenuItem quitItem = new JMenuItem(QuitAction.QUIT_TITLE);

    openItem.addActionListener(new OpenAction(applicationFrame, ruleEngineModel, dialogManager));
    saveItem.addActionListener(new SaveAction(applicationFrame, ruleEngineModel, dialogManager));
    saveAsItem.addActionListener(new SaveAsAction(applicationFrame, ruleEngineModel, dialogManager));
    closeItem.addActionListener(new CloseAction(applicationFrame, ruleEngineModel, dialogManager));
    quitItem.addActionListener(new QuitAction(applicationFrame, ruleEngineModel, dialogManager));

    menu.add(openItem);
    menu.add(saveItem);
    menu.add(saveAsItem);
    menu.add(closeItem);
    menu.add(quitItem);

    menuBar.add(menu);

    applicationFrame.setJMenuBar(menuBar);
  }
}
