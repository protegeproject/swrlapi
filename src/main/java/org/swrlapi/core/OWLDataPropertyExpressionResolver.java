package org.swrlapi.core;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class is used to keep track of OWL data property expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new property expressions as a result of inference (they do reason with supplied
 * property expressions, though) so rather than translating native rule engine representations of property expressions
 * back to the OWLAPI when returning inferred axioms, the original expressions supplied to the engine can be tracked via
 * an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 * @see org.swrlapi.core.OWLObjectPropertyExpressionResolver
 */
public class OWLDataPropertyExpressionResolver
{
	private final Map<String, OWLDataPropertyExpression> propertyExpressionMap;

	public OWLDataPropertyExpressionResolver()
	{
		this.propertyExpressionMap = new HashMap<String, OWLDataPropertyExpression>();
	}

	public void reset()
	{
		this.propertyExpressionMap.clear();
	}

	public void record(String propertyExpressionID, OWLDataPropertyExpression propertyExpression)
	{
		this.propertyExpressionMap.put(propertyExpressionID, propertyExpression);
	}

	public OWLDataPropertyExpression resolve(String propertyExpressionID) throws TargetRuleEngineException
	{
		if (this.propertyExpressionMap.containsKey(propertyExpressionID))
			return this.propertyExpressionMap.get(propertyExpressionID);
		else
			throw new TargetRuleEngineException("internal error: no data property expression found with ID "
					+ propertyExpressionID);
	}
}
