package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public interface TargetRuleEngineOWLPropertyExpressionConverter<PE> extends TargetRuleEngineConverter
{
  PE convert(OWLObjectPropertyExpression propertyExpression);

  PE convert(OWLDataPropertyExpression propertyExpression);
}
