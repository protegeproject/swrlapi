
package org.swrlapi.core;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class is used to keep track of OWL object property expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new property expressions as a result of inference (they do reason with supplied
 * property expressions, though) so rather than translating native rule engine representations of property expressions
 * back to the OWLAPI when returning inferred axioms, the original expressions supplied to the engine can be tracked via
 * an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 * @see org.swrlapi.core.OWLDataPropertyExpressionResolver
 */
public class OWLObjectPropertyExpressionResolver
{
	private final Map<String, OWLObjectPropertyExpression> propertyExpressionMap;

	public OWLObjectPropertyExpressionResolver()
	{
		this.propertyExpressionMap = new HashMap<String, OWLObjectPropertyExpression>();
	}

	public void reset()
	{
		this.propertyExpressionMap.clear();
	}

	public void record(String propertyExpressionID, OWLObjectPropertyExpression propertyExpression)
	{
		this.propertyExpressionMap.put(propertyExpressionID, propertyExpression);
	}

	public OWLObjectPropertyExpression resolve(String propertyExpressionID) throws TargetRuleEngineException
	{
		if (this.propertyExpressionMap.containsKey(propertyExpressionID))
			return this.propertyExpressionMap.get(propertyExpressionID);
		else
			throw new TargetRuleEngineException("internal error: no object property expression found with ID "
					+ propertyExpressionID);
	}
}
