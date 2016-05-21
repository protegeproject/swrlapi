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

  public static final String SQWRLPrefix = "sqwrl:";
  public static final String SQWRLNamespace = "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#";

  public static final String Select = SQWRLPrefix + "select";
  public static final String SelectDistinct = SQWRLPrefix + "selectDistinct";
  public static final String Count = SQWRLPrefix + "count";
  public static final String CountDistinct = SQWRLPrefix + "countDistinct";
  public static final String Avg = SQWRLPrefix + "avg";
  public static final String Min = SQWRLPrefix + "min";
  public static final String Max = SQWRLPrefix + "max";
  public static final String Sum = SQWRLPrefix + "sum";
  public static final String Median = SQWRLPrefix + "median";
  public static final String OrderBy = SQWRLPrefix + "orderBy";
  public static final String OrderByDescending = SQWRLPrefix + "orderByDescending";
  public static final String ColumnNames = SQWRLPrefix + "columnNames";
  public static final String CountAggregateFunction = "count";
  public static final String CountDistinctAggregateFunction = "countDistinct";
  public static final String Limit = SQWRLPrefix + "limit";

  // Aggregation functions

  public static final String MinAggregateFunction = "min";
  public static final String MaxAggregateFunction = "max";
  public static final String SumAggregateFunction = "sum";
  public static final String AvgAggregateFunction = "avg";
  public static final String MedianAggregateFunction = "median";

  // Collection construction operations
  public static final String MakeSet = SQWRLPrefix + "makeSet";
  public static final String MakeBag = SQWRLPrefix + "makeBag";
  public static final String GroupBy = SQWRLPrefix + "groupBy";

  // Collection operations

  // Core collection operations
  public static final String Size = SQWRLPrefix + "size";
  public static final String IsEmpty = SQWRLPrefix + "isEmpty";
  public static final String NotIsEmpty = SQWRLPrefix + "notIsEmpty";
  public static final String Element = SQWRLPrefix + "element";
  public static final String NotElement = SQWRLPrefix + "notElement";

  // First and last collection operations
  public static final String Last = SQWRLPrefix + "last";
  public static final String NotLast = SQWRLPrefix + "notLast";
  public static final String LastN = SQWRLPrefix + "lastN";
  public static final String NotLastN = SQWRLPrefix + "notLastN";
  public static final String First = SQWRLPrefix + "first";
  public static final String NotFirst = SQWRLPrefix + "notFirst";
  public static final String FirstN = SQWRLPrefix + "firstN";
  public static final String NotFirstN = SQWRLPrefix + "notFirstN";

  // nth collection operations
  public static final String Nth = SQWRLPrefix + "nth";
  public static final String NotNth = SQWRLPrefix + "notNth";
  public static final String NthLast = SQWRLPrefix + "nthLast";
  public static final String NotNthLast = SQWRLPrefix + "notNthLast";

  // Slicing collection operations
  public static final String NthSlice = SQWRLPrefix + "nthSlice";
  public static final String NotNthSlice = SQWRLPrefix + "notNthSlice";
  public static final String NthLastSlice = SQWRLPrefix + "nthLastSlice";
  public static final String NotNthLastSlice = SQWRLPrefix + "notNthLastSlice";

  // Multi-collection operations with collection results
  public static final String Intersection = SQWRLPrefix + "intersection";
  public static final String Union = SQWRLPrefix + "union";
  public static final String Difference = SQWRLPrefix + "difference";
  public static final String Append = SQWRLPrefix + "append";

  // Multi-collection operations with non-collection results
  public static final String Intersects = SQWRLPrefix + "intersects";
  public static final String NotIntersects = SQWRLPrefix + "notIntersects";
  public static final String Equal = SQWRLPrefix + "equal";
  public static final String NotEqual = SQWRLPrefix + "notEqual";
  public static final String Contains = SQWRLPrefix + "contains";
  public static final String NotContains = SQWRLPrefix + "notContains";

  // Aliases for first and last operators
  public static final String Greatest = SQWRLPrefix + "greatest";
  public static final String NotGreatest = SQWRLPrefix + "notGreatest";
  public static final String GreatestN = SQWRLPrefix + "greatestN";
  public static final String NotGreatestN = SQWRLPrefix + "notGreatestN";
  public static final String Least = SQWRLPrefix + "least";
  public static final String NotLeast = SQWRLPrefix + "notLeast";
  public static final String LeastN = SQWRLPrefix + "leastN";
  public static final String NotLeastN = SQWRLPrefix + "notLeastN";
  public static final String NthGreatest = SQWRLPrefix + "nthGreatest";
  public static final String NotNthGreatest = SQWRLPrefix + "notNthGreatest";
  public static final String NthGreatestSlice = SQWRLPrefix + "nthGreatestSlice";
  public static final String NotNthGreatestSlice = SQWRLPrefix + "notNthGreatestSlice";

  private static final String headSelectionBuiltInNamesArray[] = { Select, SelectDistinct, OrderBy, OrderByDescending,
    ColumnNames };
  private static final String headAggregationBuiltInNamesArray[] = { Count, CountDistinct, Avg, Median, Min, Max, Sum };

  private static final String headSlicingBuiltInNamesArray[] = { Limit, Nth, NthGreatest, NthLast, NthSlice,
    NthLastSlice, NthGreatestSlice, NotNthGreatestSlice, NotNthLastSlice, NotNthSlice, NotNth, NotNthLast,
    NotNthGreatest, NotFirst, NotFirstN, NotLast, NotLastN, NotGreatestN, NotGreatest, NotLeastN, NotLeast, LastN,
    FirstN, LeastN, GreatestN };

  private static final String collectionMakeBuiltInNamesArray[] = { MakeSet, MakeBag };
  private static final String collectionGroupByBuiltInNamesArray[] = { GroupBy };

  private static final String singleCollectionOperationWithoutCollectionCreateBuiltInNamesArray[] = { Size, IsEmpty,
    NotIsEmpty, Element, NotElement, First, Last, Least, Greatest, Min, Max, Sum, Avg, Median, Nth, NthGreatest,
    NthLast };

  private static final String singleCollectionOperationWithCollectionCreateBuiltInNamesArray[] = { NthSlice,
    NthLastSlice, NthGreatestSlice, NotNthGreatestSlice, NotNthLastSlice, NotNthSlice, NotNth, NotNthLast,
    NotNthGreatest, NotFirst, NotFirstN, NotLast, NotLastN, NotGreatestN, NotGreatest, NotLeastN, NotLeast, LastN,
    FirstN, LeastN, GreatestN };

  private static final String multiCollectionOperationWithoutCollectionCreateBuiltInNamesArray[] = { Intersects,
    NotIntersects, Equal, NotEqual, Contains, NotContains };

  private static final String multiCollectionOperationWithCollectionCreateBuiltInNamesArray[] = { Intersection, Union,
    Difference, Append };

  public static final String aggregateFunctionNames[] = { MinAggregateFunction, MaxAggregateFunction,
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
