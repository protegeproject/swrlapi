package org.protege.swrlapi.converters;

import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineSWRLBuiltInArgumentConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLIndividualBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException;
	
	T convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException;

	T convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException;
}
