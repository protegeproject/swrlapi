package org.swrlapi.ui.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class models a list of SWRL rules and SQWRL queries in an ontology for tabular display.
 *
 * @see org.swrlapi.ui.model.SWRLRuleEngineModel
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class SWRLRulesAndSQWRLQueriesTableModel extends AbstractTableModel implements SWRLAPIModel
{
  private static final long serialVersionUID = 1L;

  public enum ContentMode
  {
    RuleContentOnly, QueryContentOnly, RuleAndQueryContent
  }

  private static final String RULE_NAME_COLUMN_TITLE = "Name";
  private static final String RULE_TEXT_COLUMN_TITLE = "Rule";
  private static final String QUERY_TEXT_COLUMN_TITLE = "Query";
  private static final String RULE_AND_QUERY_TEXT_COLUMN_TITLE = "Body";
  private static final String RULE_COMMENT_COLUMN_TITLE = "Comment";

  @NonNull private SWRLRuleEngine swrlRuleEngine;
  @NonNull private final SortedMap<@NonNull String, @NonNull SWRLRuleModel> swrlRuleModels; // rule name -> SWRLRuleModel
  @NonNull private Optional<@NonNull SWRLAPIView> view = Optional.<@NonNull SWRLAPIView>empty();
  private ContentMode contentMode;

  private boolean isModified;

  public SWRLRulesAndSQWRLQueriesTableModel(@NonNull SWRLRuleEngine swrlRuleEngine)
  {
    this.swrlRuleEngine = swrlRuleEngine;
    this.swrlRuleModels = new TreeMap<>();
    this.isModified = false;
    this.contentMode = ContentMode.RuleAndQueryContent;
  }

  public void setView(@NonNull SWRLAPIView view)
  {
    this.view = Optional.of(view);
    updateRuleModels();
  }

  public void updateModel(@NonNull SWRLRuleEngine swrlRuleEngine)
  {
    this.swrlRuleEngine = swrlRuleEngine;
    this.swrlRuleModels.clear();
    this.isModified = false;

    updateView();
  }

  @NonNull public Set<@NonNull SWRLRuleModel> getSWRLRuleModels()
  {
    return new HashSet<>(this.swrlRuleModels.values());
  }

  @NonNull public Set<@NonNull SWRLRuleModel> getSWRLRuleModels(boolean isActiveFlag)
  {
    Set<@NonNull SWRLRuleModel> swrlRuleModels = new HashSet<>();
    for (SWRLRuleModel swrlRuleModel : this.swrlRuleModels.values()) {
      if (swrlRuleModel.isActive() == isActiveFlag)
        swrlRuleModels.add(swrlRuleModel);
    }
    return swrlRuleModels;
  }

  public boolean hasSWRLRules()
  {
    return !this.swrlRuleModels.isEmpty();
  }

  @NonNull public String getSWRLRuleNameByIndex(int ruleIndex)
  {
    Optional<@NonNull SWRLRuleModel> swrlRuleModel = getSWRLRuleModelByIndex(ruleIndex);

    if (swrlRuleModel.isPresent())
      return swrlRuleModel.get().getRuleName();
    else
      return "<INVALID_INDEX>";
  }

  @NonNull public String getSWRLRuleTextByIndex(int ruleIndex)
  {
    Optional<@NonNull SWRLRuleModel> swrlRuleModel = getSWRLRuleModelByIndex(ruleIndex);

    if (swrlRuleModel.isPresent())
      return swrlRuleModel.get().getRuleText();
    else
      return "<INVALID_INDEX>";
  }

  @NonNull public String getSWRLRuleCommentByIndex(int ruleIndex)
  {
    Optional<@NonNull SWRLRuleModel> swrlRuleModel = getSWRLRuleModelByIndex(ruleIndex);

    if (swrlRuleModel.isPresent())
      return swrlRuleModel.get().getComment();
    else
      return "<INVALID_INDEX>";
  }

  public boolean hasSWRLRule(@NonNull String ruleName)
  {
    return this.swrlRuleModels.containsKey(ruleName);
  }

  public boolean hasBeenModified()
  {
    return this.isModified;
  }

  public void clearModifiedStatus()
  {
    this.isModified = false;
  }

  public int getNumberOfColumns()
  {
    return this.contentMode == ContentMode.QueryContentOnly ? 3 : 4;
  }

  public boolean hasRuleActiveColumn()
  {
    return this.contentMode != ContentMode.QueryContentOnly;
  }

  public int getRuleActiveColumnNumber()
  {
    return this.contentMode == ContentMode.QueryContentOnly ? -1 : 0;
  }

  public int getRuleNameColumnNumber()
  {
    return this.contentMode == ContentMode.QueryContentOnly ? 0 : 1;
  }

  public int getRuleTextColumnNumber()
  {
    return this.contentMode == ContentMode.QueryContentOnly ? 1 : 2;
  }

  public int getRuleCommentColumnNumber()
  {
    return this.contentMode == ContentMode.QueryContentOnly ? 2 : 3;
  }

  @Override public int getRowCount()
  {
    return this.swrlRuleModels.size();
  }

  @Override public int getColumnCount()
  {
    return getNumberOfColumns();
  }


  @NonNull @Override public String getColumnName(int column)
  {
    if (column == getRuleNameColumnNumber())
      return RULE_NAME_COLUMN_TITLE;
    else if (column == getRuleTextColumnNumber()) {
      switch (this.contentMode) {
      case RuleContentOnly:
        return RULE_TEXT_COLUMN_TITLE;
      case QueryContentOnly:
        return QUERY_TEXT_COLUMN_TITLE;
      case RuleAndQueryContent:
        return RULE_AND_QUERY_TEXT_COLUMN_TITLE;
      default:
        return "INVALID";
      }
    } else if (column == getRuleCommentColumnNumber())
      return RULE_COMMENT_COLUMN_TITLE;
    else if (column == getRuleActiveColumnNumber())
      return "";
    else
      return "";
  }

  @NonNull @Override public Object getValueAt(int row, int column)
  {
    if ((row < 0 || row >= getRowCount()) || ((column < 0 || column >= getColumnCount())))
      return "OUT OF BOUNDS";
    else {
      if (column == getRuleTextColumnNumber())
        return ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).getRuleText();
      else if (column == getRuleNameColumnNumber())
        return ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).getRuleName();
      else if (column == getRuleCommentColumnNumber())
        return ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).getComment();
      else if (column == getRuleActiveColumnNumber())
        return ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[row]).isActive();
      return "INVALID COLUMN";
    }
  }

  @Override public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return columnIndex == getRuleActiveColumnNumber();
  }

  @Override public Class<?> getColumnClass(int columnIndex)
  {
    if (columnIndex == getRuleActiveColumnNumber()) {
      return Boolean.class;
    } else {
      return super.getColumnClass(columnIndex);
    }
  }

  @Override public void setValueAt(Object aValue, int rowIndex, int columnIndex)
  {
    if (columnIndex == getRuleActiveColumnNumber()) {
      ((SWRLRuleModel)this.swrlRuleModels.values().toArray()[rowIndex]).setActive((Boolean)aValue);
    } else {
      super.setValueAt(aValue, rowIndex, columnIndex);
    }
  }

  @Override public void updateView()
  {
    if (this.view.isPresent()) {
      updateRuleModels();
      this.view.get().update();
    }
  }

  @NonNull private Optional<@NonNull SWRLRuleModel> getSWRLRuleModelByIndex(int ruleIndex)
  {
    if (ruleIndex >= 0 && ruleIndex < this.swrlRuleModels.values().size())
      return Optional.of(((SWRLRuleModel)this.swrlRuleModels.values().toArray()[ruleIndex]));
    else
      return Optional.<@NonNull SWRLRuleModel>empty();
  }

  private void updateRuleModels()
  {
    this.swrlRuleModels.clear();

    for (SWRLAPIRule swrlapiRule : this.swrlRuleEngine.getSWRLRules()) {
      String ruleName = swrlapiRule.getRuleName();
      String ruleText = this.swrlRuleEngine.createSWRLRuleRenderer().renderSWRLRule(swrlapiRule);
      String comment = swrlapiRule.getComment();
      SWRLRuleModel swrlRuleModel = new SWRLRuleModel(ruleName, ruleText, comment);
      this.swrlRuleModels.put(ruleName, swrlRuleModel);
    }
  }

  @Override public String toString()
  {
    return "SWRLRulesAndSQWRLQueriesTableModel{" +
      "swrlRuleEngine=" + swrlRuleEngine +
      ", swrlRuleModels=" + swrlRuleModels +
      ", view=" + view +
      ", contentMode=" + contentMode +
      ", isModified=" + isModified +
      '}';
  }

  private class SWRLRuleModel
  {
    @NonNull private final String ruleName, ruleText, comment;
    private boolean active;

    public SWRLRuleModel(@NonNull String ruleName, @NonNull String ruleText, @NonNull String comment)
    {
      this.active = true;
      this.ruleText = ruleText;
      this.ruleName = ruleName;
      this.comment = comment;
    }

    public void setActive(boolean active)
    {
      this.active = active;
    }

    public boolean isActive()
    {
      return this.active;
    }

    @NonNull public String getRuleText()
    {
      return this.ruleText;
    }

    @NonNull public String getRuleName()
    {
      return this.ruleName;
    }

    @NonNull public String getComment()
    {
      return this.comment;
    }

    @SideEffectFree @NonNull @Override public String toString()
    {
      return "(ruleName: " + this.ruleName + ", ruleText: " + this.ruleText + ", comment: " + this.comment
        + ", active: " + this.active + ")";
    }
  }
}
