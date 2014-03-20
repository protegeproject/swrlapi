package org.swrlapi.sqwrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.swrlapi.ext.SWRLAPILiteral;
import org.swrlapi.ext.SWRLAPILiteralFactory;
import org.swrlapi.ext.impl.DefaultSWRLAPILiteral;
import org.swrlapi.ext.impl.DefaultSWRLAPILiteralFactory;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

public class DefaultSQWRLQuery implements SQWRLQuery
{
	private final String queryName;
	private final List<SWRLAtom> bodyAtoms;
	private final List<SWRLAtom> headAtoms;
	private final DefaultSQWRLResult sqwrlResult;
	private final Map<String, List<SWRLBuiltInArgument>> collectionGroupArgumentsMap; // Map of collection name to group
																																										// arguments; applies only to
																																										// grouped collections
	private final SWRLAPILiteralFactory swrlAPILiteralFactory;

	private boolean active; // Like a SWRLRule, a SQWRL query can also be inactive.

	public DefaultSQWRLQuery(String queryName, List<SWRLAtom> bodyAtoms, List<SWRLAtom> headAtoms,
			SQWRLResultValueFactory sqwrlResultValueFactory) throws SQWRLException
	{
		this.queryName = queryName;
		this.bodyAtoms = bodyAtoms;
		this.headAtoms = headAtoms;
		this.sqwrlResult = new DefaultSQWRLResult(sqwrlResultValueFactory);
		this.collectionGroupArgumentsMap = new HashMap<String, List<SWRLBuiltInArgument>>();
		this.swrlAPILiteralFactory = new DefaultSWRLAPILiteralFactory();
		this.active = false;

		processSQWRLBuiltIns();
		generateBuiltInAtomVariableDependencies();
	}

	@Override
	public String getName()
	{
		return this.queryName;
	}

	@Override
	public List<SWRLAtom> getHeadAtoms()
	{
		return Collections.unmodifiableList(this.headAtoms);
	}

	@Override
	public List<SWRLAtom> getBodyAtoms()
	{
		return Collections.unmodifiableList(this.bodyAtoms);
	}

	@Override
	public SQWRLResult getResult() throws SQWRLException
	{
		if (!this.sqwrlResult.isPrepared())
			this.sqwrlResult.prepared();

		return this.sqwrlResult;
	}

	@Override
	public SQWRLResultGenerator getResultGenerator()
	{
		return this.sqwrlResult;
	}

	@Override
	public boolean hasCollections()
	{
		return !getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames()).isEmpty();
	}

	@Override
	public List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames)
	{
		return getBuiltInAtoms(getBodyAtoms(), builtInNames);
	}

	@Override
	public void setActive(boolean isActive)
	{
		this.active = isActive;
	}

	@Override
	public boolean isActive()
	{
		return this.active;
	}

	@Override
	public List<SWRLAtom> getSQWRLPhase1BodyAtoms()
	{
		List<SWRLAtom> result = new ArrayList<SWRLAtom>();

		for (SWRLAtom atom : getBodyAtoms()) {
			if (atom instanceof SWRLAPIBuiltInAtom) {
				SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
				if (builtInAtom.usesSQWRLCollectionResults() || isSQWRLGroupCollection(builtInAtom))
					continue;
			}
			result.add(atom);
		}

		return result;
	}

	@Override
	public List<SWRLAtom> getSQWRLPhase2BodyAtoms()
	{
		List<SWRLAtom> result = new ArrayList<SWRLAtom>();

		for (SWRLAtom atom : getBodyAtoms()) {
			if (atom instanceof SWRLAPIBuiltInAtom) {
				SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
				if (isSQWRLMakeCollection(builtInAtom) || isSQWRLGroupCollection(builtInAtom))
					continue;
			}
			result.add(atom);
		}

		return result;
	}

	@Override
	public String toString()
	{
		return this.queryName + ": " + getQueryText();
	}

	@Override
	public String getQueryText()
	{
		String result = "";
		boolean isFirst = true;

		for (SWRLAtom atom : getBodyAtoms()) {
			if (!isFirst)
				result += " ^ ";
			result += "" + atom;
			isFirst = false;
		}

		result += " -> ";

		isFirst = true;
		for (SWRLAtom atom : getHeadAtoms()) {
			if (!isFirst)
				result += " ^ ";
			result += "" + atom;
			isFirst = false;
		}

		return result;
	}

	private boolean isSQWRLMakeCollection(SWRLAPIBuiltInAtom builtInAtom)
	{
		return SQWRLNames.isSQWRLCollectionMakeBuiltIn(builtInAtom.getBuiltInPrefixedName());
	}

	private boolean isSQWRLGroupCollection(SWRLAPIBuiltInAtom builtInAtom)
	{
		return SQWRLNames.isSQWRLCollectionGroupByBuiltIn(builtInAtom.getBuiltInPrefixedName());
	}

	private List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead()
	{
		return getBuiltInAtoms(getHeadAtoms());
	}

	private List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<String> builtInNames)
	{
		return getBuiltInAtoms(getHeadAtoms(), builtInNames);
	}

	private List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody()
	{
		return getBuiltInAtoms(getBodyAtoms());
	}

	private List<SWRLAPIBuiltInAtom> getBuiltInAtoms(List<SWRLAtom> atoms)
	{
		List<SWRLAPIBuiltInAtom> result = new ArrayList<SWRLAPIBuiltInAtom>();

		for (SWRLAtom atom : atoms)
			if (atom instanceof SWRLAPIBuiltInAtom)
				result.add((SWRLAPIBuiltInAtom)atom);

		return result;
	}

	private List<SWRLAPIBuiltInAtom> getBuiltInAtoms(List<SWRLAtom> atoms, Set<String> builtInNames)
	{
		List<SWRLAPIBuiltInAtom> result = new ArrayList<SWRLAPIBuiltInAtom>();

		for (SWRLAtom atom : atoms) {
			if (atom instanceof SWRLAPIBuiltInAtom) {
				SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
				if (builtInNames.contains(builtInAtom.getBuiltInPrefixedName()))
					result.add(builtInAtom);
			}
		}
		return result;
	}

	private void processSQWRLBuiltIns() throws SQWRLException
	{
		Set<String> collectionNames = new HashSet<String>();
		Set<String> cascadedUnboundVariableNames = new HashSet<String>();

		processSQWRLHeadBuiltIns();
		processSQWRLCollectionMakeBuiltIns(collectionNames); // Find all make collection built-ins
		processSQWRLCollectionGroupByBuiltIns(collectionNames); // Find the group arguments for each collection
		processSQWRLCollectionMakeGroupArguments(collectionNames); // Add the group arguments to the make built-ins for its
																																// collection
		processSQWRLCollectionOperationBuiltIns(collectionNames, cascadedUnboundVariableNames);
		processBuiltInsThatUseSQWRLCollectionOperationResults(cascadedUnboundVariableNames);

		this.sqwrlResult.configured();
		this.sqwrlResult.openRow();

		if (hasCollections())
			this.sqwrlResult.setIsDistinct();
	}

	// TODO: too long- refactor
	private void processSQWRLHeadBuiltIns() throws SQWRLException
	{
		// A variable can be selected multiple times. We record its positions in case of an orderBy clause.
		Map<String, List<Integer>> selectedVariable2ColumnIndices = new HashMap<String, List<Integer>>();

		processBuiltInIndexes();

		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromHead(SQWRLNames.getHeadBuiltInNames())) {
			String builtInName = builtInAtom.getBuiltInPrefixedName();

			int columnIndex = 0;
			for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
				boolean isArgumentAVariable = argument.isVariable();
				String variableName = null, columnName;

				if (SQWRLNames.isSQWRLHeadSelectionBuiltIn(builtInName)
						|| SQWRLNames.isSQWRLHeadAggregationBuiltIn(builtInName)) {
					if (isArgumentAVariable) {
						variableName = argument.getVariableName();
						if (selectedVariable2ColumnIndices.containsKey(variableName))
							selectedVariable2ColumnIndices.get(variableName).add(columnIndex);
						else {
							selectedVariable2ColumnIndices.put(variableName, new ArrayList<Integer>());
							selectedVariable2ColumnIndices.get(variableName).add(columnIndex);
						}
					}
					if (builtInName.equalsIgnoreCase(SQWRLNames.Select)) {
						if (isArgumentAVariable)
							columnName = "?" + variableName;
						else
							columnName = "[" + argument + "]";
						this.sqwrlResult.addColumn(columnName);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.SelectDistinct)) {
						if (isArgumentAVariable)
							columnName = "?" + variableName;
						else
							columnName = "[" + argument + "]";
						this.sqwrlResult.addColumn(columnName);
						this.sqwrlResult.setIsDistinct();
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.Count)) {
						if (isArgumentAVariable)
							columnName = "count(?" + variableName + ")";
						else
							columnName = "[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.CountAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.CountDistinct)) {
						if (isArgumentAVariable)
							columnName = "countDistinct(?" + variableName + ")";
						else
							columnName = "[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.CountDistinctAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.Min)) {
						if (isArgumentAVariable)
							columnName = "min(?" + variableName + ")";
						else
							columnName = "min[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.MinAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.Max)) {
						if (isArgumentAVariable)
							columnName = "max(?" + variableName + ")";
						else
							columnName = "max[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.MaxAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.Sum)) {
						if (isArgumentAVariable)
							columnName = "sum(?" + variableName + ")";
						else
							columnName = "sum[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.SumAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.Median)) {
						if (isArgumentAVariable)
							columnName = "median(?" + variableName + ")";
						else
							columnName = "median[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.MedianAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.Avg)) {
						if (isArgumentAVariable)
							columnName = "avg(?" + variableName + ")";
						else
							columnName = "avg[" + argument + "]";
						this.sqwrlResult.addAggregateColumn(columnName, SQWRLNames.AvgAggregateFunction);
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.OrderBy)) {
						if (!isArgumentAVariable)
							throw new SQWRLException("only variables allowed for ordered columns - found " + argument);
						if (selectedVariable2ColumnIndices.containsKey(variableName)) {
							for (int selectedColumnIndex : selectedVariable2ColumnIndices.get(variableName))
								this.sqwrlResult.addOrderByColumn(selectedColumnIndex, true);
						} else
							throw new SQWRLException("variable ?" + variableName + " must be selected before it can be ordered");
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.OrderByDescending)) {
						if (!isArgumentAVariable)
							throw new SQWRLException("only variables allowed for ordered columns - found " + argument);
						if (selectedVariable2ColumnIndices.containsKey(variableName)) {
							for (int selectedColumnIndex : selectedVariable2ColumnIndices.get(variableName))
								this.sqwrlResult.addOrderByColumn(selectedColumnIndex, false);
						} else
							throw new SQWRLException("variable ?" + variableName + " must be selected before it can be ordered");
					} else if (builtInName.equalsIgnoreCase(SQWRLNames.ColumnNames)) {
						if (argument instanceof SWRLLiteralBuiltInArgument) {
							SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
							SWRLAPILiteral literal = getSWRLAPILiteralFactory().getSWRLAPILiteral(literalArgument.getLiteral());
							if (literal.isString())
								this.sqwrlResult.addColumnDisplayName(literal.getString());
							else
								throw new SQWRLException("only string literals allowed as column names - found " + argument);
						} else
							throw new SQWRLException("only string literals allowed as column names - found " + argument);
					}
				}
				columnIndex++;
			}

			if (SQWRLNames.isSQWRLHeadSlicingBuiltIn(builtInName)) {
				if (!this.sqwrlResult.isOrdered() && !builtInName.equals(SQWRLNames.Limit))
					throw new SQWRLException("slicing operator used without an order clause");

				if (builtInName.equalsIgnoreCase(SQWRLNames.Least) || builtInName.equalsIgnoreCase(SQWRLNames.First)) {
					if (!builtInAtom.getArguments().isEmpty())
						throw new SQWRLException("first or least do not accept arguments");
					this.sqwrlResult.setFirst();
				} else if (builtInName.equalsIgnoreCase(SQWRLNames.NotLeast)
						|| builtInName.equalsIgnoreCase(SQWRLNames.NotFirst)) {
					if (!builtInAtom.getArguments().isEmpty())
						throw new SQWRLException("not first or least do not accept arguments");
					this.sqwrlResult.setNotFirst();
				} else if (builtInName.equalsIgnoreCase(SQWRLNames.Greatest) || builtInName.equalsIgnoreCase(SQWRLNames.Last)) {
					if (!builtInAtom.getArguments().isEmpty())
						throw new SQWRLException("greatest or last do not accept arguments");
					this.sqwrlResult.setLast();
				} else if (builtInName.equalsIgnoreCase(SQWRLNames.NotGreatest)
						|| builtInName.equalsIgnoreCase(SQWRLNames.NotLast)) {
					if (!builtInAtom.getArguments().isEmpty())
						throw new SQWRLException("not greatest or last do not accept arguments");
					this.sqwrlResult.setNotLast();
				} else {
					SWRLBuiltInArgument nArgument = builtInAtom.getBuiltInArguments().get(0);
					int n;

					if (nArgument instanceof SWRLLiteralBuiltInArgument) {
						SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)nArgument;
						SWRLAPILiteral literal = new DefaultSWRLAPILiteral(literalArgument.getLiteral()); // TODO Use factory
						if (literal.isInteger()) {
							n = literal.getInteger();
							if (n < 1)
								throw new SQWRLException("nth argument for slicing operator " + builtInName
										+ " must be a positive integer");
						} else
							throw new SQWRLException("expecting integer for slicing operator " + builtInName);
					} else
						throw new SQWRLException("expecting integer for slicing operator " + builtInName);

					if (builtInAtom.getArguments().size() == 1) {
						if (builtInName.equalsIgnoreCase(SQWRLNames.Limit))
							this.sqwrlResult.setLimit(n);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.Nth))
							this.sqwrlResult.setNth(n);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.NotNth))
							this.sqwrlResult.setNotNth(n);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.FirstN) || builtInName.equalsIgnoreCase(SQWRLNames.LeastN))
							this.sqwrlResult.setFirst(n);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.LastN)
								|| builtInName.equalsIgnoreCase(SQWRLNames.GreatestN))
							this.sqwrlResult.setLast(n);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.NotLastN)
								|| builtInName.equalsIgnoreCase(SQWRLNames.NotGreatestN))
							this.sqwrlResult.setNotLast(n);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.NotFirstN)
								|| builtInName.equalsIgnoreCase(SQWRLNames.NotLeastN))
							this.sqwrlResult.setNotFirst(n);
						else
							throw new SQWRLException("unknown slicing operator " + builtInName);
					} else if (builtInAtom.getArguments().size() == 2) {
						SWRLBuiltInArgument sliceArgument = builtInAtom.getBuiltInArguments().get(1);
						int sliceSize;

						if (sliceArgument instanceof SWRLLiteralBuiltInArgument) {
							SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)sliceArgument;
							SWRLAPILiteral literal = new DefaultSWRLAPILiteral(literalArgument.getLiteral()); // TODO Use factory
							if (literal.isInteger()) {
								sliceSize = literal.getInteger();
								if (sliceSize < 1)
									throw new SQWRLException("slice size argument to slicing operator " + builtInName
											+ " must be a positive integer");
							} else
								throw new SQWRLException("expecing integer to slicing operator " + builtInName);
						} else
							throw new SQWRLException("expecing integer to slicing operator " + builtInName);

						if (builtInName.equalsIgnoreCase(SQWRLNames.NthSlice))
							this.sqwrlResult.setNthSlice(n, sliceSize);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.NotNthSlice))
							this.sqwrlResult.setNotNthSlice(n, sliceSize);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.NthLastSlice)
								|| builtInName.equalsIgnoreCase(SQWRLNames.NthGreatestSlice))
							this.sqwrlResult.setNthLastSlice(n, sliceSize);
						else if (builtInName.equalsIgnoreCase(SQWRLNames.NotNthLastSlice)
								|| builtInName.equalsIgnoreCase(SQWRLNames.NotNthGreatestSlice))
							this.sqwrlResult.setNotNthLastSlice(n, sliceSize);
						else
							throw new SQWRLException("unknown slicing operator " + builtInName);
					} else
						throw new SQWRLException("unknown slicing operator " + builtInName);
				}
			}
		}
	}

	// Process all make collection built-ins.
	private void processSQWRLCollectionMakeBuiltIns(Set<String> collectionNames)
	{
		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
			String collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name

			if (!collectionNames.contains(collectionName))
				collectionNames.add(collectionName);
		}
	}

	// We store the group arguments for each collection specified in the make operation; these arguments are later
	// appended to the collection
	// operation built-ins.
	private void processSQWRLCollectionGroupByBuiltIns(Set<String> collectionNames) throws SQWRLException
	{
		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionGroupByBuiltInNames())) {
			String collectionName = builtInAtom.getArgumentVariableName(0); // The first argument is the collection name.
			List<SWRLBuiltInArgument> builtInArguments = builtInAtom.getBuiltInArguments();
			List<SWRLBuiltInArgument> groupArguments = builtInArguments.subList(1, builtInArguments.size());

			if (builtInAtom.getArguments().size() < 2)
				throw new SQWRLException("groupBy must have at least two arguments");
			if (!collectionNames.contains(collectionName))
				throw new SQWRLException("groupBy applied to undefined collection ?" + collectionName);
			if (this.collectionGroupArgumentsMap.containsKey(collectionName))
				throw new SQWRLException("groupBy specified more than once for same collection ?" + collectionName);
			if (hasUnboundArgument(groupArguments))
				throw new SQWRLException("unbound group argument passed to groupBy for collection ?" + collectionName);

			this.collectionGroupArgumentsMap.put(collectionName, groupArguments); // Store group arguments.
		}
	}

	private void processSQWRLCollectionMakeGroupArguments(Set<String> collectionNames) throws SQWRLException
	{
		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
			String collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name

			if (!collectionNames.contains(collectionName))
				throw new SQWRLException("groupBy applied to undefined collection ?" + collectionName);

			if (this.collectionGroupArgumentsMap.containsKey(collectionName))
				builtInAtom.addArguments(this.collectionGroupArgumentsMap.get(collectionName)); // Append each collections's
																																												// group arguments to make
																																												// built-in.
		}
	}

	private void processSQWRLCollectionOperationBuiltIns(Set<String> collectionNames,
			Set<String> cascadedUnboundVariableNames)
	{
		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody(SQWRLNames.getCollectionOperationBuiltInNames())) {
			List<SWRLBuiltInArgument> allOperandCollectionGroupArguments = new ArrayList<SWRLBuiltInArgument>(); // The group
																																																						// arguments
																																																						// from the
																																																						// operand
																																																						// collections
			List<String> variableNames;

			builtInAtom.setUsesSQWRLCollectionResults();

			if (builtInAtom.hasUnboundArguments()) { // Keep track of built-in's unbound arguments so that we can mark
																								// dependent built-ins.
				Set<String> unboundVariableNames = builtInAtom.getUnboundArgumentVariableNames();
				cascadedUnboundVariableNames.addAll(unboundVariableNames);
			}

			// Append the group arguments to built-ins for each of its collection arguments; also append group arguments
			// to collections created by operation built-ins.
			if (isSQWRLCollectionCreateOperation(builtInAtom))
				variableNames = builtInAtom.getArgumentsVariableNamesExceptFirst();
			else
				variableNames = builtInAtom.getArgumentsVariableNames();

			for (String variableName : variableNames) {
				if (collectionNames.contains(variableName) && this.collectionGroupArgumentsMap.containsKey(variableName)) { // The
																																																										// variable
																																																										// refers
																																																										// to
																																																										// a
																																																										// grouped
																																																										// collection.
					builtInAtom.addArguments(this.collectionGroupArgumentsMap.get(variableName)); // Append each collections's
																																												// group arguments to built-in.
					allOperandCollectionGroupArguments.addAll(this.collectionGroupArgumentsMap.get(variableName));
				}
			}

			if (isSQWRLCollectionCreateOperation(builtInAtom)) { // If a collection is created we need to record it and store
																														// necessary group arguments.
				String createdCollectionName = builtInAtom.getArgumentVariableName(0); // The first argument is the collection
																																								// name.

				if (!collectionNames.contains(createdCollectionName))
					collectionNames.add(createdCollectionName);

				if (!allOperandCollectionGroupArguments.isEmpty())
					this.collectionGroupArgumentsMap.put(createdCollectionName, allOperandCollectionGroupArguments); // Store
																																																						// group
																																																						// arguments
																																																						// from all
																																																						// operand
																																																						// collections.
			}
		}
	}

	private void processBuiltInsThatUseSQWRLCollectionOperationResults(Set<String> cascadedUnboundVariableNames)
	{
		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody()) {
			if (!isSQWRLBuiltIn(builtInAtom)) {
				// Mark later non SQWRL built-ins that (directly or indirectly) use variables bound by collection operation
				// built-ins.
				if (builtInAtom.usesAtLeastOneVariableOf(cascadedUnboundVariableNames)) {
					builtInAtom.setUsesSQWRLCollectionResults(); // Mark this built-in as dependent on collection built-in
																												// bindings.
					if (builtInAtom.hasUnboundArguments()) // Cascade the dependency from this built-in to others using its
																									// arguments.
						cascadedUnboundVariableNames.addAll(builtInAtom.getUnboundArgumentVariableNames()); // Record its unbound
																																																// variables too.
				}
			}
		}
	}

	/**
	 * Give each built-in a unique index proceeding from left to right.
	 */
	private void processBuiltInIndexes()
	{
		int builtInIndex = 0;

		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromBody())
			builtInAtom.setBuiltInIndex(builtInIndex++);
		for (SWRLAPIBuiltInAtom builtInAtom : getBuiltInAtomsFromHead())
			builtInAtom.setBuiltInIndex(builtInIndex++);
	}

	private boolean isSQWRLBuiltIn(SWRLAPIBuiltInAtom builtInAtom)
	{
		return SQWRLNames.isSQWRLBuiltIn(builtInAtom.getBuiltInPrefixedName());
	}

	private boolean hasUnboundArgument(List<SWRLBuiltInArgument> arguments)
	{
		for (SWRLBuiltInArgument argument : arguments)
			if (argument.isUnbound())
				return true;
		return false;
	}

	private boolean isSQWRLCollectionOperation(SWRLAPIBuiltInAtom builtInAtom)
	{
		return SQWRLNames.isSQWRLCollectionOperationBuiltIn(builtInAtom.getBuiltInPrefixedName());
	}

	private boolean isSQWRLCollectionCreateOperation(SWRLAPIBuiltInAtom builtInAtom)
	{
		return SQWRLNames.isSQWRLCollectionCreateOperationBuiltIn(builtInAtom.getBuiltInPrefixedName());
	}

	/**
	 * For every built-in atom, record the variables it depends from preceding atoms (directly and indirectly).
	 */
	private void generateBuiltInAtomVariableDependencies()
	{
		Map<String, Set<Set<String>>> pathMap = new HashMap<String, Set<Set<String>>>();
		Set<String> rootVariableNames = new HashSet<String>();

		for (SWRLAtom atom : getBodyAtoms()) {
			Set<String> thisAtomReferencedVariableNames = getReferencedVariableNames(atom);

			buildPaths(atom, rootVariableNames, pathMap);

			if (atom instanceof SWRLAPIBuiltInAtom) {
				SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;

				if (isSQWRLGroupCollection(builtInAtom))
					continue; // We ignore sqwrl:groupBy variables because they are really directives
				if (isSQWRLCollectionOperation(builtInAtom))
					break; // Once we encounter a SQWRL operation we stop because dependencies don't matter for these atoms

				if (hasReferencedVariables(builtInAtom)) {
					Set<String> pathVariableNames = new HashSet<String>();

					for (String rootVariableName : pathMap.keySet()) {
						for (Set<String> path : pathMap.get(rootVariableName)) {
							if (!Collections.disjoint(path, thisAtomReferencedVariableNames)) {
								pathVariableNames.addAll(path);
								pathVariableNames.add(rootVariableName);
							}
						}
					}

					if (!pathVariableNames.isEmpty()) {
						pathVariableNames.removeAll(thisAtomReferencedVariableNames); // Remove our own variables
						/*
						 * TODO: Need to think about correct operation of this if (builtInAtom.isSQWRLMakeCollection()) { String
						 * collectionName = builtInAtom.getArgumentVariableName(0); // First argument is the collection name if
						 * (collectionGroupArgumentsMap.containsKey(collectionName)) { List<BuiltInArgument> groupArguments =
						 * collectionGroupArgumentsMap.get(collectionName); Set<String> groupVariableNames =
						 * getVariableNames(groupArguments); if (!groupVariableNames.isEmpty() &&
						 * !pathVariableNames.containsAll(groupVariableNames)) throw new
						 * BuiltInException("all group arguments must be on path for corresponding collection make"); } // if }
						 */
						builtInAtom.setPathVariableNames(pathVariableNames);
					}
				}
			}
		}
	}

	/**
	 * Incrementally build variable dependency paths up to and including the current atom.
	 * <p>
	 * Note: Sets of sets in Java require care because of hash code issues. The enclosed set should not be modified or the
	 * outer set may return inconsistent results.
	 */
	private void buildPaths(SWRLAtom atom, Set<String> rootVariableNames, Map<String, Set<Set<String>>> pathMap)
	{
		Set<String> currentAtomReferencedVariableNames = getReferencedVariableNames(atom);
		Set<String> matchingRootVariableNames;

		if (currentAtomReferencedVariableNames.size() == 1) { // Make variable a root if we have not yet encountered it
			String variableName = currentAtomReferencedVariableNames.iterator().next();
			if (getMatchingPaths(pathMap, variableName).isEmpty() && !rootVariableNames.contains(variableName)) {
				Set<Set<String>> paths = new HashSet<Set<String>>();
				pathMap.put(variableName, paths);
				rootVariableNames.add(variableName);
			}
		} else if (currentAtomReferencedVariableNames.size() > 1) {
			Set<String> currentKnownAtomRootVariableNames = new HashSet<String>(currentAtomReferencedVariableNames);
			currentKnownAtomRootVariableNames.retainAll(rootVariableNames);

			if (!currentKnownAtomRootVariableNames.isEmpty()) { // At least one of atom's variables reference already known
																													// root(s)
				for (String rootVariableName : currentKnownAtomRootVariableNames) {
					Set<String> dependentVariables = new HashSet<String>(currentAtomReferencedVariableNames);
					dependentVariables.remove(rootVariableName);

					matchingRootVariableNames = getMatchingRootVariableNames(pathMap, dependentVariables);
					if (!matchingRootVariableNames.isEmpty()) { // Found existing path(s) that use these variables - add them to
																											// existing path(s)
						for (String matchingRootVariableName : matchingRootVariableNames) {
							Set<Set<String>> paths = pathMap.get(matchingRootVariableName);
							Set<Set<String>> matchedPaths = new HashSet<Set<String>>();
							for (Set<String> path : paths)
								if (!Collections.disjoint(path, dependentVariables))
									matchedPaths.add(path);
							for (Set<String> matchedPath : matchedPaths) {
								Set<String> newPath = new HashSet<String>(matchedPath);
								newPath.addAll(dependentVariables);
								paths.remove(matchedPath); // Remove the original matched path for this root's path
								paths.add(Collections.unmodifiableSet(newPath)); // Add the updated path
							}
						}
					} else { // Did not find an existing path for this root that uses these variables - add dependent variables as
										// new path
						Set<Set<String>> paths = pathMap.get(rootVariableName);
						paths.add(Collections.unmodifiableSet(dependentVariables));
					}
				}
			} else { // No known roots referenced by any of the atom's variables
				matchingRootVariableNames = getMatchingRootVariableNames(pathMap, currentAtomReferencedVariableNames);
				if (!matchingRootVariableNames.isEmpty()) {
					// Found existing paths that use the atom's variables - add all the variables (none of which is a root) to
					// each path
					for (String matchingRootVariableName : matchingRootVariableNames) {
						Set<Set<String>> paths = pathMap.get(matchingRootVariableName);
						Set<Set<String>> matchedPaths = new HashSet<Set<String>>();

						for (Set<String> path : paths)
							if (!Collections.disjoint(path, currentAtomReferencedVariableNames))
								matchedPaths.add(path);
						for (Set<String> matchedPath : matchedPaths) { // Add the new variables to the matched path and add it to
																														// this root's path
							Set<String> newPath = new HashSet<String>(matchedPath);
							newPath.addAll(currentAtomReferencedVariableNames); // Add all the non-root variable names to this path
							paths.remove(matchedPath); // Remove the original matched path
							paths.add(Collections.unmodifiableSet(newPath)); // Add the updated path
						}
					}
				} else { // No existing paths have variables from this atom - every variable becomes a root and depends on every
									// other root variable
					for (String rootVariableName : currentAtomReferencedVariableNames) {
						Set<Set<String>> paths = new HashSet<Set<String>>();
						Set<String> dependentVariables = new HashSet<String>(currentAtomReferencedVariableNames);
						dependentVariables.remove(rootVariableName); // Remove the root from its own dependent variables
						paths.add(Collections.unmodifiableSet(dependentVariables));
						pathMap.put(rootVariableName, paths);
						rootVariableNames.add(rootVariableName);
					}
				}
			}
		}
	}

	private Set<String> getMatchingPaths(Map<String, Set<Set<String>>> pathMap, String variableName)
	{
		return getMatchingRootVariableNames(pathMap, Collections.singleton(variableName));
	}

	@SuppressWarnings("unused")
	// Used by commented-out group argument checking in processBuiltInArgumentDependencies
	private Set<String> getVariableNames(List<SWRLBuiltInArgument> arguments)
	{
		Set<String> variableNames = new HashSet<String>();

		for (SWRLBuiltInArgument argument : arguments)
			if (argument.isVariable())
				variableNames.add(argument.getVariableName());

		return variableNames;
	}

	private Set<String> getMatchingRootVariableNames(Map<String, Set<Set<String>>> pathMap, Set<String> variableNames)
	{
		Set<String> matchingRootVariableNames = new HashSet<String>();

		for (String rootVariableName : pathMap.keySet()) {
			Set<Set<String>> pathsWithSameRoot = pathMap.get(rootVariableName);
			for (Set<String> path : pathsWithSameRoot)
				if (!Collections.disjoint(path, variableNames))
					matchingRootVariableNames.add(rootVariableName);
		}

		return matchingRootVariableNames;
	}

	private Set<String> getReferencedVariableNames(SWRLAtom atom)
	{
		Set<String> referencedVariableNames = new HashSet<String>();

		for (SWRLArgument argument : atom.getAllArguments()) {
			if (argument instanceof SWRLVariableAtomArgument) {
				SWRLVariableAtomArgument variableAtomArgument = (SWRLVariableAtomArgument)argument;
				referencedVariableNames.add(variableAtomArgument.getVariableName());
			} else if (argument instanceof SWRLVariableBuiltInArgument) {
				SWRLVariableBuiltInArgument variableBuiltInArgument = (SWRLVariableBuiltInArgument)argument;
				referencedVariableNames.add(variableBuiltInArgument.getVariableName());
			}
		}
		return referencedVariableNames;
	}

	private boolean hasReferencedVariables(SWRLAtom atom)
	{
		return !getReferencedVariableNames(atom).isEmpty();
	}

	private SWRLAPILiteralFactory getSWRLAPILiteralFactory()
	{
		return this.swrlAPILiteralFactory;
	}
}
