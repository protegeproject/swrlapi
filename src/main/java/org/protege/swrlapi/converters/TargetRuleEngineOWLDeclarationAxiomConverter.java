
package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLNamedIndividualAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLDeclarationAxiomConverter extends TargetRuleEngineConverter
{
	void convert(OWLClassAdapter cls) throws TargetRuleEngineException;

	void convert(OWLNamedIndividualAdapter individal) throws TargetRuleEngineException;

	void convert(OWLObjectPropertyAdapter property) throws TargetRuleEngineException;

	void convert(OWLDataPropertyAdapter property) throws TargetRuleEngineException;

	void convert(OWLDatatypeAdapter datatype) throws TargetRuleEngineException;
}
