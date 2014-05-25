package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLEntityConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLClass cls) throws TargetRuleEngineException;

	TR convert(OWLNamedIndividual individual) throws TargetRuleEngineException;

	TR convert(OWLObjectProperty property) throws TargetRuleEngineException;

	TR convert(OWLDataProperty property) throws TargetRuleEngineException;

	TR convert(OWLAnnotationProperty property) throws TargetRuleEngineException;

	TR convert(OWLDatatype datatype) throws TargetRuleEngineException;
}
