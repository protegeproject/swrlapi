
package org.swrlapi.converters;

import java.util.Set;

import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeAtomArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
	// Atom arguments
	
	T convert(SWRLVariableAtomArgument variableArgument) throws TargetRuleEngineException;;

	T convert(SWRLClassAtomArgument classArgument) throws TargetRuleEngineException;

	T convert(SWRLIndividualAtomArgument individualArgument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLDataPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLLiteralAtomArgument argument) throws TargetRuleEngineException;
	
	T convert(SWRLDatatypeAtomArgument datatypeArgument) throws TargetRuleEngineException;
	
	T convert(SWRLVariableAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLClassAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLIndividualAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLLiteralAtomArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;
	
	T convert(SWRLDatatypeAtomArgument propertyArgument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	// Built-in arguments
	
	T convert(SWRLVariableBuiltInArgument variableArgument) throws TargetRuleEngineException;;

	T convert(SWRLClassBuiltInArgument classArgument) throws TargetRuleEngineException;

	T convert(SWRLIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException;
	
	T convert(SWRLVariableBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLIndividualBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument datatypeArgument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;
	
	T convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException;
	
	T convert(SQWRLCollectionBuiltInArgument argument, String fieldName, Set<String> variableNames) throws TargetRuleEngineException;	
}
