package org.swrlapi.converters;

import java.util.Set;

import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.core.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLVariable variableArgument) throws TargetRuleEngineException;

	T convert(SWRLIndividualArgument individualArgument) throws TargetRuleEngineException;

	T convert(SWRLLiteralArgument literalArgument) throws TargetRuleEngineException;

	T convert(SWRLVariableBuiltInArgument variableArgument) throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument classArgument) throws TargetRuleEngineException;

	T convert(SWRLNamedIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLVariableBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLVariable argument, String fieldName, Set<String> variableShortNames) throws TargetRuleEngineException;

	T convert(SWRLIndividualArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLLiteralArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLNamedIndividualBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLAnnotationPropertyBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument datatypeArgument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;

	T convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SQWRLCollectionVariableBuiltInArgument argument, String fieldName, Set<String> variableShortNames)
			throws TargetRuleEngineException;
}
