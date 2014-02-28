package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLIndividualAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLIndividualConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLIndividualAdapter individual) throws TargetRuleEngineException;
}
