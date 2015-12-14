package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;

public interface TargetRuleEngineOWLIndividualConverter<TR> extends TargetRuleEngineConverter
{
  @NonNull TR convert(@NonNull OWLIndividual individual);
}
