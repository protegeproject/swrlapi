package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

import java.util.Set;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL body atoms to native
 * rule clauses.
 * <p>
 * This interface can be used by implementation that need to track variable definitions as each atom is defined.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 *
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public interface TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter<T> extends TargetRuleEngineConverter
{
  @NonNull T convert(@NonNull SWRLClassAtom atom, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);

  @NonNull T convert(@NonNull SWRLDataPropertyAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);

  @NonNull T convert(@NonNull SWRLObjectPropertyAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);

  @NonNull T convert(@NonNull SWRLSameIndividualAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);

  @NonNull T convert(@NonNull SWRLDifferentIndividualsAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);

  @NonNull T convert(@NonNull SWRLAPIBuiltInAtom atom, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);

  @NonNull T convert(@NonNull SWRLDataRangeAtom atom, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames);
}
