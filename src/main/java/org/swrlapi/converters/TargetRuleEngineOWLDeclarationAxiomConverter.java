package org.swrlapi.converters;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLDeclarationAxiomConverter extends TargetRuleEngineConverter
{
	void convert(OWLClass cls) throws TargetRuleEngineException;

	void convert(OWLNamedIndividual individal) throws TargetRuleEngineException;

	void convert(OWLObjectProperty property) throws TargetRuleEngineException;

	void convert(OWLDataProperty property) throws TargetRuleEngineException;

	void convert(OWLDatatype datatype) throws TargetRuleEngineException;
}
