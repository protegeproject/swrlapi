package org.swrlapi.sqwrl;

import org.swrlapi.sqwrl.exceptions.SQWRLInvalidAggregateFunctionNameException;

/**
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.SQWRLResult
 */
public class SQWRLResultNames
{
  public static final String MinAggregateFunction = "min";
  public static final String MaxAggregateFunction = "max";
  public static final String SumAggregateFunction = "sum";
  public static final String AvgAggregateFunction = "avg";
  public static final String MedianAggregateFunction = "median";
  public static final String CountAggregateFunction = "count";
  public static final String CountDistinctAggregateFunction = "countDistinct";

  public static final String aggregateFunctionNames[] = { MinAggregateFunction, MaxAggregateFunction,
    SumAggregateFunction, AvgAggregateFunction, MedianAggregateFunction, CountAggregateFunction,
    CountDistinctAggregateFunction };

  /**
   * @param aggregateFunctionName The name of a function
   * @throws SQWRLInvalidAggregateFunctionNameException If the function is not an aggregate function
   */
  public static void checkAggregateFunctionName(String aggregateFunctionName)
      throws SQWRLInvalidAggregateFunctionNameException
  {
    boolean found = false;

    for (String candidateAggregateFunctionName : aggregateFunctionNames) {
      if (candidateAggregateFunctionName.equalsIgnoreCase(aggregateFunctionName)) {
        found = true;
      }
    }

    if (!found)
      throw new SQWRLInvalidAggregateFunctionNameException("invalid aggregate function " + aggregateFunctionName);
  }
}
