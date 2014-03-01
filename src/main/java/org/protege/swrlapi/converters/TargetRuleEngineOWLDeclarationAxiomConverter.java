package org.protege.swrlapi.converters;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public interface TargetRuleEngineOWLDeclarationAxiomConverter extends TargetRuleEngineConverter
{
	void convert(OWLClass cls) throws TargetRuleEngineException;

	void convert(OWLNamedIndividual individal) throws TargetRuleEngineException;

	void convert(OWLObjectProperty property) throws TargetRuleEngineException;

	void convert(OWLDataProperty property) throws TargetRuleEngineException;

	void convert(OWLDatatype datatype) throws TargetRuleEngineException;
}
