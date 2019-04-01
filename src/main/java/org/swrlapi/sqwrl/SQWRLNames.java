package org.swrlapi.sqwrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidAggregateFunctionNameException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class SQWRLNames
{
  public static final String SQWRLBuiltInLibraryName = "SQWRLBuiltIns";

  public static final String SQWRL_PREFIX = "sqwrl:";
  public static final String SQWRL_NAMESPACE = "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#";

  public static final String Select = SQWRL_PREFIX + "select";
  public static final String SelectDistinct = SQWRL_PREFIX + "selectDistinct";
  public static final String Count = SQWRL_PREFIX + "count";
  public static final String CountDistinct = SQWRL_PREFIX + "countDistinct";
  public static final String Avg = SQWRL_PREFIX + "avg";
  public static final String Min = SQWRL_PREFIX + "min";
  public static final String Max = SQWRL_PREFIX + "max";
  public static final String Sum = SQWRL_PREFIX + "sum";
  public static final String Median = SQWRL_PREFIX + "median";
  public static final String OrderBy = SQWRL_PREFIX + "orderBy";
  public static final String OrderByDescending = SQWRL_PREFIX + "orderByDescending";
  public static final String ColumnNames = SQWRL_PREFIX + "columnNames";
  public static final String CountAggregateFunction = "count";
  public static final String CountDistinctAggregateFunction = "countDistinct";
  public static final String Limit = SQWRL_PREFIX + "limit";

  // Aggregation functions

  public static final String MinAggregateFunction = "min";
  public static final String MaxAggregateFunction = "max";
  public static final String SumAggregateFunction = "sum";
  public static final String AvgAggregateFunction = "avg";
  public static final String MedianAggregateFunction = "median";

  // Collection construction operations
  public static final String MakeSet = SQWRL_PREFIX + "makeSet";
  public static final String MakeBag = SQWRL_PREFIX + "makeBag";
  public static final String GroupBy = SQWRL_PREFIX + "groupBy";

  // Collection operations

  // Core collection operations
  public static final String Size = SQWRL_PREFIX + "size";
  public static final String IsEmpty = SQWRL_PREFIX + "isEmpty";
  public static final String NotIsEmpty = SQWRL_PREFIX + "notIsEmpty";
  public static final String Element = SQWRL_PREFIX + "element";
  public static final String NotElement = SQWRL_PREFIX + "notElement";

  // First and last collection operations
  public static final String LastN = SQWRL_PREFIX + "lastN";
  public static final String NotLastN = SQWRL_PREFIX + "notLastN";
  public static final String FirstN = SQWRL_PREFIX + "firstN";
  public static final String NotFirstN = SQWRL_PREFIX + "notFirstN";

  // nth collection operations
  public static final String Nth = SQWRL_PREFIX + "nth";
  public static final String NotNth = SQWRL_PREFIX + "notNth";
  public static final String NthLast = SQWRL_PREFIX + "nthLast";
  public static final String NotNthLast = SQWRL_PREFIX + "notNthLast";

  // Slicing collection operations
  public static final String NthSlice = SQWRL_PREFIX + "nthSlice";
  public static final String NotNthSlice = SQWRL_PREFIX + "notNthSlice";
  public static final String NthLastSlice = SQWRL_PREFIX + "nthLastSlice";
  public static final String NotNthLastSlice = SQWRL_PREFIX + "notNthLastSlice";

  // Multi-collection operations with collection results
  public static final String Intersection = SQWRL_PREFIX + "intersection";
  public static final String Union = SQWRL_PREFIX + "union";
  public static final String Difference = SQWRL_PREFIX + "difference";
  public static final String Append = SQWRL_PREFIX + "append";

  // Multi-collection operations with non-collection results
  public static final String Intersects = SQWRL_PREFIX + "intersects";
  public static final String NotIntersects = SQWRL_PREFIX + "notIntersects";
  public static final String Equal = SQWRL_PREFIX + "equal";
  public static final String NotEqual = SQWRL_PREFIX + "notEqual";
  public static final String Contains = SQWRL_PREFIX + "contains";
  public static final String NotContains = SQWRL_PREFIX + "notContains";

  // Aliases for first and last operators
  public static final String GreatestN = SQWRL_PREFIX + "greatestN";
  public static final String NotGreatestN = SQWRL_PREFIX + "notGreatestN";
  public static final String LeastN = SQWRL_PREFIX + "leastN";
  public static final String NotLeastN = SQWRL_PREFIX + "notLeastN";
  public static final String NthGreatest = SQWRL_PREFIX + "nthGreatest";
  public static final String NotNthGreatest = SQWRL_PREFIX + "notNthGreatest";
  public static final String NthGreatestSlice = SQWRL_PREFIX + "nthGreatestSlice";
  public static final String NotNthGreatestSlice = SQWRL_PREFIX + "notNthGreatestSlice";

  private static final String[] headSelectionBuiltInNamesArray = { Select, SelectDistinct, OrderBy, OrderByDescending,
    ColumnNames };
  private static final String[] headAggregationBuiltInNamesArray = { Count, CountDistinct, Avg, Median, Min, Max, Sum };

  private static final String[] headSlicingBuiltInNamesArray = { Limit, Nth, NthGreatest, NthLast, NthSlice,
    NthLastSlice, NthGreatestSlice, NotNthGreatestSlice, NotNthLastSlice, NotNthSlice, NotNth, NotNthLast,
    NotNthGreatest, NotFirstN, NotLastN, NotGreatestN, NotLeastN, LastN, FirstN, LeastN, GreatestN };

  private static final String[] collectionMakeBuiltInNamesArray = { MakeSet, MakeBag };
  private static final String[] collectionGroupByBuiltInNamesArray = { GroupBy };

  private static final String[] singleCollectionOperationWithoutCollectionCreateBuiltInNamesArray = { Size, IsEmpty,
    NotIsEmpty, Element, NotElement, Min, Max, Sum, Avg, Median, Nth, NthGreatest, NthLast };

  private static final String[] singleCollectionOperationWithCollectionCreateBuiltInNamesArray = { NthSlice,
    NthLastSlice, NthGreatestSlice, NotNthGreatestSlice, NotNthLastSlice, NotNthSlice, NotNth, NotNthLast,
    NotNthGreatest, NotFirstN, NotLastN, NotGreatestN, NotLeastN, LastN, FirstN, LeastN, GreatestN };

  private static final String[] multiCollectionOperationWithoutCollectionCreateBuiltInNamesArray = { Intersects,
    NotIntersects, Equal, NotEqual, Contains, NotContains };

  private static final String[] multiCollectionOperationWithCollectionCreateBuiltInNamesArray = { Intersection, Union,
    Difference, Append };

  public static final String[] aggregateFunctionNames = { MinAggregateFunction, MaxAggregateFunction,
    SumAggregateFunction, AvgAggregateFunction, MedianAggregateFunction, CountAggregateFunction,
    CountDistinctAggregateFunction };

  @NonNull private static final Set<@NonNull String> sqwrlBuiltInNames;
  @NonNull private static final Set<@NonNull String> headBuiltInNames;
  @NonNull private static final Set<@NonNull String> headSelectionBuiltInNames;
  @NonNull private static final Set<@NonNull String> headAggregationBuiltInNames;
  @NonNull private static final Set<@NonNull String> headSlicingBuiltInNames;
  @NonNull private static final Set<@NonNull String> collectionMakeBuiltInNames;
  @NonNull private static final Set<@NonNull String> collectionGroupByBuiltInNames;
  @NonNull private static final Set<@NonNull String> collectionCreateOperationBuiltInNames;
  @NonNull private static final Set<@NonNull String> collectionOperationBuiltInNames;
  @NonNull private static final Set<@NonNull String> singleCollectionOperationWithCollectionCreateBuiltInNames;
  @NonNull private static final Set<@NonNull String> singleCollectionOperationWithoutCollectionCreateBuiltInNames;
  @NonNull private static final Set<@NonNull String> multiCollectionOperationWithCollectionCreateBuiltInNames;
  @NonNull private static final Set<@NonNull String> multiCollectionOperationWithoutCollectionCreateBuiltInNames;

  static {
    sqwrlBuiltInNames = new HashSet<>();

    headBuiltInNames = new HashSet<>();
    headSelectionBuiltInNames = new HashSet<>();
    headAggregationBuiltInNames = new HashSet<>();
    headSlicingBuiltInNames = new HashSet<>();

    collectionMakeBuiltInNames = new HashSet<>();
    collectionGroupByBuiltInNames = new HashSet<>();

    collectionCreateOperationBuiltInNames = new HashSet<>();
    collectionOperationBuiltInNames = new HashSet<>();
    singleCollectionOperationWithCollectionCreateBuiltInNames = new HashSet<>();
    singleCollectionOperationWithoutCollectionCreateBuiltInNames = new HashSet<>();
    multiCollectionOperationWithCollectionCreateBuiltInNames = new HashSet<>();
    multiCollectionOperationWithoutCollectionCreateBuiltInNames = new HashSet<>();

    Collections.addAll(headSelectionBuiltInNames, headSelectionBuiltInNamesArray);
    Collections.addAll(headAggregationBuiltInNames, headAggregationBuiltInNamesArray);
    Collections.addAll(headSlicingBuiltInNames, headSlicingBuiltInNamesArray);
    headBuiltInNames.addAll(headSelectionBuiltInNames);
    headBuiltInNames.addAll(headAggregationBuiltInNames);
    headBuiltInNames.addAll(headSlicingBuiltInNames);
    sqwrlBuiltInNames.addAll(headBuiltInNames);

    Collections.addAll(collectionMakeBuiltInNames, collectionMakeBuiltInNamesArray);
    collectionCreateOperationBuiltInNames.addAll(collectionMakeBuiltInNames);
    sqwrlBuiltInNames.addAll(collectionMakeBuiltInNames);

    Collections.addAll(collectionGroupByBuiltInNames, collectionGroupByBuiltInNamesArray);
    sqwrlBuiltInNames.addAll(collectionGroupByBuiltInNames);

    Collections.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames,
      singleCollectionOperationWithCollectionCreateBuiltInNamesArray);
    collectionCreateOperationBuiltInNames.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames);
    collectionOperationBuiltInNames.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames);

    Collections.addAll(singleCollectionOperationWithoutCollectionCreateBuiltInNames,
      singleCollectionOperationWithoutCollectionCreateBuiltInNamesArray);
    collectionOperationBuiltInNames.addAll(singleCollectionOperationWithoutCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(singleCollectionOperationWithoutCollectionCreateBuiltInNames);

    Collections.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames,
      multiCollectionOperationWithCollectionCreateBuiltInNamesArray);
    collectionCreateOperationBuiltInNames.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames);
    collectionOperationBuiltInNames.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames);

    Collections.addAll(multiCollectionOperationWithoutCollectionCreateBuiltInNames,
      multiCollectionOperationWithoutCollectionCreateBuiltInNamesArray);
    collectionOperationBuiltInNames.addAll(multiCollectionOperationWithoutCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(multiCollectionOperationWithoutCollectionCreateBuiltInNames);
  }

  @NonNull public static Set<@NonNull String> getSQWRLBuiltInNames()
  {
    return sqwrlBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getHeadBuiltInNames()
  {
    return headBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getHeadSlicingBuiltInNames()
  {
    return headSlicingBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getHeadSelectionBuiltInNames()
  {
    return headSelectionBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getCollectionMakeBuiltInNames()
  {
    return collectionMakeBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getCollectionGroupByBuiltInNames()
  {
    return collectionGroupByBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getCollectionCreateBuiltInNames()
  {
    return collectionCreateOperationBuiltInNames;
  }

  @NonNull public static Set<@NonNull String> getCollectionOperationBuiltInNames()
  {
    return collectionOperationBuiltInNames;
  }

  public static boolean isSQWRLBuiltIn(@NonNull String builtInName)
  {
    return sqwrlBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadBuiltIn(@NonNull String builtInName)
  {
    return headBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadSelectionBuiltIn(@NonNull String builtInName)
  {
    return headSelectionBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadAggregationBuiltIn(@NonNull String builtInName)
  {
    return headAggregationBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadSlicingBuiltIn(@NonNull String builtInName)
  {
    return headSlicingBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionMakeBuiltIn(@NonNull String builtInName)
  {
    return collectionMakeBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionGroupByBuiltIn(@NonNull String builtInName)
  {
    return collectionGroupByBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionCreateOperationBuiltIn(@NonNull String builtInName)
  {
    return collectionCreateOperationBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionOperationBuiltIn(@NonNull String builtInName)
  {
    return collectionOperationBuiltInNames.contains(builtInName);
  }

  public static void checkAggregateFunctionName(@NonNull String aggregateFunctionName)
    throws SQWRLInvalidAggregateFunctionNameException
  {
    boolean found = false;

    for (String candidateAggregateFunctionName : aggregateFunctionNames)
      if (candidateAggregateFunctionName.equalsIgnoreCase(aggregateFunctionName))
        found = true;

    if (!found)
      throw new SQWRLInvalidAggregateFunctionNameException("invalid aggregate function " + aggregateFunctionName);
  }
}
