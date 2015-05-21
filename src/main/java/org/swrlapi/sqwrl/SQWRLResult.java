package org.swrlapi.sqwrl;

import checkers.nullness.quals.NonNull;
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
 * <p>
 * See the <tt>org.swrlapi.factory.SQWRLResultManager</tt> class for detailed comments.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.values.SQWRLResultValue
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 */
public interface SQWRLResult
{
  /**
   * @return The number of columns in the result
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  int getNumberOfColumns() throws SQWRLException;

  /**
   * @return The result column names
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  @NonNull List<String> getColumnNames() throws SQWRLException;

  /**
   * @param columnIndex A column index A column index
   * @return The name of the specified column
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  String getColumnName(int columnIndex) throws SQWRLException;

  /**
   * @return True if the result is empty
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  boolean isEmpty() throws SQWRLException;

  /**
   * @return The number of rows in the result
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  int getNumberOfRows() throws SQWRLException;

  /**
   * Reset the result so that iteration can begin again
   * 
   * @throws SQWRLException If an error occurs during resetting
   */
  void reset() throws SQWRLException;

  /**
   * @return Advance to the next row
   * @throws SQWRLException If we are at the end of the result
   */
  boolean next() throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return A class result value
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  @NonNull SQWRLClassResultValue getClass(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A class result value
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  @NonNull SQWRLClassResultValue getClass(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return An individual result value
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  @NonNull SQWRLIndividualResultValue getIndividual(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return An individual result value
   * @throws SQWRLException If an error occurs during processing If an error occurs during processing
   */
  @NonNull SQWRLIndividualResultValue getIndividual(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return An object property result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLObjectPropertyResultValue getObjectProperty(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return An object property result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLObjectPropertyResultValue getObjectProperty(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return A data property result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLDataPropertyResultValue getDataProperty(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A data property result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLDataPropertyResultValue getDataProperty(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return An annotation property result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationProperty(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return An annotation property result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationProperty(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return A literal result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLLiteralResultValue getLiteral(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A literal result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLLiteralResultValue getLiteral(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is a class
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasClassValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is a class
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasClassValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is an individual
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasIndividualValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is an individual
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasIndividualValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is an object property
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasObjectPropertyValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is an object property
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasObjectPropertyValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is a data property
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasDataPropertyValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is a data property
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasDataPropertyValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return True if the value of the specified column is an annotation property
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasAnnotationPropertyValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is an annotation property
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasAnnotationPropertyValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return True if the value of the specified column is a literal
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasLiteralValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is a literal
   * @throws SQWRLException If an error occurs during processing
   */
  boolean hasLiteralValue(int columnIndex) throws SQWRLException;

  /**
   * @return A list of SQWRL result values
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull List<SQWRLResultValue> getRow() throws SQWRLException;

  /**
   * @param columnName A column name
   * @return The SQWRL result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLResultValue getValue(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return The SQWRL result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLResultValue getValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnIndex The 0-based column index
   * @param rowIndex The 0-based row index
   * @return The SQWRL result value
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull SQWRLResultValue getValue(int columnIndex, int rowIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return A list of SQWRL result values
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull List<SQWRLResultValue> getColumn(String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A list of SQWRL result values
   * @throws SQWRLException If an error occurs during processing
   */
  @NonNull List<SQWRLResultValue> getColumn(int columnIndex) throws SQWRLException;
}
