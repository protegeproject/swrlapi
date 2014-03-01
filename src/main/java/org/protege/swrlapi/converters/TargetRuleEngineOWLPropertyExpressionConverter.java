package org.protege.swrlapi.converters;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public interface TargetRuleEngineOWLPropertyExpressionConverter<PE> extends TargetRuleEngineConverter
{
	PE convert(OWLObjectPropertyExpression propertyExpression) throws TargetRuleEngineException;

	PE convert(OWLDataPropertyExpression propertyExpression) throws TargetRuleEngineException;
}
