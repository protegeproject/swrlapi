package org.swrlapi.converters;

import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeAtomArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualAtomArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL arguments to atoms
 * and built-ins to native rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLAtomArgumentConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLVariableAtomArgument variableArgument) throws TargetRuleEngineException;;

	T convert(SWRLClassAtomArgument classArgument) throws TargetRuleEngineException;

	T convert(SWRLNamedIndividualAtomArgument individualArgument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLAnnotationPropertyAtomArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeAtomArgument propertyArgument) throws TargetRuleEngineException;

	T convert(SWRLLiteralAtomArgument argument) throws TargetRuleEngineException;

	// SWRLBuiltInArguments

	T convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLNamedIndividualBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException;
}
