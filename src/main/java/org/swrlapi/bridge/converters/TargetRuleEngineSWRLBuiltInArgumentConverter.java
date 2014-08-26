package org.swrlapi.bridge.converters;

import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface TargetRuleEngineSWRLBuiltInArgumentConverter<T> extends TargetRuleEngineConverter
{
	T convert(SWRLVariableBuiltInArgument argument);

	T convert(SWRLClassBuiltInArgument argument);

	T convert(SWRLNamedIndividualBuiltInArgument argument);

	T convert(SWRLObjectPropertyBuiltInArgument argument);

	T convert(SWRLDataPropertyBuiltInArgument argument);

	T convert(SWRLAnnotationPropertyBuiltInArgument argument);

	T convert(SWRLDatatypeBuiltInArgument argument);

	T convert(SWRLLiteralBuiltInArgument argument);

	T convert(SQWRLCollectionVariableBuiltInArgument argument);
}
