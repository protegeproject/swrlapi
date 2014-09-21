package org.swrlapi.sqwrl;

import java.util.List;

import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

/**
 * Interface to configure a query result and add data to it. See the {@link org.swrlapi.sqwrl.DefaultSQWRLResult}
 * class for detailed comments.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.SQWRLQueryEngine
 * @see org.swrlapi.sqwrl.DefaultSQWRLResult
 */
public interface SQWRLResultGenerator
{
	void addColumns(List<String> columnNames) throws SQWRLException;

	void addColumn(String columnName) throws SQWRLException;

	void addAggregateColumn(String columnName, String aggregateFunctionName) throws SQWRLException;

	void addOrderByColumn(int orderedColumnIndex, boolean ascending) throws SQWRLException;

	boolean isOrdered();

	boolean isAscending();

	void setIsDistinct();

	void addColumnDisplayName(String columnName) throws SQWRLException;

	boolean isConfigured();

	void configured() throws SQWRLException;

	void addRow(List<SQWRLResultValue> resultValues) throws SQWRLException;

	void openRow() throws SQWRLException;

	void addRowData(SQWRLResultValue value) throws SQWRLException;

	void closeRow() throws SQWRLException;

	boolean isRowOpen();

	boolean isPrepared();

	void prepared() throws SQWRLException;

	int getCurrentNumberOfColumns() throws SQWRLException;
}
