package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * @see org.semanticweb.owlapi.model.OWLEntity
 */
public interface TargetRuleEngineOWLEntityConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLClass cls);

	TR convert(OWLNamedIndividual individual);

	TR convert(OWLObjectProperty property);

	TR convert(OWLDataProperty property);

	TR convert(OWLAnnotationProperty property);

	TR convert(OWLDatatype datatype);
}
