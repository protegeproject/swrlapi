package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLDataComplementOfAdapter;
import org.protege.owl.portability.model.OWLDataIntersectionOfAdapter;
import org.protege.owl.portability.model.OWLDataOneOfAdapter;
import org.protege.owl.portability.model.OWLDataUnionOfAdapter;
import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLDatatypeRestrictionAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLDataRangeConverter<TR> extends TargetRuleEngineConverter
	{
	TR convert(OWLDatatypeAdapter range) throws TargetRuleEngineException;
	
	TR convert(OWLDataOneOfAdapter range) throws TargetRuleEngineException;

	TR convert(OWLDataComplementOfAdapter range) throws TargetRuleEngineException;

	TR convert(OWLDataIntersectionOfAdapter range) throws TargetRuleEngineException;
	
	TR convert(OWLDataUnionOfAdapter range) throws TargetRuleEngineException;
	
	TR convert(OWLDatatypeRestrictionAdapter range) throws TargetRuleEngineException;
}	