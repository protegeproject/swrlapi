package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface TargetRuleEngineSWRLBuiltInArgumentConverter<T> extends TargetRuleEngineConverter
{
  @NonNull T convert(@NonNull SWRLVariableBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLClassBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLClassExpressionBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLNamedIndividualBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLObjectPropertyBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLObjectPropertyExpressionBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLDataPropertyBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLDataPropertyExpressionBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLDatatypeBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLLiteralBuiltInArgument argument);

  @NonNull T convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument);
}
