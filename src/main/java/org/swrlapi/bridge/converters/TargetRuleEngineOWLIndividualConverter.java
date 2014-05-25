package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLIndividualConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLIndividual individual) throws TargetRuleEngineException;
}
