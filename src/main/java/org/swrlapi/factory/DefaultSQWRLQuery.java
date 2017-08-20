package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.literal.Literal;
import org.swrlapi.sqwrl.SQWRLNames;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.SQWRLResultManager;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class DefaultSQWRLQuery implements SQWRLQuery
{
  @NonNull private final String queryName;
  @NonNull private final List<@NonNull SWRLAtom> bodyAtoms;
  @NonNull private final List<@NonNull SWRLAtom> headAtoms;
  @NonNull private final SQWRLResultManager sqwrlResult;
  // Map of collection name to group arguments. Applies only to grouped collections.
  @NonNull private final Map<@NonNull String, @NonNull List<@NonNull SWRLBuiltInArgument>> collectionGroupArgumentsMap;
  @NonNull private final IRIResolver iriResolver;
  @NonNull private final LiteralFactory literalFactory;
  @NonNull private final String comment;

  private boolean active; // Like a SWRL rule, a SQWRL query can also be active or inactive.

  public DefaultSQWRLQuery(@NonNull String queryName, @NonNull List<@NonNull SWRLAtom> bodyAtoms,
    @NonNull List<@NonNull SWRLAtom> headAtoms, boolean active, @NonNull String comment,
    @NonNull LiteralFactory literalFactory, @NonNull IRIResolver iriResolver) throws SWRLBuiltInException
  {
    this.queryName = queryName;
    this.bodyAtoms = new ArrayList<>(bodyAtoms);
    this.headAtoms = new ArrayList<>(headAtoms);
    this.active = active;
    this.comment = comment;
    this.sqwrlResult = SWRLAPIInternalFactory.createSQWRLResultManager(iriResolver);
    this.collectionGroupArgumentsMap = new HashMap<>();
    this.iriResolver = iriResolver;
    this.literalFactory = literalFactory;

    processSQWRLBuiltIns();
    generateBuiltInAtomVariableDependencies();
  }

  @NonNull @Override public String getQueryName()
  {
    return this.queryName;
  }

  @NonNull @Override public List<@NonNull SWRLAtom> getHeadAtoms()
  {
    return Collections.unmodifiableList(this.headAtoms);
  }

  @NonNull @Override public List<@NonNull SWRLAtom> getBodyAtoms()
  {
    return Collections.unmodifiableList(this.bodyAtoms);
  }

  @NonNull @Override public SQWRLResult getSQWRLResult() throws SQWRLException
  {
    if (!this.sqwrlResult.isPrepared()) {
      this.sqwrlResult.prepared();
    }

    return this.sqwrlResult;
  }

  @NonNull @Override public SQWRLResultGenerator getSQWRLResultGenerator()
  {
    return this.sqwrlResult;
  }

  @Override public boolean hasSQWRLCollections()
  {
    return !getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames()).isEmpty();
  }

  @NonNull @Override public List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(
    @NonNull Set<@NonNull String> builtInNames)
  {
    return getBuiltInAtoms(getBodyAtoms(), builtInNames);
  }

  @Override public void setActive(boolean isActive)
  {
    this.active = isActive;
  }

  @NonNull @Override public String getComment()
  {
    return this.comment;
  }

  @Override public boolean isActive()
  {
    return this.active;
  }

  @NonNull @Override public List<@NonNull SWRLAtom> getSQWRLPhase1BodyAtoms()
  {
    List<@NonNull SWRLAtom> result = new ArrayList<>();

    for (SWRLAtom atom : getBodyAtoms()) {
      if (atom instanceof SWRLAPIBuiltInAtom) {
        SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
        if (builtInAtom.usesSQWRLCollectionResults() || isSQWRLGroupCollection(builtInAtom)) {
          continue;
        }
      }
      result.add(atom);
    }
    return result;
  }

  @NonNull @Override public List<@NonNull SWRLAtom> getSQWRLPhase2BodyAtoms()
  {
    List<@NonNull SWRLAtom> result = new ArrayList<>();

    for (SWRLAtom atom : getBodyAtoms()) {
      if (atom instanceof SWRLAPIBuiltInAtom) {
        SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
        if (isSQWRLMakeCollection(builtInAtom) || isSQWRLGroupCollection(builtInAtom)) {
          continue;
        }
      }
      result.add(atom);
    }

    return result;
  }

  private List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody()
  {
    return getBuiltInAtoms(getBodyAtoms());
  }

  private boolean isSQWRLMakeCollection(@NonNull SWRLAPIBuiltInAtom builtInAtom)
  {
    return SQWRLNames.isSQWRLCollectionMakeBuiltIn(builtInAtom.getBuiltInPrefixedName());
  }

  private boolean isSQWRLGroupCollection(@NonNull SWRLAPIBuiltInAtom builtInAtom)
  {
    return SQWRLNames.isSQWRLCollectionGroupByBuiltIn(builtInAtom.getBuiltInPrefixedName());
  }

  private List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead()
  {
    return getBuiltInAtoms(getHeadAtoms());
  }

  @NonNull private List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(@NonNull Set<@NonNull String> builtInNames)
  {
    return getBuiltInAtoms(getHeadAtoms(), builtInNames);
  }

  @NonNull private List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtoms(@NonNull List<@NonNull SWRLAtom> atoms)
  {
    return atoms.stream().filter(atom -> atom instanceof SWRLAPIBuiltInAtom).map(atom -> (SWRLAPIBuiltInAtom)atom)
      .collect(Collectors.toList());
  }

  @NonNull private List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtoms(@NonNull List<@NonNull SWRLAtom> atoms,
    @NonNull Set<@NonNull String> builtInNames)
  {
    List<@NonNull SWRLAPIBuiltInAtom> result = new ArrayList<>();

    atoms.stream().filter(atom -> atom instanceof SWRLAPIBuiltInAtom).forEach(atom -> {
      SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
      if (builtInNames.contains(builtInAtom.getBuiltInPrefixedName())) {
        result.add(builtInAtom);
      }
    });
    return result;
  }

  private void processSQWRLBuiltIns() throws SWRLBuiltInException
  {
    Set<@NonNull String> collectionNames = new HashSet<>();
    Set<@NonNull String> cascadedUnboundVariableNames = new HashSet<>();

    processSQWRLHeadBuiltIns();
    processSQWRLCollectionMakeBuiltIns(collectionNames); // Find all make collection built-ins
    processSQWRLCollectionGroupByBuiltIns(collectionNames); // Find the group arguments for each collection
    processSQWRLCollectionMakeGroupArguments(collectionNames); // Add group arguments to the make built-ins collections
    processSQWRLCollectionOperationBuiltIns(collectionNames, cascadedUnboundVariableNames);
    processBuiltInsThatUseSQWRLCollectionOperationResults(cascadedUnboundVariableNames);

    this.sqwrlResult.configured();
    this.sqwrlResult.openRow();

    if (hasSQWRLCollections()) {
      this.sqwrlResult.setIsDistinct();
    }
  }

  private void processSQWRLHeadBuiltIns() throws SWRLBuiltInException
  {
    // A variable can be selected multiple times.
    // We recordOWLClassExpression its positions in case of a sqwrl:orderBy clause.
    Map<@NonNull String, @NonNull List<@NonNull Integer>> selectedVariable2ColumnIndices = new HashMap<>();

    assignBuiltInIndexes();

    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromHead(SQWRLNames.getHeadBuiltInNames())) {
      String builtInName = builtInAtom.getBuiltInPrefixedName();

      processBuiltInArguments(builtInAtom, selectedVariable2ColumnIndices);

      if (SQWRLNames.isSQWRLHeadSlicingBuiltIn(builtInName)) {
        processHeadSlicingBuiltIn(builtInAtom);
      }
    }
  }

  private void processHeadSlicingBuiltIn(@NonNull SWRLAPIBuiltInAtom builtInAtom) throws SQWRLException
  {
    String builtInPrefixedName = builtInAtom.getBuiltInPrefixedName();

    if (this.sqwrlResult.getCurrentNumberOfColumns() == 0) {
      throw new SQWRLException("slicing operator used without a select clause");
    }

    if (!this.sqwrlResult.isOrdered() && !builtInPrefixedName.equals(SQWRLNames.Limit)) {
      throw new SQWRLException("slicing operator used without an order clause");
    }

    SWRLBuiltInArgument nArgument = builtInAtom.getBuiltInArguments().get(0);
    int sliceN;

    if (nArgument instanceof SWRLLiteralBuiltInArgument) {
      SWRLLiteralBuiltInArgument sliceNArgument = (SWRLLiteralBuiltInArgument)nArgument;
      Literal literal = this.literalFactory.getLiteral(sliceNArgument.getLiteral());

      if (literal.isInt()) {
        sliceN = literal.getInt();
        if (sliceN < 1) {
          throw new SQWRLException(
            "nth argument for slicing operator " + builtInPrefixedName + " must be a positive xsd:int or "
              + "xsd:integer");
        }
      } else if (literal.isInteger()) {
        BigInteger value = literal.getInteger();
        if (value.compareTo(BigInteger.ZERO) <= 0) {
          throw new SQWRLException(
            "nth argument for slicing operator " + builtInPrefixedName + " must be a positive xsd:int or "
              + "xsd:integer");
        }
        if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
          throw new SQWRLException(
            "nth argument for slicing operator " + builtInPrefixedName + " must not be greater than "
              + Integer.MAX_VALUE);
        }
        sliceN = value.intValue();
      } else {
        throw new SQWRLException(
          "expecting xsd:int or xsd:integer argument for slicing operator " + builtInPrefixedName);
      }
    } else {
      throw new SQWRLException("expecting xsd:int or xsd:integer argument for slicing operator " + builtInPrefixedName);
    }

    if (builtInAtom.getNumberOfArguments() == 1) {
      processHeadSliceOperationWithoutSliceSize(builtInPrefixedName, sliceN);
    } else if (builtInAtom.getNumberOfArguments() == 2) {
      processHeadSliceOperationWithSliceSize(builtInAtom, builtInPrefixedName, sliceN);
    } else {
      throw new SQWRLException("slicing operator " + builtInPrefixedName + " expecting a maximum of 2 arguments");
    }
  }

  private void processHeadSliceOperationWithSliceSize(@NonNull SWRLAPIBuiltInAtom builtInAtom,
    @NonNull String builtInName, int sliceN) throws SQWRLException
  {
    SWRLBuiltInArgument sliceSizeArgument = builtInAtom.getBuiltInArguments().get(1);
    int sliceSize;

    if (sliceSizeArgument instanceof SWRLLiteralBuiltInArgument) {
      SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)sliceSizeArgument;
      Literal literal = this.literalFactory.getLiteral(literalArgument.getLiteral());
      if (literal.isInt()) {
        sliceSize = literal.getInt();
        if (sliceSize < 1) {
          throw new SQWRLException(
            "slice size argument to slicing operator " + builtInName + " must be a positive xsd:int or xsd:integer");
        }
      } else if (literal.isInteger()) {
        BigInteger value = literal.getInteger();
        if (value.compareTo(BigInteger.ZERO) <= 0) {
          throw new SQWRLException(
            "slice size argument to slicing operator " + builtInName + " must be a positive xsd:int or xsd:integer");
        }
        if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
          throw new SQWRLException(
            "slice size argument to slicing operator " + builtInName + " must not be greater than "
              + Integer.MAX_VALUE);
        }
        sliceSize = value.intValue();
      } else {
        throw new SQWRLException("expecting xsd:int or xsd:integer argument for slicing operator " + builtInName);
      }
    } else {
      throw new SQWRLException("expecting xsd:int or xsd:integer argument for slicing operator " + builtInName);
    }

    if (builtInName.equalsIgnoreCase(SQWRLNames.NthSlice)) {
      this.sqwrlResult.setNthSlice(sliceN, sliceSize);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.NotNthSlice)) {
      this.sqwrlResult.setNotNthSlice(sliceN, sliceSize);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.NthLastSlice) || builtInName
      .equalsIgnoreCase(SQWRLNames.NthGreatestSlice)) {
      this.sqwrlResult.setNthLastSlice(sliceN, sliceSize);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.NotNthLastSlice) || builtInName
      .equalsIgnoreCase(SQWRLNames.NotNthGreatestSlice)) {
      this.sqwrlResult.setNotNthLastSlice(sliceN, sliceSize);
    } else {
      throw new SQWRLException("unknown slicing operator " + builtInName);
    }
  }

  private void processHeadSliceOperationWithoutSliceSize(@NonNull String builtInName, int sliceN) throws SQWRLException
  {
    if (builtInName.equalsIgnoreCase(SQWRLNames.Limit)) {
      this.sqwrlResult.setLimit(sliceN);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.Nth)) {
      this.sqwrlResult.setNth(sliceN);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.NotNth)) {
      this.sqwrlResult.setNotNth(sliceN);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.FirstN) || builtInName.equalsIgnoreCase(SQWRLNames.LeastN)) {
      this.sqwrlResult.setFirst(sliceN);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.LastN) || builtInName.equalsIgnoreCase(SQWRLNames.GreatestN)) {
      this.sqwrlResult.setLast(sliceN);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.NotLastN) || builtInName
      .equalsIgnoreCase(SQWRLNames.NotGreatestN)) {
      this.sqwrlResult.setNotLast(sliceN);
    } else if (builtInName.equalsIgnoreCase(SQWRLNames.NotFirstN) || builtInName
      .equalsIgnoreCase(SQWRLNames.NotLeastN)) {
      this.sqwrlResult.setNotFirst(sliceN);
    } else {
      throw new SQWRLException("unknown slicing operator " + builtInName);
    }
  }

  private void processBuiltInArguments(@NonNull SWRLAPIBuiltInAtom builtInAtom,
    @NonNull Map<@NonNull String, @NonNull List<@NonNull Integer>> selectedVariable2ColumnIndices)
    throws SWRLBuiltInException
  {
    String builtInName = builtInAtom.getBuiltInPrefixedName();

    int columnIndex = 0;
    for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {

      if (SQWRLNames.isSQWRLHeadSelectionBuiltIn(builtInName) || SQWRLNames
        .isSQWRLHeadAggregationBuiltIn(builtInName)) {
        if (argument.isVariable()) {
          String variableName = argument.asVariable().getVariableName();
          if (selectedVariable2ColumnIndices.containsKey(variableName)) {
            selectedVariable2ColumnIndices.get(variableName).add(columnIndex);
          } else {
            selectedVariable2ColumnIndices.put(variableName, new ArrayList<>());
            selectedVariable2ColumnIndices.get(variableName).add(columnIndex);
          }
        }
        if (builtInName.equalsIgnoreCase(SQWRLNames.Select)) {
          processSelectArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.SelectDistinct)) {
          processSelectDistinctArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.Count)) {
          processCountArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.CountDistinct)) {
          processCountDistinctArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.Min)) {
          processMinArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.Max)) {
          processMaxArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.Sum)) {
          processSumArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.Median)) {
          processMedianArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.Avg)) {
          processAverageArgument(argument);
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.OrderBy)) {
          if (!argument.isVariable()) {
            throw new SQWRLException(
              "only variables allowed for ordered columns - found " + argument.getClass().getName());
          }

          processOrderByArgument(selectedVariable2ColumnIndices, argument.asVariable());
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.OrderByDescending)) {
          if (!argument.isVariable()) {
            throw new SQWRLException(
              "only variables allowed for ordered columns - found " + argument.getClass().getName());
          }

          processOrderByDescendingArgument(selectedVariable2ColumnIndices, argument.asVariable());
        } else if (builtInName.equalsIgnoreCase(SQWRLNames.ColumnNames)) {
          processColumnNamesArgument(argument);
        }
      }
      columnIndex++;
    }
  }

  private void processSumArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "sum(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.SumAggregateFunction);
  }

  private void processMaxArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "max(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.MaxAggregateFunction);
  }

  private void processMinArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "min(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.MinAggregateFunction);
  }

  private void processCountArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "count(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.CountAggregateFunction);
  }

  private void processCountDistinctArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "countDistinct(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.CountDistinctAggregateFunction);
  }

  private void processSelectDistinctArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    processSelectArgument(argument);
    this.sqwrlResult.setIsDistinct();
  }

  private void processSelectArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = argument.asVariable().getVariableName();
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addColumn(columnName);
  }

  private void processAverageArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "avg(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "avg";
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.AvgAggregateFunction);
  }

  private void processMedianArgument(@NonNull SWRLBuiltInArgument argument) throws SWRLBuiltInException
  {
    String columnName;
    if (argument.isVariable()) {
      columnName = "median(?" + argument.asVariable().getVariableName() + ")";
    } else {
      columnName = "C" + this.sqwrlResult.getCurrentNumberOfColumns();
    }

    this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.MedianAggregateFunction);
  }

  private void processOrderByArgument(
    @NonNull Map<@NonNull String, @NonNull List<@NonNull Integer>> selectedVariable2ColumnIndices,
    @NonNull SWRLVariableBuiltInArgument argument) throws SQWRLException
  {
    String variableName = argument.getVariableName();

    if (selectedVariable2ColumnIndices.containsKey(variableName)) {
      for (int selectedColumnIndex : selectedVariable2ColumnIndices.get(variableName)) {
        this.sqwrlResult.setOrderByColumn(selectedColumnIndex, true);
      }
    } else {
      throw new SQWRLException("variable ?" + variableName + " must be selected before it can be ordered");
    }
  }

  private void processOrderByDescendingArgument(
    @NonNull Map<@NonNull String, @NonNull List<@NonNull Integer>> selectedVariable2ColumnIndices,
    @NonNull SWRLVariableBuiltInArgument argument) throws SQWRLException
  {
    String variableName = argument.getVariableName();

    if (selectedVariable2ColumnIndices.containsKey(variableName)) {
      for (int selectedColumnIndex : selectedVariable2ColumnIndices.get(variableName)) {
        this.sqwrlResult.setOrderByColumn(selectedColumnIndex, false);
      }
    } else {
      throw new SQWRLException("variable ?" + variableName + " must be selected before it can be ordered");
    }
  }

  private void processColumnNamesArgument(@NonNull SWRLBuiltInArgument argument) throws SQWRLException
  {
    if (argument instanceof SWRLLiteralBuiltInArgument) {
      SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
      Literal literal = this.literalFactory.getLiteral(literalArgument.getLiteral());
      if (literal.isString()) {
        this.sqwrlResult.addColumnDisplayName(literal.getString());
      } else {
        throw new SQWRLException("only string literals allowed as column names - found " + argument);
      }
    } else {
      throw new SQWRLException("only string literals allowed as column names - found " + argument);
    }
  }

  // Process all make collection built-ins.
  private void processSQWRLCollectionMakeBuiltIns(@NonNull Set<@NonNull String> collectionNames)
    throws SWRLBuiltInException
  {
    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
      String collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name

      if (!collectionNames.contains(collectionName)) {
        collectionNames.add(collectionName);
      }
    }
  }

  // We store the group arguments for each collection specified in the make operation; these arguments are later
  // appended to the collection operation built-ins.
  private void processSQWRLCollectionGroupByBuiltIns(@NonNull Set<@NonNull String> collectionNames)
    throws SWRLBuiltInException
  {
    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionGroupByBuiltInNames())) {
      String collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name
      List<@NonNull SWRLBuiltInArgument> builtInArguments = builtInAtom.getBuiltInArguments();
      List<@NonNull SWRLBuiltInArgument> groupArguments = builtInArguments.subList(1, builtInArguments.size());

      if (builtInAtom.getArguments().size() < 2) {
        throw new SQWRLException("groupBy must have at least two arguments");
      }
      if (!collectionNames.contains(collectionName)) {
        throw new SQWRLException("groupBy applied to undefined collection " + collectionName);
      }
      if (this.collectionGroupArgumentsMap.containsKey(collectionName)) {
        throw new SQWRLException("groupBy specified more than once for collection " + collectionName);
      }
      if (hasUnboundArgument(groupArguments)) {
        throw new SQWRLException("unbound group argument passed to groupBy for collection " + collectionName);
      }

      this.collectionGroupArgumentsMap.put(collectionName, groupArguments); // Store group arguments.
    }
  }

  private void processSQWRLCollectionMakeGroupArguments(@NonNull Set<@NonNull String> collectionNames)
    throws SWRLBuiltInException
  {
    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
      String collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name

      if (!collectionNames.contains(collectionName)) {
        throw new SQWRLException("groupBy applied to undefined collection " + collectionName);
      }

      if (this.collectionGroupArgumentsMap.containsKey(collectionName))
      // Append each collections's group arguments to make built-in.
      {
        builtInAtom.addArguments(this.collectionGroupArgumentsMap.get(collectionName));
      }
    }
  }

  private void processSQWRLCollectionOperationBuiltIns(@NonNull Set<@NonNull String> collectionNames,
    @NonNull Set<@NonNull String> cascadedUnboundVariableNames) throws SWRLBuiltInException
  {
    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionOperationBuiltInNames())) {
      List<@NonNull SWRLBuiltInArgument> allOperandCollectionGroupArguments = new ArrayList<>();
      // The group arguments form the operand collections
      List<@NonNull String> variableNames;

      builtInAtom.setUsesSQWRLCollectionResults();

      if (builtInAtom.hasUnboundArguments()) {
        // Keep track of built-in's unbound arguments so that we can mark dependent built-ins.
        Set<@NonNull String> unboundVariableNames = builtInAtom.getUnboundArgumentVariableNames();
        cascadedUnboundVariableNames.addAll(unboundVariableNames);
      }

      // Append the group arguments to built-ins for each of its collection arguments; also append group arguments
      // to collections created by operation built-ins.
      if (isSQWRLCollectionCreateOperation(builtInAtom)) {
        variableNames = builtInAtom.getArgumentsVariableNamesExceptFirst();
      } else {
        variableNames = builtInAtom.getArgumentsVariableNames();
      }

      // The variable refers to a grouped collection.
      // Append each collections's group arguments to built-in.
      variableNames.stream().filter(
        variableName -> collectionNames.contains(variableName) && this.collectionGroupArgumentsMap
          .containsKey(variableName)).forEach(variableName -> {
        // The variable refers to a grouped collection.
        builtInAtom.addArguments(this.collectionGroupArgumentsMap.get(variableName));
        // Append each collections's group arguments to built-in.
        allOperandCollectionGroupArguments.addAll(this.collectionGroupArgumentsMap.get(variableName));
      });

      if (isSQWRLCollectionCreateOperation(builtInAtom)) {
        // If a collection is created we need to recordOWLClassExpression it and store necessary group arguments.
        String createdCollectionName = builtInAtom.getArgumentVariableName(0); // The first argument is the
        // collection
        // name.

        if (!collectionNames.contains(createdCollectionName)) {
          collectionNames.add(createdCollectionName);
        }

        if (!allOperandCollectionGroupArguments.isEmpty()) // // Store group arguments from all operand collections.
        {
          this.collectionGroupArgumentsMap.put(createdCollectionName, allOperandCollectionGroupArguments);
        }
      }
    }
  }

  private void processBuiltInsThatUseSQWRLCollectionOperationResults(
    @NonNull Set<@NonNull String> cascadedUnboundVariableNames) throws SWRLBuiltInException
  {
    // Mark later non SQWRL built-ins that (directly or indirectly) use variables bound by collection operation
    // built-ins.
    // Mark this built-in as dependent on collection built-in bindings.
    // Cascade the dependency from this built-in to others using its arguments.
    // Record its unbound variables too.
    //    getBuiltInAtomsFromBody().stream().filter(builtInAtom -> !isSQWRLBuiltIn(builtInAtom))
    //      .filter(builtInAtom -> builtInAtom.usesAtLeastOneVariableOf(cascadedUnboundVariableNames))
    //      .forEach(builtInAtom -> {
    //        builtInAtom.setUsesSQWRLCollectionResults();
    //        // Mark this built-in as dependent on collection built-in bindings.
    //        if (builtInAtom.hasUnboundArguments())
    //          // Cascade the dependency from this built-in to others using its arguments.
    //          // Record its unbound variables too.
    //          cascadedUnboundVariableNames.addAll(builtInAtom.getUnboundArgumentVariableNames());
    //      });
    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody()) {
      if (!isSQWRLBuiltIn(builtInAtom)) {
        if (builtInAtom.usesAtLeastOneVariableOf(cascadedUnboundVariableNames)) {
          builtInAtom.setUsesSQWRLCollectionResults();
          // Mark this built-in as dependent on collection built-in bindings.
          if (builtInAtom.hasUnboundArguments())
          // Cascade the dependency from this built-in to others using its arguments.
          // Record its unbound variables too.
          {
            cascadedUnboundVariableNames.addAll(builtInAtom.getUnboundArgumentVariableNames());
          }
        }
      }
    }
  }

  /**
   * Give each built-in a unique index proceeding from left to right.
   */
  private void assignBuiltInIndexes()
  {
    int builtInIndex = 0;

    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody()) {
      builtInAtom.setBuiltInIndex(builtInIndex++);
    }
    for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromHead()) {
      builtInAtom.setBuiltInIndex(builtInIndex++);
    }
  }

  private boolean isSQWRLBuiltIn(@NonNull SWRLAPIBuiltInAtom builtInAtom)
  {
    return SQWRLNames.isSQWRLBuiltIn(builtInAtom.getBuiltInPrefixedName());
  }

  private boolean hasUnboundArgument(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    for (SWRLBuiltInArgument argument : arguments) {
      if (argument.isVariable() && argument.asVariable().isUnbound()) {
        return true;
      }
    }
    return false;
  }

  private boolean isSQWRLCollectionOperation(@NonNull SWRLAPIBuiltInAtom builtInAtom)
  {
    return SQWRLNames.isSQWRLCollectionOperationBuiltIn(builtInAtom.getBuiltInPrefixedName());
  }

  private boolean isSQWRLCollectionCreateOperation(@NonNull SWRLAPIBuiltInAtom builtInAtom)
  {
    return SQWRLNames.isSQWRLCollectionCreateOperationBuiltIn(builtInAtom.getBuiltInPrefixedName());
  }

  /**
   * For every built-in atom, recordOWLClassExpression the variables it depends from preceding atoms (directly and
   * indirectly).
   */
  private void generateBuiltInAtomVariableDependencies() throws SWRLBuiltInException
  {
    Map<@NonNull String, @NonNull Set<@NonNull Set<@NonNull String>>> pathMap = new HashMap<>();
    Set<@NonNull String> rootVariableNames = new HashSet<>();

    for (SWRLAtom atom : getBodyAtoms()) {
      Set<@NonNull String> thisAtomReferencedVariableNames = getReferencedVariableNames(atom);

      buildPaths(atom, rootVariableNames, pathMap);

      if (atom instanceof SWRLAPIBuiltInAtom) {
        SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;

        if (isSQWRLGroupCollection(builtInAtom)) {
          continue; // We ignore sqwrl:groupBy variables because they are really directives
        }
        if (isSQWRLCollectionOperation(builtInAtom)) {
          break; // Once we encounter a SQWRL operation we stop because dependencies don't matter for these atoms
        }

        if (hasReferencedVariables(builtInAtom)) {
          Set<@NonNull String> pathVariableNames = new HashSet<>();

          for (String rootVariableName : pathMap.keySet()) {
            pathMap.get(rootVariableName).stream()
              .filter(path -> !Collections.disjoint(path, thisAtomReferencedVariableNames)).forEach(path -> {
              pathVariableNames.addAll(path);
              pathVariableNames.add(rootVariableName);
            });
          }

          if (!pathVariableNames.isEmpty()) {
            pathVariableNames.removeAll(thisAtomReferencedVariableNames); // Remove our own variables
            /*
             * TODO: Need to think about correct operation of this if (builtInAtom.isSQWRLMakeCollection()) { String
             * collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name if
             * (collectionGroupArgumentsMap.containsKey(collectionName)) { List<@NonNull SWRLBuiltInArgument>
             *   groupArguments =
             * collectionGroupArgumentsMap.get(collectionName); Set<@NonNull String> groupVariableNames =
             * getVariableNames(groupArguments); if (!groupVariableNames.isEmpty() &&
             * !pathVariableNames.containsAll(groupVariableNames)) throw new
             * BuiltInException("all group arguments must be on path for corresponding collection make"); } // if }
             */
            builtInAtom.setPathVariableNames(pathVariableNames);
          }
        }
      }
    }
  }

  /**
   * Incrementally build variable dependency paths up to and including the current atom.
   * <p>
   * Note: Sets of sets in Java require care because of hash code issues. The enclosed set should not be modified or the
   * outer set may return inconsistent results.
   */
  private void buildPaths(@NonNull SWRLAtom atom, @NonNull Set<@NonNull String> rootVariableNames,
    @NonNull Map<@NonNull String, @NonNull Set<@NonNull Set<@NonNull String>>> pathMap) throws SWRLBuiltInException
  {
    Set<@NonNull String> currentAtomReferencedVariableNames = getReferencedVariableNames(atom);
    Set<@NonNull String> matchingRootVariableNames;

    if (currentAtomReferencedVariableNames.size() == 1) { // Make root if we have not yet encountered it
      String variableName = currentAtomReferencedVariableNames.iterator().next();
      if (getMatchingPaths(pathMap, variableName).isEmpty() && !rootVariableNames.contains(variableName)) {
        pathMap.put(variableName, new HashSet<>());
        rootVariableNames.add(variableName);
      }
    } else if (currentAtomReferencedVariableNames.size() > 1) {
      Set<@NonNull String> currentKnownAtomRootVariableNames = new HashSet<>(currentAtomReferencedVariableNames);
      currentKnownAtomRootVariableNames.retainAll(rootVariableNames);

      if (!currentKnownAtomRootVariableNames.isEmpty()) {
        // At least one of atom's variables reference already known root(s).
        for (String rootVariableName : currentKnownAtomRootVariableNames) {
          Set<@NonNull String> dependentVariables = new HashSet<>(currentAtomReferencedVariableNames);
          dependentVariables.remove(rootVariableName);

          matchingRootVariableNames = getMatchingRootVariableNames(pathMap, dependentVariables);
          if (!matchingRootVariableNames.isEmpty()) { // Found existing path(s) that use these variables - add them to
            // existing path(s)
            for (String matchingRootVariableName : matchingRootVariableNames) {
              Set<@NonNull Set<@NonNull String>> paths = pathMap.get(matchingRootVariableName);
              Set<@NonNull Set<@NonNull String>> matchedPaths = paths.stream()
                .filter(path -> !Collections.disjoint(path, dependentVariables)).collect(Collectors.toSet());
              for (Set<@NonNull String> matchedPath : matchedPaths) {
                Set<@NonNull String> newPath = new HashSet<>(matchedPath);
                newPath.addAll(dependentVariables);
                paths.remove(matchedPath); // Remove the original matched path for this root's path
                paths.add(Collections.unmodifiableSet(newPath)); // Add the updated path
              }
            }
          } else {
            // Did not loadExternalSWRLBuiltInLibraries existing path for this root using these variables - add
            // dependent variables as new path
            Set<@NonNull Set<@NonNull String>> paths = pathMap.get(rootVariableName);
            paths.add(Collections.unmodifiableSet(dependentVariables));
          }
        }
      } else { // No known roots referenced by any of the atom's variables
        matchingRootVariableNames = getMatchingRootVariableNames(pathMap, currentAtomReferencedVariableNames);
        if (!matchingRootVariableNames.isEmpty()) {
          // Found existing paths that use the atom's variables - add all the variables (none of which is a root) to
          // each path
          for (String matchingRootVariableName : matchingRootVariableNames) {
            Set<@NonNull Set<@NonNull String>> paths = pathMap.get(matchingRootVariableName);
            Set<@NonNull Set<@NonNull String>> matchedPaths = paths.stream()
              .filter(path -> !Collections.disjoint(path, currentAtomReferencedVariableNames))
              .collect(Collectors.toSet());

            for (Set<@NonNull String> matchedPath : matchedPaths) {
              // Add the new variables to the matched path and add it to this root's path
              Set<@NonNull String> newPath = new HashSet<>(matchedPath);
              newPath.addAll(currentAtomReferencedVariableNames);
              // Add all the non-root variable names to this path.
              paths.remove(matchedPath); // Remove the original matched path
              paths.add(Collections.unmodifiableSet(newPath)); // Add the updated path
            }
          }
        } else { // No existing paths have variables from this atom - every variable becomes a root and depends on every
          // other root variable
          for (String rootVariableName : currentAtomReferencedVariableNames) {
            Set<@NonNull Set<@NonNull String>> paths = new HashSet<>();
            Set<@NonNull String> dependentVariables = new HashSet<>(currentAtomReferencedVariableNames);
            dependentVariables.remove(rootVariableName); // Remove the root from its own dependent variables
            paths.add(Collections.unmodifiableSet(dependentVariables));
            pathMap.put(rootVariableName, paths);
            rootVariableNames.add(rootVariableName);
          }
        }
      }
    }
  }

  @NonNull private Set<@NonNull String> getMatchingPaths(
    @NonNull Map<@NonNull String, @NonNull Set<@NonNull Set<@NonNull String>>> pathMap, @NonNull String variableName)
  {
    return getMatchingRootVariableNames(pathMap, Collections.singleton(variableName));
  }

  @NonNull private Set<@NonNull String> getMatchingRootVariableNames(
    @NonNull Map<@NonNull String, @NonNull Set<@NonNull Set<@NonNull String>>> pathMap,
    @NonNull Set<@NonNull String> variableNames)
  {
    Set<@NonNull String> matchingRootVariableNames = new HashSet<>();

    for (String rootVariableName : pathMap.keySet()) {
      Set<@NonNull Set<@NonNull String>> pathsWithSameRoot = pathMap.get(rootVariableName);
      matchingRootVariableNames.addAll(
        pathsWithSameRoot.stream().filter(path -> !Collections.disjoint(path, variableNames))
          .map(path -> rootVariableName).collect(Collectors.toList()));
    }
    return matchingRootVariableNames;
  }

  @NonNull private Set<@NonNull String> getReferencedVariableNames(@NonNull SWRLAtom atom) throws SWRLBuiltInException
  {
    Set<@NonNull String> referencedVariableNames = new HashSet<>();

    for (SWRLArgument argument : atom.getAllArguments()) {
      if (atom instanceof SWRLBuiltInAtom) {
        if (argument instanceof SWRLVariableBuiltInArgument) {
          SWRLVariableBuiltInArgument variableBuiltInArgument = (SWRLVariableBuiltInArgument)argument;
          referencedVariableNames.add(variableBuiltInArgument.getVariableName());
        }
      } else {
        if (argument instanceof SWRLVariable) {
          SWRLVariable swrlVariable = (SWRLVariable)argument;
          IRI variableIRI = swrlVariable.getIRI();
          Optional<String> variableName = iriResolver.iri2VariableName(variableIRI);
          if (variableName.isPresent())
            referencedVariableNames.add(variableName.get());
          else
            throw new SWRLBuiltInException("internal error: could not find variable name for variable IRI " + variableIRI);
        }
      }
    }

    return referencedVariableNames;
  }

  private boolean hasReferencedVariables(@NonNull SWRLAtom atom) throws SWRLBuiltInException
  {
    return !getReferencedVariableNames(atom).isEmpty();
  }
}
