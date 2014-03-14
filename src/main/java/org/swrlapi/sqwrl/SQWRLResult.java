package org.swrlapi.sqwrl;

import java.util.List;

import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLClassValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualValue;
import org.swrlapi.sqwrl.values.SQWRLPropertyValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

/**
 * Interface that defines methods to process results from a query. See the {@link DefaultSQWRLResult} class for detailed
 * comments.
 */
public interface SQWRLResult
{
	List<String> getColumnNames() throws SQWRLException;

	int getNumberOfColumns() throws SQWRLException;

	String getColumnName(int columnIndex) throws SQWRLException;

	boolean isEmpty() throws SQWRLException;

	int getNumberOfRows() throws SQWRLException;

	void reset() throws SQWRLException;

	void next() throws SQWRLException;

	boolean hasNext() throws SQWRLException;

	boolean hasObjectValue(String columnName) throws SQWRLException;

	boolean hasObjectValue(int columnIndex) throws SQWRLException;

	boolean hasLiteralValue(String columnName) throws SQWRLException;

	boolean hasLiteralValue(int columnIndex) throws SQWRLException;

	boolean hasClassValue(String columnName) throws SQWRLException;

	boolean hasClassValue(int columnIndex) throws SQWRLException;

	boolean hasPropertyValue(String columnName) throws SQWRLException;

	boolean hasPropertyValue(int columnIndex) throws SQWRLException;

	List<SQWRLResultValue> getRow() throws SQWRLException;

	SQWRLResultValue getValue(String columnName) throws SQWRLException;

	SQWRLResultValue getValue(int columnIndex) throws SQWRLException;

	SQWRLResultValue getValue(int columnIndex, int rowIndex) throws SQWRLException;

	SQWRLClassValue getClassValue(String columnName) throws SQWRLException;

	SQWRLClassValue getClassValue(int columnIndex) throws SQWRLException;

	SQWRLIndividualValue getObjectValue(String columnName) throws SQWRLException;

	SQWRLIndividualValue getObjectValue(int columnIndex) throws SQWRLException;

	SQWRLPropertyValue getPropertyValue(String columnName) throws SQWRLException;

	SQWRLPropertyValue getPropertyValue(int columnIndex) throws SQWRLException;

	SQWRLResultValue getLiteralValue(String columnName) throws SQWRLException;

	SQWRLResultValue getLiteralValue(int columnIndex) throws SQWRLException;

	List<SQWRLResultValue> getColumn(String columnName) throws SQWRLException;

	List<SQWRLResultValue> getColumn(int columnIndex) throws SQWRLException;
}
