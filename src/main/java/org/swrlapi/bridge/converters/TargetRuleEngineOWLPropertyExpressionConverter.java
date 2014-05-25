package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLPropertyExpressionConverter<PE> extends TargetRuleEngineConverter
{
	PE convert(OWLObjectPropertyExpression propertyExpression) throws TargetRuleEngineException;

	PE convert(OWLDataPropertyExpression propertyExpression) throws TargetRuleEngineException;
}
