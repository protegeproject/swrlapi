package org.swrlapi.sqwrl;

import java.util.List;

import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.*;

/**
 * Interface that defines methods to process results from a SQWRL query.
 * <p/>
 * See the {@link org.swrlapi.sqwrl.DefaultSQWRLResult} class for detailed comments.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.values.SQWRLResultValue
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 * @see org.swrlapi.sqwrl.DefaultSQWRLResult
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

	List<SQWRLResultValue> getColumn(String columnName) throws SQWRLException;

	List<SQWRLResultValue> getColumn(int columnIndex) throws SQWRLException;

	SQWRLClassResultValue getClassValue(String columnName) throws SQWRLException;

	SQWRLClassResultValue getClassValue(int columnIndex) throws SQWRLException;

	SQWRLIndividualResultValue getObjectValue(String columnName) throws SQWRLException;

	SQWRLIndividualResultValue getObjectValue(int columnIndex) throws SQWRLException;

	SQWRLPropertyResultValue getPropertyValue(String columnName) throws SQWRLException;

	SQWRLPropertyResultValue getPropertyValue(int columnIndex) throws SQWRLException;

	SQWRLLiteralResultValue getLiteralValue(String columnName) throws SQWRLException;

	SQWRLLiteralResultValue getLiteralValue(int columnIndex) throws SQWRLException;
}
