package org.protege.swrlapi.builtins.sqwrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.protege.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.InvalidBuiltInArgumentException;
import org.protege.swrlapi.sqwrl.DefaultSQWRLQuery;
import org.protege.swrlapi.sqwrl.SQWRLNames;
import org.protege.swrlapi.sqwrl.SQWRLResultGenerator;
import org.protege.swrlapi.sqwrl.values.SQWRLAnnotationPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLClassValue;
import org.protege.swrlapi.sqwrl.values.SQWRLDataPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLIndividualValue;
import org.protege.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.protege.swrlapi.sqwrl.values.SQWRLObjectPropertyValue;
import org.semanticweb.owlapi.model.SWRLArgument;

/**
 * Implementation library for SQWRL built-ins. See <a href="http://protege.cim3.net/cgi-bin/wiki.pl?SQWRL">here</a> for
 * documentation.
 * <p>
 * Unlike other built-in libraries, queries that use built-ins in this library need to be preprocessed by a SQWRL-aware
 * processor. See the {@link DefaultSQWRLQuery} class for an example of this processing.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
	/**
	 * A collection can be uniquely identified by its query name and collection name. A unique key is generated from this
	 * combination. If a collection is grouped, each group will be have a unique key generated. a {@link #collectionsMap}
	 * is a map of collection keys to a map of group keys to collections.
	 */
	private final Map<String, Map<String, Collection<SWRLBuiltInArgument>>> collectionsMap;

	/**
	 * A map of collection keys to group size (which will be 0 for ungrouped collections)
	 */
	private final Map<String, Integer> collectionGroupElementNumbersMap;

	private final Set<String> setKeys, bagKeys;

	public SWRLBuiltInLibraryImpl()
	{
		super(SQWRLNames.SQWRLBuiltInLibraryName);

		this.collectionsMap = new HashMap<String, Map<String, Collection<SWRLBuiltInArgument>>>();
		this.collectionGroupElementNumbersMap = new HashMap<String, Integer>();
		this.setKeys = new HashSet<String>();
		this.bagKeys = new HashSet<String>();
	}

	@Override
	public void reset()
	{
		this.collectionsMap.clear();
		this.collectionGroupElementNumbersMap.clear();
		this.setKeys.clear();
		this.bagKeys.clear();
	}

	public boolean select(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		checkForUnboundArguments(arguments);
		checkNumberOfArgumentsAtLeastOne(arguments);
		SQWRLResultGenerator resultGenerator = getSQWRLResultGenerator(getInvokingRuleName());

		if (!resultGenerator.isRowOpen())
			resultGenerator.openRow();

		int argumentIndex = 0;
		for (SWRLBuiltInArgument argument : arguments) {
			if (argument instanceof SWRLLiteralBuiltInArgument) {
				SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
				SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
				resultGenerator.addRowData(literal);
			} else if (argument instanceof SWRLIndividualBuiltInArgument) {
				SWRLIndividualBuiltInArgument individualArgument = (SWRLIndividualBuiltInArgument)argument;
				SQWRLIndividualValue individualValue = getSQWRLResultValueFactory().createIndividualValue(individualArgument);
				resultGenerator.addRowData(individualValue);
			} else if (argument instanceof SWRLClassBuiltInArgument) {
				SWRLClassBuiltInArgument classArgument = (SWRLClassBuiltInArgument)argument;
				SQWRLClassValue classValue = getSQWRLResultValueFactory().createClassValue(classArgument);
				resultGenerator.addRowData(classValue);
			} else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
				SWRLObjectPropertyBuiltInArgument objectPropertyArgument = (SWRLObjectPropertyBuiltInArgument)argument;
				SQWRLObjectPropertyValue objectPropertyValue = getSQWRLResultValueFactory().createObjectPropertyValue(
						objectPropertyArgument);
				resultGenerator.addRowData(objectPropertyValue);
			} else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
				SWRLDataPropertyBuiltInArgument dataPropertyArgument = (SWRLDataPropertyBuiltInArgument)argument;
				SQWRLDataPropertyValue dataPropertyValue = getSQWRLResultValueFactory().createDataPropertyValue(
						dataPropertyArgument);
				resultGenerator.addRowData(dataPropertyValue);
			} else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
				SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument = (SWRLAnnotationPropertyBuiltInArgument)argument;
				SQWRLAnnotationPropertyValue annotationPropertyValue = getSQWRLResultValueFactory()
						.createAnnotationPropertyValue(annotationPropertyArgument);
				resultGenerator.addRowData(annotationPropertyValue);
			} else if (argument instanceof SQWRLCollectionBuiltInArgument) {
				throw new InvalidBuiltInArgumentException(argumentIndex, "collections cannot be selected");
			} else
				throw new InvalidBuiltInArgumentException(argumentIndex, "unknown type "
						+ argument.getClass().getCanonicalName());
			argumentIndex++;
		}

		return false;
	}

	// Preprocessed to signal that duplicates should be removed from result
	public boolean selectDistinct(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();

		return select(arguments);
	}

	public boolean count(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		checkForUnboundArguments(arguments);
		checkNumberOfArgumentsEqualTo(1, arguments.size());

		SQWRLResultGenerator resultGenerator = getSQWRLResultGenerator(getInvokingRuleName());
		SWRLBuiltInArgument argument = arguments.get(0);

		if (!resultGenerator.isRowOpen())
			resultGenerator.openRow();

		if (argument instanceof SWRLLiteralBuiltInArgument) {
			SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
			// SQWRLLiteralResultValue literal = new SQWRLLiteralResultValueImpl(literalArgument.getLiteral());
			SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
			resultGenerator.addRowData(literal);
		} else if (argument instanceof SWRLIndividualBuiltInArgument) {
			SWRLIndividualBuiltInArgument individualArgument = (SWRLIndividualBuiltInArgument)argument;
			SQWRLIndividualValue individualValue = getSQWRLResultValueFactory().createIndividualValue(individualArgument);
			resultGenerator.addRowData(individualValue);
		} else if (argument instanceof SWRLClassBuiltInArgument) {
			SWRLClassBuiltInArgument classArgument = (SWRLClassBuiltInArgument)argument;
			SQWRLClassValue classValue = getSQWRLResultValueFactory().createClassValue(classArgument);
			resultGenerator.addRowData(classValue);
		} else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
			SWRLObjectPropertyBuiltInArgument objectPropertyArgument = (SWRLObjectPropertyBuiltInArgument)argument;
			SQWRLObjectPropertyValue objectPropertyValue = getSQWRLResultValueFactory().createObjectPropertyValue(
					objectPropertyArgument);
			resultGenerator.addRowData(objectPropertyValue);
		} else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
			SWRLDataPropertyBuiltInArgument dataPropertyArgument = (SWRLDataPropertyBuiltInArgument)argument;
			SQWRLDataPropertyValue dataPropertyValue = getSQWRLResultValueFactory().createDataPropertyValue(
					dataPropertyArgument);
			resultGenerator.addRowData(dataPropertyValue);
		} else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
			SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument = (SWRLAnnotationPropertyBuiltInArgument)argument;
			SQWRLAnnotationPropertyValue annotationPropertyValue = getSQWRLResultValueFactory()
					.createAnnotationPropertyValue(annotationPropertyArgument);
			resultGenerator.addRowData(annotationPropertyValue);
		} else if (argument instanceof SQWRLCollectionBuiltInArgument) {
			throw new InvalidBuiltInArgumentException(0, "collections cannot be counted");
		} else
			throw new InvalidBuiltInArgumentException(0, "unknown type " + argument.getClass().getCanonicalName());

		return false;
	}

	// This built-in is preprocessed by SWRLProcessor so there is nothing to do here
	public boolean countDistinct(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		return count(arguments);
	}

	// These built-in is preprocessed by SWRLProcessor so there is nothing to do here
	public boolean columnNames(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		return true;
	}

	public boolean orderBy(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		return true;
	}

	public boolean orderByDescending(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		return true;
	}

	public boolean limit(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInConsequent();
		return true;
	}

	/*********************************************************************************************************************
	 * 
	 * SQWRL collection operators
	 * 
	 *********************************************************************************************************************/

	public boolean makeSet(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();
		checkForUnboundNonFirstArguments(arguments);

		final int resultCollectionArgumentNumber = 0, elementArgumentNumber = 1;
		String queryName = getInvokingRuleName();
		String collectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String collectionGroupKey = getCollectionGroupKeyInMake(arguments); // Get unique key for collection group (if any);
																																				// does argument checking
		SWRLBuiltInArgument element = arguments.get(elementArgumentNumber);
		Collection<SWRLBuiltInArgument> set;

		if (isCollection(queryName, collectionName, collectionGroupKey))
			set = getCollection(queryName, collectionName, collectionGroupKey);
		else
			set = createSet(queryName, collectionName, collectionGroupKey);

		set.add(element);

		if (isUnboundArgument(resultCollectionArgumentNumber, arguments))
			arguments.get(resultCollectionArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, collectionName, collectionGroupKey));

		return true;
	}

	public boolean makeBag(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();
		checkForUnboundNonFirstArguments(arguments);

		final int resultCollectionArgumentNumber = 0, elementArgumentNumber = 1;
		String queryName = getInvokingRuleName();
		String collectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String collectionGroupKey = getCollectionGroupKeyInMake(arguments); // Get unique key for bag; does argument
																																				// checking
		SWRLBuiltInArgument element = arguments.get(elementArgumentNumber);
		Collection<SWRLBuiltInArgument> bag;

		if (isCollection(queryName, collectionName, collectionGroupKey))
			bag = getCollection(queryName, collectionName, collectionGroupKey);
		else
			bag = createBag(queryName, collectionName, collectionGroupKey);

		bag.add(element);

		if (isUnboundArgument(resultCollectionArgumentNumber, arguments))
			arguments.get(resultCollectionArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, collectionName, collectionGroupKey));

		return true;
	}

	// Preprocesed so nothing to do here
	public boolean groupBy(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		return true;
	}

	/*********************************************************************************************************************
	 * 
	 * SQWRL operators that work with a single collection and return a value or an element or evaluate to true or false
	 * 
	 *********************************************************************************************************************/

	public boolean isEmpty(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int sourceCollectionArgumentNumber = 0, numberOfCoreArguments = 1;
		Collection<SWRLBuiltInArgument> collection = getCollectionInSingleCollectionOperation(arguments,
				sourceCollectionArgumentNumber, numberOfCoreArguments);

		return collection.size() == 0;
	}

	public boolean notEmpty(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		return !isEmpty(arguments);
	}

	public boolean size(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreArguments = 2;
		Collection<SWRLBuiltInArgument> collection = getCollectionInSingleCollectionOperation(arguments,
				sourceCollectionArgumentNumber, numberOfCoreArguments);

		return processResultArgument(arguments, resultArgumentNumber, collection.size());
	}

	public boolean element(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreArguments = 2;

		Collection<SWRLBuiltInArgument> collection = getCollectionInSingleCollectionOperation(arguments,
				sourceCollectionArgumentNumber, numberOfCoreArguments);

		return processResultArgument(arguments, resultArgumentNumber, collection);
	}

	public boolean notElement(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		return !element(arguments);
	}

	public boolean min(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, numberOfConsequentArguments = 1;
		boolean result = false;

		if (getIsInConsequent()) { // Simple SQWRL aggregation operator
			checkForUnboundArguments(arguments);
			checkNumberOfArgumentsEqualTo(numberOfConsequentArguments, arguments.size());

			SQWRLResultGenerator resultGenerator = getSQWRLResultGenerator(getInvokingRuleName());
			SWRLBuiltInArgument argument = arguments.get(resultArgumentNumber);

			if (!resultGenerator.isRowOpen())
				resultGenerator.openRow();

			if (argument instanceof SWRLLiteralBuiltInArgument) {
				SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
				SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
				if (literal.isNumeric()) {
					resultGenerator.addRowData(literal);
				} else
					throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got " + argument);
			} else
				throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got " + argument);

			result = true;
		} else
			result = least(arguments); // Redirect to SQWRL collection operator

		return result;
	}

	public boolean max(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, numberOfConsequentArguments = 1;
		boolean result = false;

		if (getIsInConsequent()) { // Simple SQWRL aggregation operator
			checkForUnboundArguments(arguments);
			checkNumberOfArgumentsEqualTo(numberOfConsequentArguments, arguments.size());

			SQWRLResultGenerator resultGenerator = getSQWRLResultGenerator(getInvokingRuleName());
			SWRLBuiltInArgument argument = arguments.get(resultArgumentNumber);

			if (!resultGenerator.isRowOpen())
				resultGenerator.openRow();

			if (argument instanceof SWRLLiteralBuiltInArgument) {
				SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
				SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
				if (literal.isNumeric()) {
					resultGenerator.addRowData(literal);
				} else
					throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got: " + argument);
			} else
				throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got: " + argument);

			result = true;
		} else
			result = greatest(arguments); // Redirect to SQWRL collection operator

		return result;
	}

	public boolean sum(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreAntecedentArguments = 2, numberOfConsequentArguments = 1;
		boolean result = false;

		if (getIsInConsequent()) { // Simple SQWRL aggregation operator
			checkForUnboundArguments(arguments);
			checkNumberOfArgumentsEqualTo(numberOfConsequentArguments, arguments.size());

			SQWRLResultGenerator resultGenerator = getSQWRLResultGenerator(getInvokingRuleName());
			SWRLBuiltInArgument argument = arguments.get(resultArgumentNumber);

			if (!resultGenerator.isRowOpen())
				resultGenerator.openRow();

			if (argument instanceof SWRLLiteralBuiltInArgument) {
				SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
				SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
				if (literal.isNumeric())
					resultGenerator.addRowData(literal);
				else
					throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got " + argument
							+ " with type " + argument.getClass().getCanonicalName());
			} else
				throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got " + argument
						+ " with type " + argument.getClass().getCanonicalName());

			result = true;
		} else { // SQWRL collection operator
			Collection<SWRLBuiltInArgument> collection = getCollectionInSingleCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreAntecedentArguments);

			if (collection.isEmpty())
				result = false;
			else {
				double sumValue = 0, value;
				for (SWRLBuiltInArgument element : collection) {
					checkThatElementIsComparable(element);
					value = getArgumentAsADouble(element);
					sumValue += value;
				}
				result = processResultArgument(arguments, resultArgumentNumber, sumValue);
			}
		}

		return result;
	}

	public boolean avg(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreAntecedentArguments = 2, numberOfConsequentArguments = 1;
		boolean result = false;

		if (getIsInConsequent()) { // Simple SQWRL aggregation operator
			checkForUnboundArguments(arguments);
			checkNumberOfArgumentsEqualTo(numberOfConsequentArguments, arguments.size());

			SQWRLResultGenerator resultGenerator = getSQWRLResultGenerator(getInvokingRuleName());
			SWRLArgument argument = arguments.get(0);

			if (!resultGenerator.isRowOpen())
				resultGenerator.openRow();

			if (argument instanceof SWRLLiteralBuiltInArgument) {
				SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
				SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
				if (literal.isNumeric())
					resultGenerator.addRowData(literal);
				else
					throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got " + argument
							+ " with type " + argument.getClass().getCanonicalName());
			} else
				throw new InvalidBuiltInArgumentException(resultArgumentNumber, "expecting numeric literal, got " + argument
						+ " with type " + argument.getClass().getCanonicalName());
		} else { // SQWRL collection operator
			Collection<SWRLBuiltInArgument> collection = getCollectionInSingleCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreAntecedentArguments);

			if (collection.isEmpty())
				result = false;
			else {
				double avgValue, sumValue = 0, value;
				for (SWRLBuiltInArgument element : collection) {
					checkThatElementIsComparable(element);
					value = getArgumentAsADouble(element);
					sumValue += value;
				}
				avgValue = sumValue / collection.size();

				result = processResultArgument(arguments, resultArgumentNumber, avgValue);
			}
		}

		return result;
	}

	public boolean median(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreConsequentArguments = 2;
		boolean result = false;

		if (getIsInConsequent()) { // Simple SQWRL aggregation operator
			throw new BuiltInException("not implemented");
		} else { // SQWRL collection operator
			Collection<SWRLBuiltInArgument> collection = getCollectionInSingleCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreConsequentArguments);

			if (collection.isEmpty())
				result = false;
			else {
				double[] valueArray = new double[collection.size()];
				int count = 0, middle = collection.size() / 2;
				double medianValue, value;

				for (SWRLBuiltInArgument element : collection) {
					checkThatElementIsComparable(element);
					value = getArgumentAsADouble(element);
					valueArray[count++] = value;
				}

				Arrays.sort(valueArray);

				if (collection.size() % 2 == 1)
					medianValue = valueArray[middle];
				else
					medianValue = (valueArray[middle - 1] + valueArray[middle]) / 2;

				result = processResultArgument(arguments, resultArgumentNumber, medianValue);
			}
		}

		return result;
	}

	public boolean nth(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;
		boolean result = false;

		if (getIsInConsequent())
			result = true; // Post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments) - 1; // 1-offset for user, 0 for processing

			if (!sortedList.isEmpty()) {
				if (n >= 0 && n < sortedList.size()) {
					SWRLBuiltInArgument nth = sortedList.get(n);
					result = processResultArgument(arguments, resultArgumentNumber, nth);
				} else
					result = false;
			}
		}

		return result;
	}

	public boolean greatest(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreArguments = 2;
		boolean result = false;

		if (getIsInConsequent())
			result = true; // Post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);

			if (!sortedList.isEmpty()) {
				SWRLBuiltInArgument greatest = sortedList.get(sortedList.size() - 1);
				result = processResultArgument(arguments, resultArgumentNumber, greatest);
			}
		}

		return result;
	}

	public boolean nthGreatest(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;
		boolean result = false;

		if (getIsInConsequent())
			result = true; // Post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);

			if (!sortedList.isEmpty() && n > 0 && n <= sortedList.size()) {
				SWRLBuiltInArgument nthGreatest = sortedList.get(sortedList.size() - n);
				result = processResultArgument(arguments, resultArgumentNumber, nthGreatest);
			} else
				result = false;
		}

		return result;
	}

	public boolean least(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreArguments = 2;
		boolean result = false;

		if (getIsInConsequent())
			result = true; // Post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);

			if (!sortedList.isEmpty()) {
				SWRLBuiltInArgument least = sortedList.get(resultArgumentNumber);
				result = processResultArgument(arguments, resultArgumentNumber, least);
			}
		}

		return result;
	}

	/*********************************************************************************************************************
	 * 
	 * SQWRL operators that work with a single collection and return a collection
	 * 
	 *********************************************************************************************************************/

	public boolean notNthGreatest(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);

			if (!sortedList.isEmpty() && n > 0 && n <= sortedList.size())
				sortedList.remove(sortedList.size() - n);

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, sortedList);
		}
	}

	public boolean nthSlice(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, sliceSizeArgumentNumber = 3, numberOfCoreArguments = 4;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments) - 1; // 1-offset for user, 0 for processing
			int sliceSize = getArgumentAsAPositiveInteger(sliceSizeArgumentNumber, arguments);
			List<SWRLBuiltInArgument> slice = new ArrayList<SWRLBuiltInArgument>();

			if (!sortedList.isEmpty() && n >= 0) {
				int startIndex = n;
				int finishIndex = n + sliceSize - 1;
				for (int index = startIndex; index <= finishIndex && index < sortedList.size(); index++)
					slice.add(sortedList.get(index));
			}

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, slice);
		}
	}

	public boolean notNthSlice(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, sliceSizeArgumentNumber = 3, numberOfCoreArguments = 4;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments) - 1; // 1-offset for user, 0 for processing
			int sliceSize = getArgumentAsAPositiveInteger(sliceSizeArgumentNumber, arguments);
			List<SWRLBuiltInArgument> notSlice = new ArrayList<SWRLBuiltInArgument>();

			if (!sortedList.isEmpty() && n >= 0 && n < sortedList.size()) {
				int startIndex = n;
				int finishIndex = n + sliceSize - 1;
				for (int index = 0; index < sortedList.size(); index++)
					if (index < startIndex || index > finishIndex)
						notSlice.add(sortedList.get(index));
			}

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, notSlice);
		}
	}

	public boolean nthGreatestSlice(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, sliceSizeArgumentNumber = 3, numberOfCoreArguments = 4;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);
			List<SWRLBuiltInArgument> slice = new ArrayList<SWRLBuiltInArgument>();
			int sliceSize = getArgumentAsAPositiveInteger(sliceSizeArgumentNumber, arguments);

			if (!sortedList.isEmpty() && n > 0) {
				int startIndex = sortedList.size() - n;
				int finishIndex = startIndex + sliceSize - 1;
				if (startIndex < 0)
					startIndex = 0;
				for (int index = startIndex; index <= finishIndex && index < sortedList.size(); index++)
					slice.add(sortedList.get(index));
			}

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, slice);
		}
	}

	public boolean notNthGreatestSlice(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, sliceSizeArgumentNumber = 3, numberOfCoreArguments = 4;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);
			int sliceSize = getArgumentAsAPositiveInteger(sliceSizeArgumentNumber, arguments);
			List<SWRLBuiltInArgument> slice = new ArrayList<SWRLBuiltInArgument>();

			if (!sortedList.isEmpty() && n > 0 && n <= sortedList.size()) {
				int startIndex = sortedList.size() - n;
				int finishIndex = startIndex + sliceSize - 1;
				for (int index = 0; index < sortedList.size(); index++)
					if (index < startIndex || index > finishIndex)
						slice.add(sortedList.get(index));
			}

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, slice);
		}
	}

	public boolean notNth(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments) - 1; // 1-offset for user, 0 for processing

			if (!sortedList.isEmpty() && n >= 0 && n < sortedList.size())
				sortedList.remove(n);

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, sortedList);
		}
	}

	public boolean notGreatest(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreArguments = 2;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);

			if (!sortedList.isEmpty())
				sortedList.remove(sortedList.size() - 1);

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, sortedList);
		}
	}

	public boolean greatestN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);
			List<SWRLBuiltInArgument> greatestN = new ArrayList<SWRLBuiltInArgument>();

			if (!sortedList.isEmpty() && n > 0) {
				int startIndex = sortedList.size() - n;
				if (startIndex < 0)
					startIndex = 0;
				for (int i = startIndex; i < sortedList.size(); i++)
					greatestN.add(sortedList.get(i));
			}

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, greatestN);
		}
	}

	public boolean notGreatestN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);
			List<SWRLBuiltInArgument> notGreatestN = new ArrayList<SWRLBuiltInArgument>();

			if (!sortedList.isEmpty() && n > 0) {
				int startIndex = sortedList.size() - n;
				if (startIndex < 0)
					startIndex = 0;
				for (int i = 0; i < startIndex; i++)
					notGreatestN.add(sortedList.get(i));
			}

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, notGreatestN);
		}
	}

	public boolean notLeast(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, numberOfCoreArguments = 2;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);

			if (!sortedList.isEmpty())
				sortedList.remove(0); // Remove the first (least) element; if there are multiple element with same least value,
															// they will not be removed

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, sortedList);
		}
	}

	public boolean leastN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments) - 1;
			List<SWRLBuiltInArgument> leastN = new ArrayList<SWRLBuiltInArgument>();

			for (int i = 0; i <= n && i < sortedList.size(); i++)
				leastN.add(sortedList.get(i));

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, leastN);
		}
	}

	public boolean notLeastN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		final int resultCollectionArgumentNumber = 0, sourceCollectionArgumentNumber = 1, nArgumentNumber = 2, numberOfCoreArguments = 3;

		if (getIsInConsequent())
			return true; // Non collection operator that is post processed - ignore
		else {
			List<SWRLBuiltInArgument> sortedList = getSortedListInSingleOperandCollectionOperation(arguments,
					sourceCollectionArgumentNumber, numberOfCoreArguments);
			int n = getArgumentAsAPositiveInteger(nArgumentNumber, arguments);
			List<SWRLBuiltInArgument> notLeastN = new ArrayList<SWRLBuiltInArgument>();

			for (int i = n; i >= 0 && i < sortedList.size(); i++)
				notLeastN.add(sortedList.get(i));

			return processSingleOperandCollectionOperationListResult(arguments, resultCollectionArgumentNumber,
					sourceCollectionArgumentNumber, numberOfCoreArguments, notLeastN);
		}
	}

	/*********************************************************************************************************************
	 * 
	 * SQWRL operators that work with two collections and return an element or evaluate to true or false
	 * 
	 *********************************************************************************************************************/

	public boolean intersects(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int collection1ArgumentNumber = 0, collection2ArgumentNumber = 1, numberOfCoreArguments = 2;
		String queryName = getInvokingRuleName();
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);
		Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
		Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);

		return !Collections.disjoint(collection1, collection2);
	}

	public boolean notIntersects(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		return !intersects(arguments);
	}

	public boolean contains(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int collection1ArgumentNumber = 0, collection2ArgumentNumber = 1, numberOfCoreArguments = 2;
		String queryName = getInvokingRuleName();
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);
		Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
		Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);

		return collection1.containsAll(collection2);
	}

	public boolean notContains(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		return !contains(arguments);
	}

	public boolean equal(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int collection1ArgumentNumber = 0, collection2ArgumentNumber = 1, numberOfCoreArguments = 2;
		String queryName = getInvokingRuleName();
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);

		if (collection1GroupKey.equals(collection2GroupKey))
			return true; // The same collection was passed
		else { // Different collections - compare them
			Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
			Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);
			return collection1.equals(collection2); // Remember, sets and lists will not be equal
		}
	}

	public boolean notEqual(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		return !equal(arguments);
	}

	/*********************************************************************************************************************
	 * 
	 * SQWRL operators that work with two collections and return a collection
	 * 
	 *********************************************************************************************************************/

	public boolean intersection(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultCollectionArgumentNumber = 0, collection1ArgumentNumber = 1, collection2ArgumentNumber = 2, numberOfCoreArguments = 3;
		String queryName = getInvokingRuleName();
		String resultCollectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		int collectionResultNumberOfGroupElements = collection1NumberOfGroupElements + collection2NumberOfGroupElements;
		String resultCollectionGroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				resultCollectionArgumentNumber, numberOfCoreArguments, 0, collectionResultNumberOfGroupElements);
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, 0 + collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);
		Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
		Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);
		Collection<SWRLBuiltInArgument> intersection = new HashSet<SWRLBuiltInArgument>(collection1);

		intersection.retainAll(collection2);

		if (!isCollection(queryName, resultCollectionName, resultCollectionGroupKey))
			recordCollection(queryName, resultCollectionName, resultCollectionGroupKey, intersection);

		if (isUnboundArgument(resultCollectionArgumentNumber, arguments))
			arguments.get(resultCollectionArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, resultCollectionName, resultCollectionGroupKey));

		return true;
	}

	public boolean append(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultCollectionArgumentNumber = 0, collection1ArgumentNumber = 1, collection2ArgumentNumber = 2, numberOfCoreArguments = 3;
		String queryName = getInvokingRuleName();
		String resultCollectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		int resultCollectionNumberOfGroupElements = collection1NumberOfGroupElements + collection2NumberOfGroupElements;
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, 0 + collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);
		String resultCollectionGroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				resultCollectionArgumentNumber, numberOfCoreArguments, 0, resultCollectionNumberOfGroupElements);
		Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
		Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);
		List<SWRLBuiltInArgument> resultCollection = new ArrayList<SWRLBuiltInArgument>(collection1);

		resultCollection.addAll(collection2);

		if (!isCollectionRecorded(resultCollectionName, resultCollectionGroupKey))
			recordCollection(queryName, resultCollectionName, resultCollectionGroupKey, resultCollection);

		if (isUnboundArgument(resultCollectionArgumentNumber, arguments))
			arguments.get(resultCollectionArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, resultCollectionName, resultCollectionGroupKey));

		return true;
	}

	public boolean union(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultCollectionArgumentNumber = 0, collection1ArgumentNumber = 1, collection2ArgumentNumber = 2, numberOfCoreArguments = 3;
		String queryName = getInvokingRuleName();
		String resultCollectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		int resultCollectionNumberOfGroupElements = collection1NumberOfGroupElements + collection2NumberOfGroupElements;
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, 0 + collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);
		String resultCollectionGroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				resultCollectionArgumentNumber, numberOfCoreArguments, 0, resultCollectionNumberOfGroupElements);
		Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
		Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);
		Set<SWRLBuiltInArgument> union = new HashSet<SWRLBuiltInArgument>(collection1);

		union.addAll(collection2);

		if (!isCollection(queryName, resultCollectionName, resultCollectionGroupKey))
			recordCollection(queryName, resultCollectionName, resultCollectionGroupKey, union);

		if (isUnboundArgument(resultCollectionArgumentNumber, arguments))
			arguments.get(resultCollectionArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, resultCollectionName, resultCollectionGroupKey));

		return true;
	}

	public boolean difference(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkThatInAntecedent();

		final int resultCollectionArgumentNumber = 0, collection1ArgumentNumber = 1, collection2ArgumentNumber = 2, numberOfCoreArguments = 3;
		String queryName = getInvokingRuleName();
		String resultCollectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String collection1Name = getCollectionName(arguments, collection1ArgumentNumber);
		String collection2Name = getCollectionName(arguments, collection2ArgumentNumber);
		int collection1NumberOfGroupElements = getNumberOfGroupElements(queryName, collection1Name);
		int collection2NumberOfGroupElements = getNumberOfGroupElements(queryName, collection2Name);
		int collectionResultNumberOfGroupElements = collection1NumberOfGroupElements + collection2NumberOfGroupElements;
		String resultCollectionGroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				resultCollectionArgumentNumber, numberOfCoreArguments, 0, collectionResultNumberOfGroupElements);
		String collection1GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection1ArgumentNumber, numberOfCoreArguments, 0, collection1NumberOfGroupElements);
		String collection2GroupKey = getCollectionGroupKeyInMultiOperandCollectionOperation(arguments,
				collection2ArgumentNumber, numberOfCoreArguments, 0 + collection1NumberOfGroupElements,
				collection2NumberOfGroupElements);
		Collection<SWRLBuiltInArgument> collection1 = getCollection(queryName, collection1Name, collection1GroupKey);
		Collection<SWRLBuiltInArgument> collection2 = getCollection(queryName, collection2Name, collection2GroupKey);
		Collection<SWRLBuiltInArgument> difference = new HashSet<SWRLBuiltInArgument>(collection1);

		difference.removeAll(collection2);

		if (!isCollection(queryName, resultCollectionName, resultCollectionGroupKey))
			recordCollection(queryName, resultCollectionName, resultCollectionGroupKey, difference);

		if (isUnboundArgument(resultCollectionArgumentNumber, arguments))
			arguments.get(resultCollectionArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, resultCollectionName, resultCollectionGroupKey));

		return true;
	}

	/*********************************************************************************************************************
	 * 
	 * Alias definitions for SQWRL operators
	 * 
	 *********************************************************************************************************************/

	public boolean nthLast(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return nthGreatest(arguments);
	}

	public boolean notNthLast(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return notNthGreatest(arguments);
	}

	public boolean nthLastSlice(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return nthGreatestSlice(arguments);
	}

	public boolean notNthLastSlice(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return notNthGreatestSlice(arguments);
	}

	public boolean last(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return greatest(arguments);
	}

	public boolean notLast(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return notGreatest(arguments);
	}

	public boolean lastN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return greatestN(arguments);
	}

	public boolean notLastN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return notGreatestN(arguments);
	}

	public boolean first(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return least(arguments);
	}

	public boolean notFirst(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return notLeast(arguments);
	}

	public boolean firstN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return leastN(arguments);
	}

	public boolean notFirstN(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return notLeastN(arguments);
	}

	/*********************************************************************************************************************
	 * 
	 * Internal methods
	 * 
	 *********************************************************************************************************************/

	private String getCollectionGroupKeyInMake(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		// The collection is the first argument, the element is the second; subsequent arguments (if any) are group elements
		final int collectionArgumentNumber = 0, numberOfCoreArguments = 2;
		checkNumberOfArgumentsAtLeast(numberOfCoreArguments, arguments.size());

		String queryName = getInvokingRuleName();
		String collectionName = getCollectionName(arguments, collectionArgumentNumber);
		String collectionKey = createCollectionKey(queryName, collectionName);
		int numberOfGroupArguments = arguments.size() - numberOfCoreArguments;
		boolean hasGroupPattern = numberOfGroupArguments != 0;
		String groupPattern = !hasGroupPattern ? "" : createInvocationPattern(getBuiltInBridge(), queryName, 0, false,
				arguments.subList(2, arguments.size()));

		if (isBoundArgument(collectionArgumentNumber, arguments)
				&& !this.collectionGroupElementNumbersMap.containsKey(collectionKey)) {
			// Collection variable already used in non collection context
			throw new BuiltInException("collection variable ?" + arguments.get(collectionArgumentNumber).getVariableName()
					+ " already used in non collection context in query " + queryName);
		}

		if (hasGroupPattern) {
			if (!this.collectionGroupElementNumbersMap.containsKey(collectionKey))
				this.collectionGroupElementNumbersMap.put(collectionKey, numberOfGroupArguments);
			else if (this.collectionGroupElementNumbersMap.get(collectionKey) != numberOfGroupArguments) {
				throw new BuiltInException("internal error: inconsistent number of group elements for collection "
						+ collectionName + " in query " + queryName);
			}
			return groupPattern;
		} else {
			if (this.collectionGroupElementNumbersMap.containsKey(collectionKey)) {
				if (this.collectionGroupElementNumbersMap.get(collectionKey) != 0) {
					throw new BuiltInException("internal error: inconsistent number of group elements for collection "
							+ collectionName + " in query " + queryName);
				}
			} else
				this.collectionGroupElementNumbersMap.put(collectionKey, 0);
			return "";
		}
	}

	private String getCollectionGroupKeyInSingleCollectionOperation(List<SWRLBuiltInArgument> arguments,
			int coreNumberOfArguments) throws BuiltInException
	{
		String queryName = getInvokingRuleName();

		checkThatInAntecedent();

		if ((arguments.size() > coreNumberOfArguments)) // Is a grouped collection
			return createInvocationPattern(getBuiltInBridge(), queryName, 0, false,
					arguments.subList(coreNumberOfArguments, arguments.size()));
		else
			return "";
	}

	private String getCollectionGroupKeyInMultiOperandCollectionOperation(List<SWRLBuiltInArgument> arguments,
			int collectionArgumentNumber, int coreNumberOfArguments, int groupArgumentOffset,
			int numberOfRelevantGroupArguments) throws BuiltInException
	{
		String queryName = getInvokingRuleName();
		String collectionName = getCollectionName(arguments, collectionArgumentNumber);
		String collectionKey = createCollectionKey(queryName, collectionName);

		checkThatInAntecedent();

		if (!this.collectionGroupElementNumbersMap.containsKey(collectionKey))
			this.collectionGroupElementNumbersMap.put(collectionKey, numberOfRelevantGroupArguments);

		if (numberOfRelevantGroupArguments != 0) // Is a grouped collection
			return createInvocationPattern(
					getBuiltInBridge(),
					queryName,
					0,
					false,
					arguments.subList(coreNumberOfArguments + groupArgumentOffset, coreNumberOfArguments + groupArgumentOffset
							+ numberOfRelevantGroupArguments));
		else
			return "";
	}

	private boolean processSingleOperandCollectionOperationListResult(List<SWRLBuiltInArgument> arguments,
			int resultCollectionArgumentNumber, int sourceCollectionArgumentNumber, int numberOfCoreArguments,
			Collection<SWRLBuiltInArgument> resultList) throws BuiltInException
	{
		String queryName = getInvokingRuleName();
		String sourceCollectionName = getCollectionName(arguments, sourceCollectionArgumentNumber);
		String resultCollectionName = getCollectionName(arguments, resultCollectionArgumentNumber);
		String resultCollectionGroupKey = getCollectionGroupKeyInSingleCollectionOperation(arguments, numberOfCoreArguments);
		String resultCollectionKey = createCollectionKey(queryName, resultCollectionName);

		if (!isCollection(queryName, resultCollectionName, resultCollectionGroupKey))
			recordCollection(queryName, resultCollectionName, resultCollectionGroupKey, resultList);

		if (!this.collectionGroupElementNumbersMap.containsKey(resultCollectionKey)) // Give it the same number of group
																																									// elements as the source collection
			this.collectionGroupElementNumbersMap.put(resultCollectionKey,
					getNumberOfGroupElements(queryName, sourceCollectionName));

		return processListResultArgument(arguments, resultCollectionArgumentNumber, resultCollectionName,
				resultCollectionGroupKey, resultList);
	}

	private boolean processListResultArgument(List<SWRLBuiltInArgument> arguments, int resultArgumentNumber,
			String resultListName, String resultListID, Collection<SWRLBuiltInArgument> resultList) throws BuiltInException
	{
		checkArgumentNumber(resultArgumentNumber, arguments);

		String queryName = getInvokingRuleName();

		if (isUnboundArgument(resultArgumentNumber, arguments)) {
			arguments.get(resultArgumentNumber).setBuiltInResult(
					createCollectionArgument(queryName, resultListName, resultListID));
			return true;
		} else {
			Collection<SWRLBuiltInArgument> collection = getCollection(queryName, resultListName, resultListID);
			return collection.equals(resultList); // Remember, sets and lists will not be equal
		}
	}

	private SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws BuiltInException
	{
		return getBuiltInBridge().getSQWRLResultGenerator(queryName);
	}

	private void checkThatElementIsComparable(SWRLBuiltInArgument argument) throws BuiltInException
	{
		if (argument instanceof SWRLLiteralBuiltInArgument) {
			SWRLLiteralBuiltInArgument literalArgument = (SWRLLiteralBuiltInArgument)argument;
			SQWRLLiteralResultValue literal = getSQWRLResultValueFactory().getLiteral(literalArgument.getLiteral());
			if (!literal.isComparable())
				throw new BuiltInException("may only be applied to collections with comparable elements");
		} else
			throw new BuiltInException("may only be applied to collections with comparable elements");
	}

	private Collection<SWRLBuiltInArgument> getCollectionInSingleCollectionOperation(List<SWRLBuiltInArgument> arguments,
			int sourceCollectionArgumentNumber, int coreNumberOfArguments) throws BuiltInException
	{
		String queryName = getInvokingRuleName();
		String collectionName = getCollectionName(arguments, sourceCollectionArgumentNumber);
		String collectionGroupKey = getCollectionGroupKeyInSingleCollectionOperation(arguments, coreNumberOfArguments);

		return getCollection(queryName, collectionName, collectionGroupKey);
	}

	private List<SWRLBuiltInArgument> getSortedListInSingleOperandCollectionOperation(
			List<SWRLBuiltInArgument> arguments, int sourceCollectionArgumentNumber, int coreNumberOfArguments)
			throws BuiltInException
	{
		String queryName = getInvokingRuleName();
		String collectionName = getCollectionName(arguments, sourceCollectionArgumentNumber);
		String collectionGroupKey = getCollectionGroupKeyInSingleCollectionOperation(arguments, coreNumberOfArguments);

		return getSortedList(queryName, collectionName, collectionGroupKey);
	}

	// We do not cache because only one built-in will typically perform an operation on a particular collection per query.
	// Note: currently implementations may modify the returned collection.
	private List<SWRLBuiltInArgument> getSortedList(String queryName, String collectionName, String collectionGroupKey)
			throws BuiltInException
	{
		Collection<SWRLBuiltInArgument> collection = getCollection(queryName, collectionName, collectionGroupKey);
		List<SWRLBuiltInArgument> result = new ArrayList<SWRLBuiltInArgument>(collection);
		Collections.sort(result);

		return result;
	}

	private List<SWRLBuiltInArgument> createBag(String queryName, String collectionName, String collectionGroupKey)
			throws BuiltInException
	{
		List<SWRLBuiltInArgument> bag = new ArrayList<SWRLBuiltInArgument>();

		recordCollection(queryName, collectionName, collectionGroupKey, bag);

		return bag;
	}

	private Set<SWRLBuiltInArgument> createSet(String queryName, String collectionName, String collectionGroupKey)
			throws BuiltInException
	{
		Set<SWRLBuiltInArgument> set = new HashSet<SWRLBuiltInArgument>();

		recordCollection(queryName, collectionName, collectionGroupKey, set);

		return set;
	}

	private String getCollectionName(List<SWRLBuiltInArgument> arguments, int collectionArgumentNumber)
			throws BuiltInException
	{
		return getVariableName(collectionArgumentNumber, arguments);
	}

	private int getNumberOfGroupElements(String queryName, String collectionName) throws BuiltInException
	{
		String collectionKey = createCollectionKey(queryName, collectionName);

		if (!this.collectionGroupElementNumbersMap.containsKey(collectionKey))
			throw new BuiltInException("internal error: invalid collection name " + collectionName + " in query " + queryName
					+ "; no group element number found");

		return this.collectionGroupElementNumbersMap.get(collectionKey);
	}

	// An ungrouped collection will have a collectionGroupKey of the empty string so will not be partitioned.
	private void recordCollection(String queryName, String collectionName, String collectionGroupKey,
			Collection<SWRLBuiltInArgument> collection) throws BuiltInException
	{
		String collectionKey = createCollectionKey(queryName, collectionName);

		if (!isCollectionRecorded(queryName, collectionName)) { // Is the collection recorded?
			if (isBag(collection))
				this.bagKeys.add(collectionKey);
			else if (isSet(collection))
				this.setKeys.add(collectionKey);
			else
				throw new BuiltInException("internal error: collection " + collectionName + " in query " + queryName
						+ " with group key " + collectionGroupKey + " is neither a bag or a set");

			this.collectionsMap.put(collectionKey, new HashMap<String, Collection<SWRLBuiltInArgument>>());
		}

		if (!isCollection(queryName, collectionName, collectionGroupKey)) { // Is the collection for this group recorded
			if (isBag(queryName, collectionName) && !isBag(collection))
				throw new BuiltInException("attempt to add non bag elements to bag " + collectionName + " in query "
						+ queryName + "; group key=" + collectionGroupKey);

			if (isSet(queryName, collectionName) && !isSet(collection))
				throw new BuiltInException("attempt to add non set elements to set " + collectionName + " in query "
						+ queryName + "; group key=" + collectionGroupKey);

			this.collectionsMap.get(collectionKey).put(collectionGroupKey, collection);
		}
	}

	private Collection<SWRLBuiltInArgument> getCollection(String queryName, String collectionName,
			String collectionGroupKey) throws BuiltInException
	{
		String collectionKey = queryName + ":" + collectionName;

		if (!isCollection(queryName, collectionName, collectionGroupKey))
			throw new BuiltInException("collection argument in query " + queryName + " with name " + collectionName
					+ " and group key " + collectionGroupKey + " does not refer to a collection");

		return this.collectionsMap.get(collectionKey).get(collectionGroupKey);
	}

	private boolean isCollection(String queryName, String collectionName, String collectionGroupKey)
	{
		String collectionKey = createCollectionKey(queryName, collectionName);

		return this.collectionsMap.containsKey(collectionKey)
				&& this.collectionsMap.get(collectionKey).containsKey(collectionGroupKey);
	}

	private boolean isSet(String queryName, String collectionName)
	{
		String setKey = createCollectionKey(queryName, collectionName);

		return this.setKeys.contains(setKey);
	}

	private boolean isBag(String queryName, String collectionName)
	{
		String bagKey = createCollectionKey(queryName, collectionName);

		return this.bagKeys.contains(bagKey);
	}

	private boolean isCollectionRecorded(String queryName, String collectionName) throws BuiltInException
	{
		String collectionKey = createCollectionKey(queryName, collectionName);

		return this.collectionsMap.containsKey(collectionKey);
	}

	private boolean isBag(Collection<SWRLBuiltInArgument> collection)
	{
		return (collection instanceof List<?>);
	}

	private boolean isSet(Collection<SWRLBuiltInArgument> collection)
	{
		return (collection instanceof Set<?>);
	}

	private String createCollectionKey(String queryName, String collectionName)
	{
		return queryName + ":" + collectionName;
	}

	@SuppressWarnings("unused")
	private Collection<SWRLBuiltInArgument> ungroupCollection(String queryName, String collectionName)
			throws BuiltInException
	{
		if (!isCollectionRecorded(queryName, collectionName))
			throw new BuiltInException(collectionName + " in query " + queryName + " is not a collection");
		else {
			Collection<SWRLBuiltInArgument> ungroupedCollection = isSet(queryName, collectionName) ? new HashSet<SWRLBuiltInArgument>()
					: new ArrayList<SWRLBuiltInArgument>();
			String collectionKey = createCollectionKey(queryName, collectionName);

			for (String collectionGroupKey : this.collectionsMap.get(collectionKey).keySet()) {
				ungroupedCollection.addAll(this.collectionsMap.get(collectionKey).get(collectionGroupKey));
			}
			return ungroupedCollection;
		}
	}
}
