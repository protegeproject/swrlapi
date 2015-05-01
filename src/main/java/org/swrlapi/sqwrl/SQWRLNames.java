package org.swrlapi.sqwrl;

import java.util.HashSet;
import java.util.Set;

import org.swrlapi.sqwrl.exceptions.SQWRLInvalidAggregateFunctionNameException;

/**
 * @see org.swrlapi.sqwrl.SQWRLQuery
 */
public class SQWRLNames
{
  public static String SQWRLBuiltInLibraryName = "SQWRLBuiltIns";

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

  // Aggregation
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
  public static final String Size = SQWRLPrefix + "size";
  public static final String IsEmpty = SQWRLPrefix + "isEmpty";
  public static final String NotIsEmpty = SQWRLPrefix + "notIsEmpty";
  public static final String Element = SQWRLPrefix + "element";
  public static final String NotElement = SQWRLPrefix + "notElement";

  // First and last
  public static final String Last = SQWRLPrefix + "last";
  public static final String NotLast = SQWRLPrefix + "notLast";
  public static final String LastN = SQWRLPrefix + "lastN";
  public static final String NotLastN = SQWRLPrefix + "notLastN";
  public static final String First = SQWRLPrefix + "first";
  public static final String NotFirst = SQWRLPrefix + "notFirst";
  public static final String FirstN = SQWRLPrefix + "firstN";
  public static final String NotFirstN = SQWRLPrefix + "notFirstN";

  // nth
  public static final String Nth = SQWRLPrefix + "nth";
  public static final String NotNth = SQWRLPrefix + "notNth";
  public static final String NthLast = SQWRLPrefix + "nthLast";
  public static final String NotNthLast = SQWRLPrefix + "notNthLast";

  // Slicing
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

  private static Set<String> sqwrlBuiltInNames;
  private static Set<String> headBuiltInNames, headSelectionBuiltInNames, headAggregationBuiltInNames,
  headSlicingBuiltInNames;
  private static Set<String> collectionMakeBuiltInNames, collectionGroupByBuiltInNames;
  private static Set<String> collectionCreateOperationBuiltInNames, collectionOperationBuiltInNames;
  private static Set<String> singleCollectionOperationWithCollectionCreateBuiltInNames;
  private static Set<String> singleCollectionOperationWithoutCollectionCreateBuiltInNames;
  private static Set<String> multiCollectionOperationWithCollectionCreateBuiltInNames;
  private static Set<String> multiCollectionOperationWithoutCollectionCreateBuiltInNames;

  static {
    sqwrlBuiltInNames = new HashSet<String>();

    headBuiltInNames = new HashSet<String>();
    headSelectionBuiltInNames = new HashSet<String>();
    headAggregationBuiltInNames = new HashSet<String>();
    headSlicingBuiltInNames = new HashSet<String>();

    collectionMakeBuiltInNames = new HashSet<String>();
    collectionGroupByBuiltInNames = new HashSet<String>();

    collectionCreateOperationBuiltInNames = new HashSet<String>();
    collectionOperationBuiltInNames = new HashSet<String>();
    singleCollectionOperationWithCollectionCreateBuiltInNames = new HashSet<String>();
    singleCollectionOperationWithoutCollectionCreateBuiltInNames = new HashSet<String>();
    multiCollectionOperationWithCollectionCreateBuiltInNames = new HashSet<String>();
    multiCollectionOperationWithoutCollectionCreateBuiltInNames = new HashSet<String>();

    for (String builtInName : headSelectionBuiltInNamesArray)
      headSelectionBuiltInNames.add(builtInName);
    for (String builtInName : headAggregationBuiltInNamesArray)
      headAggregationBuiltInNames.add(builtInName);
    for (String builtInName : headSlicingBuiltInNamesArray)
      headSlicingBuiltInNames.add(builtInName);
    headBuiltInNames.addAll(headSelectionBuiltInNames);
    headBuiltInNames.addAll(headAggregationBuiltInNames);
    headBuiltInNames.addAll(headSlicingBuiltInNames);
    sqwrlBuiltInNames.addAll(headBuiltInNames);

    for (String builtInName : collectionMakeBuiltInNamesArray)
      collectionMakeBuiltInNames.add(builtInName);
    collectionCreateOperationBuiltInNames.addAll(collectionMakeBuiltInNames);
    sqwrlBuiltInNames.addAll(collectionMakeBuiltInNames);

    for (String builtInName : collectionGroupByBuiltInNamesArray)
      collectionGroupByBuiltInNames.add(builtInName);
    sqwrlBuiltInNames.addAll(collectionGroupByBuiltInNames);

    for (String builtInName : singleCollectionOperationWithCollectionCreateBuiltInNamesArray)
      singleCollectionOperationWithCollectionCreateBuiltInNames.add(builtInName);
    collectionCreateOperationBuiltInNames.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames);
    collectionOperationBuiltInNames.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(singleCollectionOperationWithCollectionCreateBuiltInNames);

    for (String builtInName : singleCollectionOperationWithoutCollectionCreateBuiltInNamesArray)
      singleCollectionOperationWithoutCollectionCreateBuiltInNames.add(builtInName);
    collectionOperationBuiltInNames.addAll(singleCollectionOperationWithoutCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(singleCollectionOperationWithoutCollectionCreateBuiltInNames);

    for (String builtInName : multiCollectionOperationWithCollectionCreateBuiltInNamesArray)
      multiCollectionOperationWithCollectionCreateBuiltInNames.add(builtInName);
    collectionCreateOperationBuiltInNames.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames);
    collectionOperationBuiltInNames.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(multiCollectionOperationWithCollectionCreateBuiltInNames);

    for (String builtInName : multiCollectionOperationWithoutCollectionCreateBuiltInNamesArray)
      multiCollectionOperationWithoutCollectionCreateBuiltInNames.add(builtInName);
    collectionOperationBuiltInNames.addAll(multiCollectionOperationWithoutCollectionCreateBuiltInNames);
    sqwrlBuiltInNames.addAll(multiCollectionOperationWithoutCollectionCreateBuiltInNames);
  }

  public static Set<String> getSQWRLBuiltInNames()
  {
    return sqwrlBuiltInNames;
  }

  public static Set<String> getHeadBuiltInNames()
  {
    return headBuiltInNames;
  }

  public static Set<String> getHeadSlicingBuiltInNames()
  {
    return headSlicingBuiltInNames;
  }

  public static Set<String> getHeadSelectionBuiltInNames()
  {
    return headSelectionBuiltInNames;
  }

  public static Set<String> getCollectionMakeBuiltInNames()
  {
    return collectionMakeBuiltInNames;
  }

  public static Set<String> getCollectionGroupByBuiltInNames()
  {
    return collectionGroupByBuiltInNames;
  }

  public static Set<String> getCollectionCreateBuiltInNames()
  {
    return collectionCreateOperationBuiltInNames;
  }

  public static Set<String> getCollectionOperationBuiltInNames()
  {
    return collectionOperationBuiltInNames;
  }

  public static boolean isSQWRLBuiltIn(String builtInName)
  {
    return sqwrlBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadBuiltIn(String builtInName)
  {
    return headBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadSelectionBuiltIn(String builtInName)
  {
    return headSelectionBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadAggregationBuiltIn(String builtInName)
  {
    return headAggregationBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLHeadSlicingBuiltIn(String builtInName)
  {
    return headSlicingBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionMakeBuiltIn(String builtInName)
  {
    return collectionMakeBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionGroupByBuiltIn(String builtInName)
  {
    return collectionGroupByBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionCreateOperationBuiltIn(String builtInName)
  {
    return collectionCreateOperationBuiltInNames.contains(builtInName);
  }

  public static boolean isSQWRLCollectionOperationBuiltIn(String builtInName)
  {
    return collectionOperationBuiltInNames.contains(builtInName);
  }

  public static void checkAggregateFunctionName(String aggregateFunctionName)
      throws SQWRLInvalidAggregateFunctionNameException
  {
    boolean found = false;

    for (int i = 0; i < aggregateFunctionNames.length; i++)
      if (aggregateFunctionNames[i].equalsIgnoreCase(aggregateFunctionName))
        found = true;

    if (!found)
      throw new SQWRLInvalidAggregateFunctionNameException("invalid aggregate function " + aggregateFunctionName);
  }
}
