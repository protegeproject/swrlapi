package org.swrlapi.bridge.converters;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public interface TargetRuleEngineOWLPropertyExpressionConverter<PE> extends TargetRuleEngineConverter
{
  @NonNull PE convert(@NonNull OWLObjectPropertyExpression propertyExpression);

  @NonNull PE convert(@NonNull OWLDataPropertyExpression propertyExpression);
}
