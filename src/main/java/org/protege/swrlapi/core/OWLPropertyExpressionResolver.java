package org.protege.swrlapi.core;

import java.util.HashMap;
import java.util.Map;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * This class is used to keep track of property expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new property expressions as a result of inference (they do reason with supplied
 * property expressions, though) so rather than translating native rule engine representations of property expressions
 * back to the Portability API when returning inferred axioms, the original expressions supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 * */
public class OWLPropertyExpressionResolver
{
	private final Map<String, OWLPropertyExpression<?, ?>> propertyExpressionMap;

	public OWLPropertyExpressionResolver()
	{
		this.propertyExpressionMap = new HashMap<String, OWLPropertyExpression<?, ?>>();
	}

	public void reset()
	{
		this.propertyExpressionMap.clear();
	}

	public void record(String propertyExpressionID, OWLPropertyExpression<?, ?> propertyExpression)
	{
		this.propertyExpressionMap.put(propertyExpressionID, propertyExpression);
	}

	public OWLPropertyExpression<?, ?> resolve(String propertyExpressionID) throws TargetRuleEngineException
	{
		if (this.propertyExpressionMap.containsKey(propertyExpressionID))
			return this.propertyExpressionMap.get(propertyExpressionID);
		else
			throw new TargetRuleEngineException("internal error: no property expression found with ID "
					+ propertyExpressionID);
	}
}
