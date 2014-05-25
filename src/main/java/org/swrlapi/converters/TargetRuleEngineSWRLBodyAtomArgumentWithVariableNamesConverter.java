package org.swrlapi.converters;

import java.util.Set;

import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
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

	T convert(SWRLVariableBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SWRLVariable argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames)
			throws TargetRuleEngineException;

	T convert(SWRLIndividualArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames)
			throws TargetRuleEngineException;

	T convert(SWRLLiteralArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames)
			throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames)
			throws TargetRuleEngineException;

	T convert(SWRLNamedIndividualBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SWRLAnnotationPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument datatypeArgument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;

	T convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SQWRLCollectionVariableBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException;
}
