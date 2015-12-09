package org.swrlapi.ui.menu;

import checkers.nullness.quals.NonNull;
import org.swrlapi.ui.action.CloseAction;
import org.swrlapi.ui.action.OpenAction;
import org.swrlapi.ui.action.QuitAction;
import org.swrlapi.ui.action.SaveAction;
import org.swrlapi.ui.action.SaveAsAction;
import org.swrlapi.ui.dialog.SWRLRuleEngineDialogManager;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class SWRLRuleEngineMenuManager
{
  private static final String FILE_MENU_TITLE = "File";

  public static void createSWRLRuleEngineMenus(@NonNull JFrame applicationFrame,
      @NonNull FileBackedSWRLRuleEngineModel ruleEngineModel, @NonNull SWRLRuleEngineDialogManager dialogManager)
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
    menu.addMenuListener(new SWRLRuleEngineMenuListener(ruleEngineModel, saveItem));
  }

  private static class SWRLRuleEngineMenuListener implements MenuListener
  {
    @NonNull private final FileBackedSWRLRuleEngineModel ruleEngineModel;
    @NonNull private final JMenuItem saveItem;

    public SWRLRuleEngineMenuListener(@NonNull FileBackedSWRLRuleEngineModel ruleEngineModel,
        @NonNull JMenuItem saveItem)
    {
      this.ruleEngineModel = ruleEngineModel;
      this.saveItem = saveItem;
    }

    @Override public void menuSelected(@NonNull MenuEvent e)
    {
      this.saveItem.setEnabled(ruleEngineModel.hasBackingFile());
    }

    @Override public void menuDeselected(MenuEvent e)
    {
    }

    @Override public void menuCanceled(MenuEvent e)
    {
    }
  }
}
