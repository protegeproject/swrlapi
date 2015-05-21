package org.swrlapi.bridge.converters;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;

public interface TargetRuleEngineOWLIndividualConverter<TR> extends TargetRuleEngineConverter
{
  @NonNull TR convert(@NonNull OWLIndividual individual);
}
