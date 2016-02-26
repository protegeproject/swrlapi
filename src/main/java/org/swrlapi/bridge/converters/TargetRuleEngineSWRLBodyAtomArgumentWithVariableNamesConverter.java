package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
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

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
  @NonNull T convert(@NonNull SWRLVariable variableArgument);

  @NonNull T convert(@NonNull SWRLIndividualArgument individualArgument);

  @NonNull T convert(@NonNull SWRLLiteralArgument literalArgument);

  @NonNull T convert(@NonNull SWRLVariableBuiltInArgument variableArgument);

  @NonNull T convert(@NonNull SWRLClassBuiltInArgument classArgument);

  @NonNull T convert(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument);

  @NonNull T convert(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument);

  @NonNull T convert(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument);

  @NonNull T convert(@NonNull SWRLAnnotationPropertyBuiltInArgument propertyArgument);

  @NonNull T convert(@NonNull SWRLLiteralBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLVariableBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLVariable argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLIndividualArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLLiteralArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLClassBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLNamedIndividualBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLObjectPropertyBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLDataPropertyBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLLiteralBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SWRLDatatypeBuiltInArgument datatypeArgument);

  @NonNull T convert(@NonNull SWRLDatatypeBuiltInArgument datatypeArgument, @NonNull String fieldName,
    Set<@NonNull String> previouslyEncounteredVariableNames);

  @NonNull T convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument);

  @NonNull T convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames);
}
