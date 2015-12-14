package org.swrlapi.sqwrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

import java.util.List;

/**
 * Interface that defines methods to process results from a SQWRL query.
 * <p>
 * See the {@link org.swrlapi.sqwrl.SQWRLResultManager} interface for detailed comments on using this interface.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.SQWRLResultManager
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 * @see org.swrlapi.sqwrl.values.SQWRLResultValue
 */
public interface SQWRLResult
{
  /**
   * @return The number of columns in the result
   * @throws SQWRLException If an error occurs
   */
  int getNumberOfColumns() throws SQWRLException;

  /**
   * @return The result column names
   * @throws SQWRLException If an error occurs
   */
  @NonNull List<@NonNull String> getColumnNames() throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return The name of the specified column
   * @throws SQWRLException If an error occurs
   */
  @NonNull String getColumnName(int columnIndex) throws SQWRLException;

  /**
   * @return True if the result is empty
   * @throws SQWRLException If an error occurs during processing
   */
  boolean isEmpty() throws SQWRLException;

  /**
   * @return The number of rows in the result
   * @throws SQWRLException If an error occurs
   */
  int getNumberOfRows() throws SQWRLException;

  /**
   * Reset the result so that iteration can begin again
   *
   * @throws SQWRLException If an error occurs during resetting
   */
  void reset() throws SQWRLException;

  /**
   * Advance to the next row
   *
   * @return True if we are not at the end of the result
   * @throws SQWRLException If an error occurs
   */
  boolean next() throws SQWRLException;

  /**
   * @param columnName A column name
   * @return A class result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLClassResultValue getClass(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A class result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLClassResultValue getClass(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return A named individual result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLNamedIndividualResultValue getNamedIndividual(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A named individual result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLNamedIndividualResultValue getNamedIndividual(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return An object property result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLObjectPropertyResultValue getObjectProperty(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return An object property result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLObjectPropertyResultValue getObjectProperty(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return A data property result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLDataPropertyResultValue getDataProperty(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A data property result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLDataPropertyResultValue getDataProperty(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return An annotation property result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationProperty(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return An annotation property result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLAnnotationPropertyResultValue getAnnotationProperty(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return A literal result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLLiteralResultValue getLiteral(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A literal result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLLiteralResultValue getLiteral(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is a class
   * @throws SQWRLException If an error occurs
   */
  boolean hasClassValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is a class
   * @throws SQWRLException If an error occurs
   */
  boolean hasClassValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is an individual
   * @throws SQWRLException If an error occurs
   */
  boolean hasNamedIndividualValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is an individual
   * @throws SQWRLException If an error occurs
   */
  boolean hasNamedIndividualValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is an object property
   * @throws SQWRLException If an error occurs
   */
  boolean hasObjectPropertyValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is an object property
   * @throws SQWRLException If an error occurs
   */
  boolean hasObjectPropertyValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name A column name
   * @return True if the value of the specified column is a data property
   * @throws SQWRLException If an error occurs
   */
  boolean hasDataPropertyValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is a data property
   * @throws SQWRLException If an error occurs
   */
  boolean hasDataPropertyValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return True if the value of the specified column is an annotation property
   * @throws SQWRLException If an error occurs
   */
  boolean hasAnnotationPropertyValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is an annotation property
   * @throws SQWRLException If an error occurs
   */
  boolean hasAnnotationPropertyValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return True if the value of the specified column is a literal
   * @throws SQWRLException If an error occurs
   */
  boolean hasLiteralValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return True if the value of the specified column is a literal
   * @throws SQWRLException If an error occurs
   */
  boolean hasLiteralValue(int columnIndex) throws SQWRLException;

  /**
   * @return A list of SQWRL result values
   * @throws SQWRLException If an error occurs
   */
  @NonNull List<@NonNull SQWRLResultValue> getRow() throws SQWRLException;

  /**
   * @param columnName A column name
   * @return The SQWRL result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLResultValue getValue(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return The SQWRL result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLResultValue getValue(int columnIndex) throws SQWRLException;

  /**
   * @param columnIndex The 0-based column index
   * @param rowIndex    The 0-based row index
   * @return The SQWRL result value
   * @throws SQWRLException If an error occurs
   */
  @NonNull SQWRLResultValue getValue(int columnIndex, int rowIndex) throws SQWRLException;

  /**
   * @param columnName A column name
   * @return A list of SQWRL result values
   * @throws SQWRLException If an error occurs
   */
  @NonNull List<@NonNull SQWRLResultValue> getColumn(@NonNull String columnName) throws SQWRLException;

  /**
   * @param columnIndex A column index
   * @return A list of SQWRL result values
   * @throws SQWRLException If an error occurs
   */
  @NonNull List<@NonNull SQWRLResultValue> getColumn(int columnIndex) throws SQWRLException;
}
