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

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL atom arguments to
 * native rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface TargetRuleEngineSWRLAtomArgumentConverter<T> extends TargetRuleEngineConverter
{
  @NonNull T convert(@NonNull SWRLVariable argument);

  @NonNull T convert(@NonNull SWRLLiteralArgument argument);

  @NonNull T convert(@NonNull SWRLIndividualArgument argument);

  @NonNull T convert(@NonNull SWRLVariableBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLClassBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLNamedIndividualBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLObjectPropertyBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLDataPropertyBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLDatatypeBuiltInArgument argument);

  @NonNull T convert(@NonNull SWRLLiteralBuiltInArgument argument);

  @NonNull T convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument);
}
