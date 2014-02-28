package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLAnnotationPropertyAdapter;
import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLNamedIndividualAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLNamedObjectConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLClassAdapter cls) throws TargetRuleEngineException;

	TR convert(OWLNamedIndividualAdapter individual) throws TargetRuleEngineException;

	TR convert(OWLObjectPropertyAdapter property) throws TargetRuleEngineException;

	TR convert(OWLDataPropertyAdapter property) throws TargetRuleEngineException;
	
	TR convert(OWLAnnotationPropertyAdapter property) throws TargetRuleEngineException;

	TR convert(OWLDatatypeAdapter datatype) throws TargetRuleEngineException;
}
