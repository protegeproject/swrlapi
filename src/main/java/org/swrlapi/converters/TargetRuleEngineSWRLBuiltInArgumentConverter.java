package org.swrlapi.converters;

import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineSWRLBuiltInArgumentConverter<T> extends TargetRuleEngineConverter
{
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
