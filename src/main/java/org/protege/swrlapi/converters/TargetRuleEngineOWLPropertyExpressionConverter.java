package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLDataPropertyExpressionAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyExpressionAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

public interface TargetRuleEngineOWLPropertyExpressionConverter<PE> extends TargetRuleEngineConverter
{	
	PE convert(OWLObjectPropertyExpressionAdapter propertyExpression) throws TargetRuleEngineException;
	
	PE convert(OWLDataPropertyExpressionAdapter propertyExpression) throws TargetRuleEngineException;
}
