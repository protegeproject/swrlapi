package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

/**
 * This interface describes methods that can be implemented by a target rule engine to convert SWRL body atoms to native
 * rule clauses.
 * <p>
 * Implementors may also chose an alternate conversion approach.
 */
public interface TargetRuleEngineSWRLBodyAtomConverter<T> extends TargetRuleEngineConverter
{
  @NonNull T convert(@NonNull SWRLClassAtom atom);

  @NonNull T convert(@NonNull SWRLDataPropertyAtom atom);

  @NonNull T convert(@NonNull SWRLObjectPropertyAtom atom);

  @NonNull T convert(@NonNull SWRLSameIndividualAtom atom);

  @NonNull T convert(@NonNull SWRLDifferentIndividualsAtom atom);

  @NonNull T convert(@NonNull SWRLAPIBuiltInAtom atom);

  @NonNull T convert(@NonNull SWRLDataRangeAtom atom);
}
