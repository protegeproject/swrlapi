package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;

/**
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface TargetRuleEngineOWLDataRangeConverter<TR> extends TargetRuleEngineConverter
{
  @NonNull TR convert(@NonNull OWLDatatype range);

  @NonNull TR convert(@NonNull OWLDataOneOf range);

  @NonNull TR convert(@NonNull OWLDataComplementOf range);

  @NonNull TR convert(@NonNull OWLDataIntersectionOf range);

  @NonNull TR convert(@NonNull OWLDataUnionOf range);

  @NonNull TR convert(@NonNull OWLDatatypeRestriction range);
}