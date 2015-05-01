package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLIndividual;

public interface TargetRuleEngineOWLIndividualConverter<TR> extends TargetRuleEngineConverter
{
  TR convert(OWLIndividual individual);
}
