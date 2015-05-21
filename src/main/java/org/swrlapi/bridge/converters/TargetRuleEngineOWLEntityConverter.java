package org.swrlapi.bridge.converters;

import checkers.nullness.quals.NonNull;
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
  @NonNull TR convert(@NonNull OWLClass cls);

  @NonNull TR convert(@NonNull OWLNamedIndividual individual);

  @NonNull TR convert(@NonNull OWLObjectProperty property);

  @NonNull TR convert(@NonNull OWLDataProperty property);

  @NonNull TR convert(@NonNull OWLAnnotationProperty property);

  @NonNull TR convert(@NonNull OWLDatatype datatype);
}
