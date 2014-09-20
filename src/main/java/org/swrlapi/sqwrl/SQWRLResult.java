package org.swrlapi.sqwrl;

import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

import java.util.List;

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
	int getNumberOfColumns() throws SQWRLException;

	List<String> getColumnNames() throws SQWRLException;

	String getColumnName(int columnIndex) throws SQWRLException;

	boolean isEmpty() throws SQWRLException;

	int getNumberOfRows() throws SQWRLException;

	void reset() throws SQWRLException;

	boolean next() throws SQWRLException;

	SQWRLClassResultValue getClass(String columnName) throws SQWRLException;

	SQWRLClassResultValue getClass(int columnIndex) throws SQWRLException;

	SQWRLIndividualResultValue getIndividual(String columnName) throws SQWRLException;

	SQWRLIndividualResultValue getIndividual(int columnIndex) throws SQWRLException;

	SQWRLObjectPropertyResultValue getObjectProperty(String columnName) throws SQWRLException;

	SQWRLObjectPropertyResultValue getObjectProperty(int columnIndex) throws SQWRLException;

	SQWRLDataPropertyResultValue getDataProperty(String columnName) throws SQWRLException;

	SQWRLDataPropertyResultValue getDataProperty(int columnIndex) throws SQWRLException;

	SQWRLAnnotationPropertyResultValue getAnnotationProperty(String columnName) throws SQWRLException;

	SQWRLAnnotationPropertyResultValue getAnnotationProperty(int columnIndex) throws SQWRLException;

	SQWRLLiteralResultValue getLiteral(String columnName) throws SQWRLException;

	SQWRLLiteralResultValue getLiteral(int columnIndex) throws SQWRLException;

	boolean hasClassValue(String columnName) throws SQWRLException;

	boolean hasClassValue(int columnIndex) throws SQWRLException;

	boolean hasIndividualValue(String columnName) throws SQWRLException;

	boolean hasIndividualValue(int columnIndex) throws SQWRLException;

	boolean hasObjectPropertyValue(String columnName) throws SQWRLException;

	boolean hasObjectPropertyValue(int columnIndex) throws SQWRLException;

	boolean hasDataPropertyValue(String columnName) throws SQWRLException;

	boolean hasDataPropertyValue(int columnIndex) throws SQWRLException;

	boolean hasAnnotationPropertyValue(String columnName) throws SQWRLException;

	boolean hasAnnotationPropertyValue(int columnIndex) throws SQWRLException;

	boolean hasLiteralValue(String columnName) throws SQWRLException;

	boolean hasLiteralValue(int columnIndex) throws SQWRLException;

	List<SQWRLResultValue> getRow() throws SQWRLException;

	SQWRLResultValue getValue(String columnName) throws SQWRLException;

	SQWRLResultValue getValue(int columnIndex) throws SQWRLException;

	SQWRLResultValue getValue(int columnIndex, int rowIndex) throws SQWRLException;

	List<SQWRLResultValue> getColumn(String columnName) throws SQWRLException;

	List<SQWRLResultValue> getColumn(int columnIndex) throws SQWRLException;

}
