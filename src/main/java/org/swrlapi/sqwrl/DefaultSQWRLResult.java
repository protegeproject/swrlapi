package org.swrlapi.sqwrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.swrlapi.exceptions.SWRLAPIInternalException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidAggregateFunctionNameException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidColumnIndexException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidColumnNameException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidColumnTypeException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidRowIndexException;
import org.swrlapi.sqwrl.exceptions.SQWRLResultStateException;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

/**
 * This class implements the interfaces {@link org.swrlapi.sqwrl.SQWRLResult} and
 * {@link org.swrlapi.sqwrl.SQWRLResultGenerator}. It can be used to generate a result structure and populate it with
 * data; it can also be used to retrieve those data from the result.
 * <p>
 * This class operates in three phases:
 * <p>
 * (1) Configuration Phase: In this phase the structure of the result is defined. A newly created object will start in
 * this phase. In this phase the columns are defined; aggregation or ordering is also specified in this phase. This
 * phase is closed by a call to the {@link #configured()} method.
 * <p>
 * (2) Preparation Phase: In this phase data are added to the result. This phase is implicitly opened by the call to the
 * {@link #configured()} method. It is closed by a call to the {@link #prepared()} method.
 * <p>
 * A convenience method {@link #addColumns} that takes a list of column names is also supplied.
 * <p>
 * There is also a convenience method {@link #addRow}, which takes a list of {@link SQWRLResultValue} objects. This
 * method automatically does a row open and close. It is expecting the exact same number of list elements as there are
 * columns in the result.
 * <p>
 * The interface {@link org.swrlapi.sqwrl.SQWRLResultGenerator} defines the calls used in these two phases.
 * <p>
 * (3) Processing Phase: In this phase data may be retrieved from the result. This phase is implicitly opened by the
 * call to the {@link #prepared()} method.
 * <p>
 * The interface {@link org.swrlapi.sqwrl.SQWRLResult} defines the calls used in the processing phase.
 * <p>
 * An example configuration, data generation, and result retrieval is:
 *
 * <pre>
 * IRIResolver iriResolver = swrlapiOWLOntology.getIRIResolver();
 * SQWRLResultValueFactory valueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(iriResolver);
 * DefaultSQWRLResult result = new SWRLAPIFactory.createSQWRLResult(valueFactory);
 * 
 * result.addColumn(&quot;name&quot;);
 * result.addAggregateColumn(&quot;average&quot;, SQWRLResultNames.AvgAggregateFunction);
 * result.configured();
 * 
 * result.openRow();
 * result.addRowData(valueFactory.getIndividualValue(&quot;Fred&quot;));
 * result.addRowData(valueFactory.getValue(27));
 * result.closeRow();
 * result.openRow();
 * result.addRowData(valueFactory.getIndividualValue(&quot;Joe&quot;));
 * result.addRowData(valueFactory.getLiteralValue(34));
 * result.closeRow();
 * result.openRow();
 * result.addRowData(valueFactory.getIndividualValue(&quot;Joe&quot;));
 * result.addRowData(valueFactory.getLiteralValue(21));
 * result.closeRow();
 * result.prepared();
 * </pre>
 * <p>
 * The result is now available for reading. The interface {@link org.swrlapi.sqwrl.SQWRLResult} defines the assessor
 * methods. A row consists of a list of objects defined by the interface
 * {@link org.swrlapi.sqwrl.values.SQWRLResultValue}.
 * <p>
 * The possible types of values are (1) {@link org.swrlapi.sqwrl.values.SQWRLLiteralResultValue}, representing literals;
 * (2) {@link org.swrlapi.sqwrl.values.SQWRLIndividualResultValue}, representing OWL individuals; (3)
 * {@link org.swrlapi.sqwrl.values.SQWRLClassResultValue}, representing OWL classes; (4)
 * {@link org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue}, representing OWL object properties, (5)
 * {@link org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue}, representing OWL data properties, and (6)
 * {@link org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue}, representing OWL annotation properties.
 *
 * <pre>
 * while (result.next()) {
 * 	SQWRLIndividualResultValue nameValue = result.getIndividual(&quot;name&quot;);
 * 	SQWRLLiteralResultValue averageValue = result.getLiteral(&quot;average&quot;);
 * 	System.out.println(&quot;Name: &quot; + nameValue.getPrefixedName());
 * 	System.out.println(&quot;Average: &quot; + averageValue.getInt());
 * }
 * </pre>
 */
public class DefaultSQWRLResult implements SQWRLResult, SQWRLResultGenerator, Serializable
{
	private static final long serialVersionUID = 1L;

	private final SQWRLResultValueFactory sqwrlResultValueFactory;

	private final List<String> allColumnNames, columnDisplayNames;
	private final List<Integer> selectedColumnIndexes, orderByColumnIndexes;
	private final Map<Integer, String> aggregateColumnIndexes; // Map of (index, function) pairs
	private List<List<SQWRLResultValue>> rows; // List of List of SQWRLResultValue objects.
	private List<SQWRLResultValue> rowData; // List of SQWRLResultValue objects used when assembling a row.
	private Map<String, List<SQWRLResultValue>> columnValuesMap; // Column name -> List<SQWRLResultValue>

	private int numberOfColumns, currentRowIndex, currentRowDataColumnIndex;
	private boolean isConfigured, isPrepared, isRowOpen, isOrdered, isAscending, isDistinct, hasAggregates;
	private int limit = -1, nth = -1, firstN = -1, lastN = -1, sliceSize = -1;
	private boolean notNthSelection = false, firstSelection = false, lastSelection = false, notFirstSelection = false;
	private boolean notLastSelection = false, nthSliceSelection = false, notNthSliceSelection = false;
	private boolean nthLastSliceSelection = false, notNthLastSliceSelection = false;

	public DefaultSQWRLResult(SQWRLResultValueFactory sqwrlResultValueFactory)
	{
		this.sqwrlResultValueFactory = sqwrlResultValueFactory;

		this.isConfigured = false;
		this.isPrepared = false;
		this.isRowOpen = false;

		// The following variables will not be externally valid until configured() is called.
		this.allColumnNames = new ArrayList<>();
		this.aggregateColumnIndexes = new HashMap<>();
		this.selectedColumnIndexes = new ArrayList<>();
		this.orderByColumnIndexes = new ArrayList<>();
		this.columnDisplayNames = new ArrayList<>();

		this.numberOfColumns = 0;
		this.isOrdered = this.isAscending = this.isDistinct = false;

		// The following variables will not be externally valid until prepared() is called.
		this.currentRowIndex = -1; // If there are no rows in the final result, it will remain at -1.
		this.rows = new ArrayList<>();
	}

	// Configuration phase methods

	@Override
	public boolean isConfigured()
	{
		return this.isConfigured;
	}

	@Override
	public boolean isRowOpen()
	{
		return this.isRowOpen;
	}

	@Override
	public boolean isPrepared()
	{
		return this.isPrepared;
	}

	@Override
	public boolean isOrdered()
	{
		return this.isOrdered;
	}

	@Override
	public boolean isAscending()
	{
		return this.isAscending;
	}

	@Override
	public void addColumns(List<String> columnNames) throws SQWRLException
	{
		for (String columnName : columnNames)
			addColumn(columnName);
	}

	@Override
	public void addColumn(String columnName) throws SQWRLException
	{
		throwExceptionIfAlreadyConfigured();

		this.selectedColumnIndexes.add(this.numberOfColumns);
		this.allColumnNames.add(columnName);
		this.numberOfColumns++;
	}

	@Override
	public void addAggregateColumn(String columnName, String aggregateFunctionName) throws SQWRLException
	{
		throwExceptionIfAlreadyConfigured();

		SQWRLResultNames.checkAggregateFunctionName(aggregateFunctionName);

		this.aggregateColumnIndexes.put(this.numberOfColumns, aggregateFunctionName);
		this.allColumnNames.add(columnName);
		this.numberOfColumns++;
	}

	@Override
	public void addOrderByColumn(int orderedColumnIndex, boolean ascending) throws SQWRLException
	{
		throwExceptionIfAlreadyConfigured();

		if (orderedColumnIndex < 0 || orderedColumnIndex >= this.allColumnNames.size())
			throw new SQWRLException("ordered column index " + orderedColumnIndex + " out of range");

		if (this.isOrdered && (this.isAscending != ascending)) {
			if (this.isAscending)
				throw new SQWRLException("attempt to order column " + this.allColumnNames.get(orderedColumnIndex)
						+ " ascending when descending was previously specified");
			else
				throw new SQWRLException("attempt to order column " + this.allColumnNames.get(orderedColumnIndex)
						+ " descending when ascending was previously specified");
		}

		this.isOrdered = true;
		this.isAscending = ascending;

		this.orderByColumnIndexes.add(orderedColumnIndex);
	}

	@Override
	public void addColumnDisplayName(String columnName) throws SQWRLException
	{
		if (columnName.length() == 0 || columnName.indexOf(',') != -1)
			throw new SQWRLException("invalid column name " + columnName + " - no commas or empty names allowed");

		this.columnDisplayNames.add(columnName);
	}

	@Override
	public void configured() throws SQWRLException
	{
		throwExceptionIfAlreadyConfigured();

		// We will already have checked that all ordered columns are selected or aggregated

		if (containsOneOf(this.selectedColumnIndexes, this.aggregateColumnIndexes.keySet()))
			throw new SQWRLInvalidQueryException("aggregate columns cannot also be selected columns");

		this.hasAggregates = !this.aggregateColumnIndexes.isEmpty();

		this.isConfigured = true;
	}

	// Methods used to retrieve the result structure after the result has been configured

	@Override
	public void setIsDistinct()
	{
		this.isDistinct = true;
	}

	@Override
	public int getNumberOfColumns() throws SQWRLException
	{
		throwExceptionIfNotConfigured();

		return this.numberOfColumns;
	}

	@Override
	public int getCurrentNumberOfColumns() throws SQWRLException
	{
		throwExceptionIfConfigured();

		return this.numberOfColumns;
	}

	@Override
	public List<String> getColumnNames() throws SQWRLException
	{
		List<String> result = new ArrayList<>();

		throwExceptionIfNotConfigured();

		if (this.columnDisplayNames.size() < getNumberOfColumns()) {
			result.addAll(this.columnDisplayNames);
			result.addAll(this.allColumnNames.subList(this.columnDisplayNames.size(), this.allColumnNames.size()));
		} else
			result.addAll(this.columnDisplayNames);

		return Collections.unmodifiableList(result);
	}

	@Override
	public String getColumnName(int columnIndex) throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		checkColumnIndex(columnIndex);

		if (columnIndex < this.columnDisplayNames.size())
			return this.columnDisplayNames.get(columnIndex);
		else
			return this.allColumnNames.get(columnIndex);
	}

	// Methods used to add data after result has been configured

	@Override
	public void addRow(List<SQWRLResultValue> row) throws SQWRLException
	{
		if (row.size() != getNumberOfColumns())
			throw new SQWRLException("addRow expecting " + getNumberOfColumns() + ", got " + row.size() + " values");

		openRow();
		for (SQWRLResultValue value : row)
			addRowData(value);
		closeRow();
	}

	@Override
	public void openRow() throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfAlreadyPrepared();
		throwExceptionIfRowOpen();

		this.currentRowDataColumnIndex = 0;
		this.rowData = new ArrayList<>();
		this.isRowOpen = true;
	}

	@Override
	public void addRowData(SQWRLResultValue value) throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfAlreadyPrepared();
		throwExceptionIfRowNotOpen();

		if (this.currentRowDataColumnIndex == getNumberOfColumns())
			throw new SQWRLResultStateException("attempt to add data beyond the end of a row");

		if (this.aggregateColumnIndexes.containsKey(this.currentRowDataColumnIndex)
				&& (!this.aggregateColumnIndexes.get(this.currentRowDataColumnIndex).equals(
						SQWRLResultNames.CountAggregateFunction))
				&& (!this.aggregateColumnIndexes.get(this.currentRowDataColumnIndex).equals(
						SQWRLResultNames.CountDistinctAggregateFunction)) && (!isNumericValue(value)))
			throw new SQWRLException("attempt to add non numeric value " + value
					+ " to min, max, sum, or avg aggregate column " + this.allColumnNames.get(this.currentRowDataColumnIndex));
		this.rowData.add(value);
		this.currentRowDataColumnIndex++;

		if (this.currentRowDataColumnIndex == getNumberOfColumns())
			closeRow(); // Automatically close the row
	}

	@Override
	public void closeRow() throws SQWRLException
	{ // Will ignore if row is already closed, assuming it was automatically closed in addRowData
		throwExceptionIfNotConfigured();
		throwExceptionIfAlreadyPrepared();

		if (this.isRowOpen)
			this.rows.add(this.rowData);

		this.isRowOpen = false;
	}

	@Override
	public void prepared() throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfAlreadyPrepared();

		if (this.currentRowDataColumnIndex != 0)
			throwExceptionIfRowOpen(); // We allow prepared() with an open row if no data have been added.

		this.isPrepared = true;
		this.isRowOpen = false;
		this.currentRowDataColumnIndex = 0;
		if (getNumberOfRows() > 0)
			this.currentRowIndex = -1;
		else
			this.currentRowIndex = -1;

		if (this.hasAggregates)
			this.rows = aggregate(this.rows); // Aggregation implies killing duplicate rows
		else if (this.isDistinct)
			this.rows = distinct(this.rows);

		if (this.isOrdered && this.rows.size() > 0)
			this.rows = orderBy(this.rows, this.isAscending);

		this.rows = processSelectionOperators(this.rows);

		prepareColumnVectors();
	}

	// Methods used to retrieve data after result has been prepared

	@Override
	public int getNumberOfRows() throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();

		return this.rows.size();
	}

	@Override
	public boolean isEmpty() throws SQWRLException
	{
		return getNumberOfRows() == 0;
	}

	@Override
	public void reset() throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();

		if (getNumberOfRows() > 0)
			this.currentRowIndex = -1;
	}

	@Override
	public boolean next() throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();

		this.currentRowIndex++;

		if (this.currentRowIndex != -1 && this.currentRowIndex < getNumberOfRows()) {
			return true;
		} else
			return false;
	}

	@Override
	public List<SQWRLResultValue> getRow() throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();
		throwExceptionIfAtEndOfResult();

		return this.rows.get(this.currentRowIndex);
	}

	@Override
	public SQWRLResultValue getValue(String columnName) throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();
		throwExceptionIfAtEndOfResult();

		int columnIndex = getColumnIndex(columnName);

		List<SQWRLResultValue> row = this.rows.get(this.currentRowIndex);

		return row.get(columnIndex);
	}

	@Override
	public SQWRLResultValue getValue(int columnIndex) throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();
		throwExceptionIfAtEndOfResult();

		checkColumnIndex(columnIndex);

		List<SQWRLResultValue> row = this.rows.get(this.currentRowIndex);

		return row.get(columnIndex);
	}

	@Override
	public SQWRLResultValue getValue(int columnIndex, int rowIndex) throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();

		checkColumnIndex(columnIndex);
		checkRowIndex(rowIndex);

		return this.rows.get(rowIndex).get(columnIndex);
	}

	@Override
	public SQWRLIndividualResultValue getIndividual(String columnName) throws SQWRLException
	{
		if (!hasIndividualValue(columnName))
			throw new SQWRLInvalidColumnTypeException("expecting ObjectValue type for column " + columnName);
		return (SQWRLIndividualResultValue)getValue(columnName);
	}

	@Override
	public SQWRLIndividualResultValue getIndividual(int columnIndex) throws SQWRLException
	{
		return getIndividual(getColumnName(columnIndex));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(String columnName) throws SQWRLException
	{
		if (!hasLiteralValue(columnName))
			throw new SQWRLInvalidColumnTypeException("expecting LiteralValue type for column " + columnName);
		return (SQWRLLiteralResultValue)getValue(columnName);
	}

	@Override
	public SQWRLClassResultValue getClass(String columnName) throws SQWRLException
	{
		if (!hasClassValue(columnName))
			throw new SQWRLInvalidColumnTypeException("expecting ClassValue type for column " + columnName);
		return (SQWRLClassResultValue)getValue(columnName);
	}

	@Override
	public SQWRLClassResultValue getClass(int columnIndex) throws SQWRLException
	{
		return getClass(getColumnName(columnIndex));
	}

	@Override
	public SQWRLObjectPropertyResultValue getObjectProperty(int columnIndex) throws SQWRLException
	{
		return getObjectProperty(getColumnName(columnIndex));
	}

	@Override
	public SQWRLObjectPropertyResultValue getObjectProperty(String columnName) throws SQWRLException
	{
		if (!hasObjectPropertyValue(columnName))
			throw new SQWRLInvalidColumnTypeException("expecting OWL object property in column " + columnName);
		return (SQWRLObjectPropertyResultValue)getValue(columnName);
	}

	@Override
	public SQWRLDataPropertyResultValue getDataProperty(int columnIndex) throws SQWRLException
	{
		return getDataProperty(getColumnName(columnIndex));
	}

	@Override
	public SQWRLDataPropertyResultValue getDataProperty(String columnName) throws SQWRLException
	{
		if (!hasDataPropertyValue(columnName))
			throw new SQWRLInvalidColumnTypeException("expecting OWL data property in column " + columnName);
		return (SQWRLDataPropertyResultValue)getValue(columnName);
	}

	@Override
	public SQWRLAnnotationPropertyResultValue getAnnotationProperty(int columnIndex) throws SQWRLException
	{
		return getAnnotationProperty(getColumnName(columnIndex));
	}

	@Override
	public SQWRLAnnotationPropertyResultValue getAnnotationProperty(String columnName) throws SQWRLException
	{
		if (!hasAnnotationPropertyValue(columnName))
			throw new SQWRLInvalidColumnTypeException("expecting OWL data property in column " + columnName);
		return (SQWRLAnnotationPropertyResultValue)getValue(columnName);
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(int columnIndex) throws SQWRLException
	{
		return getLiteral(getColumnName(columnIndex));
	}

	@Override
	public List<SQWRLResultValue> getColumn(String columnName) throws SQWRLException
	{
		throwExceptionIfNotConfigured();
		throwExceptionIfNotPrepared();

		checkColumnName(columnName);

		return this.columnValuesMap.get(columnName);
	}

	@Override
	public List<SQWRLResultValue> getColumn(int columnIndex) throws SQWRLException
	{
		return getColumn(getColumnName(columnIndex));
	}

	@Override
	public boolean hasIndividualValue(String columnName) throws SQWRLException
	{
		return getValue(columnName) instanceof SQWRLIndividualResultValue;
	}

	@Override
	public boolean hasIndividualValue(int columnIndex) throws SQWRLException
	{
		return getValue(columnIndex) instanceof SQWRLIndividualResultValue;
	}

	@Override
	public boolean hasLiteralValue(String columnName) throws SQWRLException
	{
		return getValue(columnName) instanceof SQWRLLiteralResultValue;
	}

	@Override
	public boolean hasLiteralValue(int columnIndex) throws SQWRLException
	{
		return getValue(columnIndex) instanceof SQWRLLiteralResultValue;
	}

	@Override
	public boolean hasClassValue(String columnName) throws SQWRLException
	{
		return getValue(columnName) instanceof SQWRLClassResultValue;
	}

	@Override
	public boolean hasClassValue(int columnIndex) throws SQWRLException
	{
		return getValue(columnIndex) instanceof SQWRLClassResultValue;
	}

	@Override
	public boolean hasObjectPropertyValue(String columnName) throws SQWRLException
	{
		return getValue(columnName) instanceof SQWRLObjectPropertyResultValue;
	}

	@Override
	public boolean hasObjectPropertyValue(int columnIndex) throws SQWRLException
	{
		return getValue(columnIndex) instanceof SQWRLObjectPropertyResultValue;
	}

	@Override
	public boolean hasDataPropertyValue(String columnName) throws SQWRLException
	{
		return getValue(columnName) instanceof SQWRLDataPropertyResultValue;
	}

	@Override
	public boolean hasDataPropertyValue(int columnIndex) throws SQWRLException
	{
		return getValue(columnIndex) instanceof SQWRLDataPropertyResultValue;
	}

	@Override
	public boolean hasAnnotationPropertyValue(String columnName) throws SQWRLException
	{
		return getValue(columnName) instanceof SQWRLAnnotationPropertyResultValue;
	}

	@Override
	public boolean hasAnnotationPropertyValue(int columnIndex) throws SQWRLException
	{
		return getValue(columnIndex) instanceof SQWRLPropertyResultValue;
	}

	private int getColumnIndex(String columnName) throws SQWRLException
	{
		checkColumnName(columnName);

		if (this.allColumnNames.contains(columnName))
			return this.allColumnNames.indexOf(columnName);
		else
			return this.columnDisplayNames.indexOf(columnName);
	}

	// nth, firstN, etc. are 1-indexed
	private List<List<SQWRLResultValue>> processSelectionOperators(List<List<SQWRLResultValue>> sourceRows)
			throws SQWRLException
	{
		List<List<SQWRLResultValue>> processedRows = new ArrayList<>();
		boolean hasSelection = false;

		if (hasLimit()) {
			int localLimit = this.limit > sourceRows.size() ? sourceRows.size() : this.limit;
			if (this.limit < 0)
				this.limit = 0;
			processedRows.addAll(sourceRows.subList(0, localLimit));
			hasSelection = true;
		} else {
			if (hasNth()) {
				if (this.nth < 1)
					this.nth = 1;
				if (this.nth <= sourceRows.size())
					processedRows.add(sourceRows.get(this.nth - 1));
				hasSelection = true;
			}

			if (hasNotNth()) {
				if (this.nth < 1)
					this.nth = 1;
				if (this.nth <= sourceRows.size()) {
					List<List<SQWRLResultValue>> localRows = new ArrayList<>(sourceRows);
					localRows.remove(this.nth - 1);
					processedRows.addAll(localRows);
				} else
					processedRows.addAll(sourceRows); // Add everything
				hasSelection = true;
			}

			if (hasFirstSelection()) {
				if (this.firstN < 1)
					this.firstN = 1;
				if (this.firstN <= sourceRows.size())
					processedRows.addAll(sourceRows.subList(0, this.firstN));
				hasSelection = true;
			}

			if (hasNotFirstSelection()) {
				if (this.firstN < 1)
					this.firstN = 1;
				if (this.firstN <= sourceRows.size())
					processedRows.addAll(sourceRows.subList(this.firstN, sourceRows.size()));
				else
					processedRows.addAll(sourceRows); // Add everything
				hasSelection = true;
			}

			if (hasLastSelection()) {
				if (this.lastN < 1)
					this.lastN = 1;
				if (this.lastN <= sourceRows.size())
					processedRows.addAll(sourceRows.subList(sourceRows.size() - this.lastN, sourceRows.size()));
				hasSelection = true;
			}

			if (hasNotLastSelection()) {
				if (this.lastN < 1)
					this.lastN = 1;
				if (this.lastN <= sourceRows.size())
					processedRows.addAll(sourceRows.subList(0, sourceRows.size() - this.lastN));
				else
					processedRows.addAll(sourceRows); // Add everything
				hasSelection = true;
			}

			if (hasNthSliceSelection()) {
				if (this.firstN < 1)
					this.firstN = 1;
				if (this.firstN <= sourceRows.size()) {
					int finish = (this.firstN + this.sliceSize > sourceRows.size()) ? sourceRows.size() : this.firstN
							+ this.sliceSize - 1;
					processedRows.addAll(sourceRows.subList(this.firstN - 1, finish));
				}
				hasSelection = true;
			}

			if (hasNotNthSliceSelection()) {
				if (this.firstN < 1)
					this.firstN = 1;
				if (this.firstN <= sourceRows.size()) {
					int finish = (this.firstN + this.sliceSize > sourceRows.size()) ? sourceRows.size() : this.firstN
							+ this.sliceSize - 1;
					processedRows.addAll(sourceRows.subList(0, this.firstN - 1));
					if (finish <= sourceRows.size())
						processedRows.addAll(sourceRows.subList(finish, sourceRows.size()));
				} else
					processedRows.addAll(sourceRows); // Add everything
				hasSelection = true;
			}

			if (hasNthLastSliceSelection()) {
				if (this.lastN < 1)
					this.lastN = 1;
				int finish = (this.lastN + this.sliceSize > sourceRows.size()) ? sourceRows.size() : this.lastN
						+ this.sliceSize;
				if (this.lastN <= sourceRows.size()) {
					processedRows.addAll(sourceRows.subList(this.lastN, finish));
				}
				hasSelection = true;
			}

			if (hasNotNthLastSliceSelection()) {
				if (this.lastN <= sourceRows.size()) {
					if (this.lastN < 1)
						this.lastN = 1;
					int finish = (this.lastN + this.sliceSize > sourceRows.size()) ? sourceRows.size() : this.lastN
							+ this.sliceSize;
					processedRows.addAll(sourceRows.subList(0, this.lastN));
					if (finish <= sourceRows.size())
						processedRows.addAll(sourceRows.subList(finish, sourceRows.size()));
				} else
					processedRows.addAll(sourceRows); // Add everything
				hasSelection = true;
			}
		}

		if (hasSelection)
			return processedRows;
		else
			return sourceRows;
	}

	// nth, firstN, etc. are 1-indexed
	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public void setNth(int nth)
	{
		this.nth = nth;
	}

	public void setNotNth(int nth)
	{
		this.notNthSelection = true;
		this.nth = nth;
	}

	public void setFirst()
	{
		this.firstSelection = true;
		this.firstN = 1;
	}

	public void setFirst(int n)
	{
		this.firstSelection = true;
		this.firstN = n;
	}

	public void setLast()
	{
		this.lastSelection = true;
		this.lastN = 1;
	}

	public void setLast(int n)
	{
		this.lastSelection = true;
		this.lastN = n;
	}

	public void setNotFirst()
	{
		this.notFirstSelection = true;
		this.firstN = 1;
	}

	public void setNotFirst(int n)
	{
		this.notFirstSelection = true;
		this.firstN = n;
	}

	public void setNotLast()
	{
		this.notLastSelection = true;
		this.lastN = 1;
	}

	public void setNotLast(int n)
	{
		this.notLastSelection = true;
		this.lastN = n;
	}

	public void setNthSlice(int n, int sliceSize)
	{
		this.nthSliceSelection = true;
		this.firstN = n;
		this.sliceSize = sliceSize;
	}

	public void setNotNthSlice(int n, int sliceSize)
	{
		this.notNthSliceSelection = true;
		this.firstN = n;
		this.sliceSize = sliceSize;
	}

	public void setNthLastSlice(int n, int sliceSize)
	{
		this.nthLastSliceSelection = true;
		this.lastN = n;
		this.sliceSize = sliceSize;
	}

	public void setNotNthLastSlice(int n, int sliceSize)
	{
		this.notNthLastSliceSelection = true;
		this.lastN = n;
		this.sliceSize = sliceSize;
	}

	private boolean hasLimit()
	{
		return this.limit != -1;
	}

	private boolean hasNth()
	{
		return !hasNotNth() && this.nth != -1;
	}

	private boolean hasNotNth()
	{
		return this.notNthSelection;
	}

	private boolean hasFirstSelection()
	{
		return this.firstSelection;
	}

	private boolean hasLastSelection()
	{
		return this.lastSelection;
	}

	private boolean hasNotFirstSelection()
	{
		return this.notFirstSelection;
	}

	private boolean hasNotLastSelection()
	{
		return this.notLastSelection;
	}

	private boolean hasNthSliceSelection()
	{
		return this.nthSliceSelection;
	}

	private boolean hasNotNthSliceSelection()
	{
		return this.notNthSliceSelection;
	}

	private boolean hasNthLastSliceSelection()
	{
		return this.nthLastSliceSelection;
	}

	private boolean hasNotNthLastSliceSelection()
	{
		return this.notNthLastSliceSelection;
	}

	private void prepareColumnVectors() throws SQWRLException
	{
		this.columnValuesMap = new HashMap<>();

		if (getNumberOfColumns() > 0) {
			List<List<SQWRLResultValue>> columns = new ArrayList<>(getNumberOfColumns());

			for (int c = 0; c < getNumberOfColumns(); c++)
				columns.add(new ArrayList<SQWRLResultValue>(getNumberOfRows()));

			for (int r = 0; r < getNumberOfRows(); r++)
				for (int c = 0; c < getNumberOfColumns(); c++)
					columns.get(c).add(this.rows.get(r).get(c));

			for (int c = 0; c < getNumberOfColumns(); c++)
				this.columnValuesMap.put(getColumnName(c), columns.get(c));
		}
	}

	@Override
	public String toString()
	{
		String result = "[numberOfColumns: " + this.numberOfColumns + ", isConfigured: " + this.isConfigured
				+ ", isPrepared: " + this.isPrepared + ", isRowOpen: " + this.isRowOpen + ", isOrdered: " + this.isOrdered
				+ ", isAscending " + this.isAscending + ", isDistinct: " + this.isDistinct + ", hasAggregates: "
				+ this.hasAggregates + "]\n";

		result += "[columnDisplayNames: ";
		for (String columnDisplayName : this.columnDisplayNames)
			result += "" + columnDisplayName + "";
		result += "]\n";

		for (List<SQWRLResultValue> row : this.rows) {
			for (SQWRLResultValue value : row) {
				result += "" + value + " ";
			}
			result += "\n";
		}
		return result;
	}

	// Phase verification exception throwing methods

	private void throwExceptionIfConfigured() throws SQWRLException
	{
		if (isConfigured())
			throw new SQWRLResultStateException("attempt to get pre-configuration data after configuration");
	}

	private void throwExceptionIfNotConfigured() throws SQWRLException
	{
		if (!isConfigured())
			throw new SQWRLResultStateException("attempt to add data to unconfigured result");
	}

	private void throwExceptionIfAtEndOfResult() throws SQWRLException
	{
		if (this.currentRowIndex >= getNumberOfRows())
			throw new SQWRLResultStateException("attempt to get data after end of result reached");
	}

	private void throwExceptionIfNotPrepared() throws SQWRLException
	{
		if (!isPrepared())
			throw new SQWRLResultStateException("attempt to process unprepared result");
	}

	private void throwExceptionIfAlreadyConfigured() throws SQWRLException
	{
		if (isConfigured())
			throw new SQWRLResultStateException("attempt to configure already configured result");
	}

	private void throwExceptionIfAlreadyPrepared() throws SQWRLException
	{
		if (isPrepared())
			throw new SQWRLResultStateException("attempt to modify prepared result");
	}

	private void checkColumnName(String columnName) throws SQWRLInvalidColumnNameException
	{
		if (!this.allColumnNames.contains(columnName) && !this.columnDisplayNames.contains(columnName))
			throw new SQWRLInvalidColumnNameException("invalid column name " + columnName);
	}

	private void throwExceptionIfRowNotOpen() throws SQWRLException
	{
		if (!this.isRowOpen)
			throw new SQWRLResultStateException("attempt to add data to an unopened row");
	} // throwExceptionIfRowNotOpen

	private void throwExceptionIfRowOpen() throws SQWRLException
	{
		if (this.isRowOpen)
			throw new SQWRLResultStateException("attempt to process result with a partially prepared row");
	}

	private void checkColumnIndex(int columnIndex) throws SQWRLException
	{
		if (columnIndex < 0 || columnIndex >= getNumberOfColumns())
			throw new SQWRLInvalidColumnIndexException("column index " + columnIndex + " out of bounds");
	}

	private void checkRowIndex(int rowIndex) throws SQWRLException
	{
		if (rowIndex < 0 || rowIndex >= getNumberOfRows())
			throw new SQWRLInvalidRowIndexException("row index " + rowIndex + " out of bounds");
	}

	private boolean containsOneOf(List<Integer> collection1, Set<Integer> collection2)
	{
		for (Integer i : collection2)
			if (collection1.contains(i))
				return true;

		return false;
	}

	private boolean isNumericValue(SQWRLResultValue value)
	{
		return ((value instanceof SQWRLLiteralResultValue) && (((SQWRLLiteralResultValue)value).isNumeric()));
	}

	// TODO: fix - very inefficient
	private List<List<SQWRLResultValue>> distinct(List<List<SQWRLResultValue>> sourceRows) throws SQWRLException
	{
		List<List<SQWRLResultValue>> localRows = new ArrayList<>(sourceRows);
		List<List<SQWRLResultValue>> processedRows = new ArrayList<>();
		SQWRLResultRowComparator rowComparator = new SQWRLResultRowComparator(this.allColumnNames, true); // Look at the
		// entire row.

		try {
			Collections.sort(localRows, rowComparator); // Binary search is expecting a sorted list
			for (List<SQWRLResultValue> row : localRows)
				if (Collections.binarySearch(processedRows, row, rowComparator) < 0)
					processedRows.add(row);
		} catch (RuntimeException e) {
			throw new SQWRLException("Internal error comparing rows", e);
		}

		return processedRows;
	}

	private List<List<SQWRLResultValue>> aggregate(List<List<SQWRLResultValue>> sourceRows) throws SQWRLException
	{
		List<List<SQWRLResultValue>> result = new ArrayList<>();
		SQWRLResultRowComparator rowComparator = new SQWRLResultRowComparator(this.allColumnNames,
				this.selectedColumnIndexes, true);
		// Key is index of aggregated row in result, value is hash map of aggregate column index to list of original values.
		HashMap<Integer, HashMap<Integer, List<SQWRLResultValue>>> aggregatesMap = new HashMap<>();
		// Map of column indexes to value lists; used to accumulate values for aggregation.
		HashMap<Integer, List<SQWRLResultValue>> aggregateRowMap;
		List<SQWRLResultValue> values;
		SQWRLResultValue value;
		int rowIndex;

		for (List<SQWRLResultValue> row : sourceRows) {
			rowIndex = findRowIndex(result, row, rowComparator); // Find row with same values for non aggregated columns

			if (rowIndex < 0) { // Row with same values for non aggregated columns not yet present in result.
				aggregateRowMap = new HashMap<>();
				// Find value for each aggregated column in row and add each to map indexed by result row
				for (Integer aggregateColumnIndex : this.aggregateColumnIndexes.keySet()) {
					values = new ArrayList<>();
					value = row.get(aggregateColumnIndex);
					values.add(value);
					aggregateRowMap.put(aggregateColumnIndex, values);
				}
				aggregatesMap.put(result.size(), aggregateRowMap); //
				result.add(row);
			} else { // We found a row that has the same values for the non aggregated columns.
				aggregateRowMap = aggregatesMap.get(Integer.valueOf(rowIndex)); // Find the aggregate map
				for (Integer aggregateColumnIndex : this.aggregateColumnIndexes.keySet()) {
					value = row.get(aggregateColumnIndex); // Find value
					values = aggregateRowMap.get(aggregateColumnIndex); // Find row map
					values.add(value); // Add value
				}
			}
		}

		rowIndex = 0;
		for (List<SQWRLResultValue> row : result) {
			aggregateRowMap = aggregatesMap.get(Integer.valueOf(rowIndex));

			for (Integer aggregateColumnIndex : this.aggregateColumnIndexes.keySet()) {
				String aggregateFunctionName = this.aggregateColumnIndexes.get(aggregateColumnIndex);
				List<SQWRLResultValue> columnValues = aggregateRowMap.get(aggregateColumnIndex);

				// We have checked in addRowData that only numeric data are added for sum, max, min, and avg
				if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.MinAggregateFunction)) {
					List<SQWRLLiteralResultValue> literalColumnValues = convert2LiteralResultValues(columnValues,
							aggregateColumnIndex);
					value = min(literalColumnValues, aggregateColumnIndex);
				} else if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.MaxAggregateFunction)) {
					List<SQWRLLiteralResultValue> literalColumnValues = convert2LiteralResultValues(columnValues,
							aggregateColumnIndex);
					value = max(literalColumnValues, aggregateColumnIndex);
				} else if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.SumAggregateFunction)) {
					List<SQWRLLiteralResultValue> literalColumnValues = convert2LiteralResultValues(columnValues,
							aggregateColumnIndex);
					value = sum(literalColumnValues, aggregateColumnIndex);
				} else if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.AvgAggregateFunction)) {
					List<SQWRLLiteralResultValue> literalColumnValues = convert2LiteralResultValues(columnValues,
							aggregateColumnIndex);
					value = avg(literalColumnValues, aggregateColumnIndex);
				} else if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.MedianAggregateFunction)) {
					List<SQWRLLiteralResultValue> literalColumnValues = convert2LiteralResultValues(columnValues,
							aggregateColumnIndex);
					value = median(literalColumnValues, aggregateColumnIndex);
				} else if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.CountAggregateFunction))
					value = count(columnValues);
				else if (aggregateFunctionName.equalsIgnoreCase(SQWRLResultNames.CountDistinctAggregateFunction))
					value = countDistinct(columnValues);
				else
					throw new SQWRLInvalidAggregateFunctionNameException("invalid aggregate function " + aggregateFunctionName);

				row.set(aggregateColumnIndex, value);
			}
			rowIndex++;
		}
		return result;
	}

	private List<List<SQWRLResultValue>> orderBy(List<List<SQWRLResultValue>> sourceRows, boolean ascending)
			throws SQWRLException
	{
		List<List<SQWRLResultValue>> result = new ArrayList<>(sourceRows);
		SQWRLResultRowComparator rowComparator = new SQWRLResultRowComparator(this.allColumnNames,
				this.orderByColumnIndexes, ascending);

		try {
			Collections.sort(result, rowComparator);
		} catch (RuntimeException e) {
			throw new SQWRLException("Internal error comparing rows", e);
		}

		return result;
	}

	private SQWRLLiteralResultValue min(List<SQWRLLiteralResultValue> columnValues, int columnIndex)
			throws SQWRLException
	{
		SQWRLLiteralResultValue result = null;

		if (columnValues.isEmpty())
			throw new SQWRLException("empty aggregate list for " + SQWRLResultNames.MinAggregateFunction);

		int rowIndex = 0;
		for (SQWRLLiteralResultValue value : columnValues) {

			if (!isNumericValue(value))
				throw new SQWRLException("attempt to use " + SQWRLResultNames.MinAggregateFunction
						+ " aggregate on column with non numeric literal " + value + " with type " + value.getOWLDatatype()
						+ "in (0-based) row " + rowIndex + ", column " + columnIndex);

			if (result == null)
				result = value;
			else if (value.compareTo(result) < 0)
				result = value;
			rowIndex++;
		}

		return result;
	}

	private SQWRLLiteralResultValue max(List<SQWRLLiteralResultValue> columnValues, int columnIndex)
			throws SQWRLException
	{
		SQWRLLiteralResultValue result = null;

		if (columnValues.isEmpty())
			throw new SQWRLException("empty aggregate list for " + SQWRLResultNames.MaxAggregateFunction);

		int rowIndex = 0;
		for (SQWRLLiteralResultValue value : columnValues) {

			if (!isNumericValue(value))
				throw new SQWRLException("attempt to use " + SQWRLResultNames.MaxAggregateFunction
						+ " aggregate with non numeric literal " + value + " with type " + value.getOWLDatatype()
						+ "in (0-based) row " + rowIndex + ", column " + columnIndex);

			if (result == null)
				result = value;
			else if (value.compareTo(result) > 0)
				result = value;
			rowIndex++;
		}

		return result;
	}

	private SQWRLLiteralResultValue sum(List<SQWRLLiteralResultValue> columnValues, int columnIndex)
			throws SQWRLException
	{
		double sum = 0;

		if (columnValues.isEmpty())
			throw new SQWRLException("empty aggregate list for " + SQWRLResultNames.SumAggregateFunction);

		int rowIndex = 0;
		for (SQWRLLiteralResultValue value : columnValues) {

			if (!isNumericValue(value))
				throw new SQWRLException("attempt to use " + SQWRLResultNames.SumAggregateFunction
						+ " aggregate  on non numeric value: " + value + " with type " + value.getOWLDatatype()
						+ " in (0-based) row " + rowIndex + ", column " + columnIndex);

			double d = value.getDouble();

			sum = sum + d;
			rowIndex++;
		}
		return getSQWRLResultValueFactory().createLeastNarrowNumericLiteralValue(sum, columnValues);
	}

	private SQWRLLiteralResultValue avg(List<SQWRLLiteralResultValue> columnValues, int columnIndex)
			throws SQWRLException
	{
		double sum = 0;
		int count = 0;

		if (columnValues.isEmpty())
			throw new SQWRLException("empty aggregate list for function " + SQWRLResultNames.AvgAggregateFunction);

		int rowIndex = 0;
		for (SQWRLLiteralResultValue value : columnValues) {

			if (!isNumericValue(value))
				throw new SQWRLException("attempt to use " + SQWRLResultNames.AvgAggregateFunction
						+ " aggregate on column with non literal value " + value + " with type " + value.getOWLDatatype()
						+ " in (0-based) row " + rowIndex + ", column " + columnIndex);

			double d = value.getDouble();

			count++;
			sum = sum + d;
			rowIndex++;
		}
		double avgValue = sum / count;

		return getSQWRLResultValueFactory().createLeastNarrowNumericLiteralValue(avgValue, columnValues);
	}

	private SQWRLLiteralResultValue median(List<SQWRLLiteralResultValue> columnValues, int columnIndex)
			throws SQWRLException
	{
		double[] valueArray = new double[columnValues.size()];
		int count = 0, middle = columnValues.size() / 2;
		double medianValue;

		int rowIndex = 0;
		for (SQWRLLiteralResultValue value : columnValues) {
			if (!isNumericValue(value))
				throw new SQWRLException("attempt to use " + SQWRLResultNames.MedianAggregateFunction
						+ " aggregate on column with non literal value " + value + " with type " + value.getOWLDatatype()
						+ " in (0-based) row " + rowIndex + ", column " + columnIndex);

			double d = value.getDouble();
			valueArray[count++] = d;
		}

		Arrays.sort(valueArray);

		if (columnValues.size() % 2 == 1)
			medianValue = valueArray[middle];
		else
			medianValue = (valueArray[middle - 1] + valueArray[middle]) / 2;

		return getSQWRLResultValueFactory().createLeastNarrowNumericLiteralValue(medianValue, columnValues);
	}

	private SQWRLLiteralResultValue count(List<SQWRLResultValue> columnValues) throws SQWRLException
	{
		return getSQWRLResultValueFactory().getLiteralValue(columnValues.size());
	}

	private SQWRLLiteralResultValue countDistinct(List<SQWRLResultValue> columnValues) throws SQWRLException
	{
		Set<SQWRLResultValue> distinctValues = new HashSet<>(columnValues);

		return getSQWRLResultValueFactory().getLiteralValue(distinctValues.size());
	}

	// TODO: linear search is not very efficient.
	private int findRowIndex(List<List<SQWRLResultValue>> result, List<SQWRLResultValue> rowToFind,
			Comparator<List<SQWRLResultValue>> rowComparator) throws SQWRLException
	{
		int rowIndex = 0;

		try {
			for (List<SQWRLResultValue> row : result) {
				if (rowComparator.compare(rowToFind, row) == 0)
					return rowIndex;
				rowIndex++;
			}
		} catch (RuntimeException e) {
			throw new SQWRLException("Internal error comparing rows", e);
		}
		return -1;
	}

	private static class SQWRLResultRowComparator implements Comparator<List<SQWRLResultValue>>
	{
		private final List<Integer> orderByColumnIndexes;
		private final boolean ascending;

		public SQWRLResultRowComparator(List<String> allColumnNames, List<Integer> orderByColumnIndexes, boolean ascending)
		{
			this.ascending = ascending;
			this.orderByColumnIndexes = orderByColumnIndexes;
		}

		public SQWRLResultRowComparator(List<String> allColumnNames, boolean ascending)
		{
			this.ascending = ascending;
			this.orderByColumnIndexes = new ArrayList<>();

			for (String columnName : allColumnNames)
				this.orderByColumnIndexes.add(allColumnNames.indexOf(columnName));
		}

		@Override
		public int compare(List<SQWRLResultValue> row1, List<SQWRLResultValue> row2)
		{
			for (Integer columnIndex : this.orderByColumnIndexes) {
				SQWRLResultValue value1 = row1.get(columnIndex);
				SQWRLResultValue value2 = row2.get(columnIndex);
				int diff;

				try {
					if (value1.isLiteral() && value2.isLiteral())
						diff = value1.asLiteralResult().compareTo(value2.asLiteralResult());
					else if (value1.isEntity() && value2.isEntity())
						diff = value1.asEntityResult().compareTo(value2.asEntityResult());
					else
						throw new SWRLAPIInternalException("attempt to compare a " + value1.getClass().getName() + " with a "
								+ value2.getClass().getName());
				} catch (SQWRLException e) {
					throw new SWRLAPIInternalException("internal error comparing " + value1.getClass().getName() + " with a "
							+ value2.getClass().getName() + ": " + e.getMessage());
				}
				if (diff != 0) {
					if (this.ascending)
						return diff;
					else
						return -diff;
				}
			}
			return 0;
		}
	}

	private List<SQWRLLiteralResultValue> convert2LiteralResultValues(List<SQWRLResultValue> columnValues, int columnIndex)
			throws SQWRLException
	{
		List<SQWRLLiteralResultValue> literalValues = new ArrayList<>();

		int rowIndex = 0;
		for (SQWRLResultValue value : columnValues) {
			if (value.isLiteral())
				literalValues.add(value.asLiteralResult());
			else
				throw new SQWRLException("Found non literal value " + value + " in (0-based) row " + rowIndex + ", column "
						+ columnIndex + " - expecting literal");
			rowIndex++;
		}
		return literalValues;
	}

	private SQWRLResultValueFactory getSQWRLResultValueFactory()
	{
		return this.sqwrlResultValueFactory;
	}
}
