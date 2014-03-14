package org.swrlapi.sqwrl;

import org.swrlapi.sqwrl.exceptions.SQWRLInvalidAggregateFunctionNameException;

public class SQWRLResultNames
{
	// Aggregation
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
