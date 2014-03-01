package org.protege.swrlapi.converters;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLIndividual;

public interface TargetRuleEngineOWLIndividualConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLIndividual individual) throws TargetRuleEngineException;
}
