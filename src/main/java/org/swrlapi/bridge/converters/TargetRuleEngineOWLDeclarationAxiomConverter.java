package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public interface TargetRuleEngineOWLDeclarationAxiomConverter extends TargetRuleEngineConverter
{
  void convert(OWLClass cls);

  void convert(OWLNamedIndividual individual);

  void convert(OWLObjectProperty property);

  void convert(OWLDataProperty property);

  void convert(OWLDatatype datatype);
}
