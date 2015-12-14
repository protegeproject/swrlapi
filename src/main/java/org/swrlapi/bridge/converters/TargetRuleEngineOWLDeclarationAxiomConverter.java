package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
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
  void convert(@NonNull OWLClass cls);

  void convert(@NonNull OWLNamedIndividual individual);

  void convert(@NonNull OWLObjectProperty property);

  void convert(@NonNull OWLDataProperty property);

  void convert(@NonNull OWLDatatype datatype);
}
