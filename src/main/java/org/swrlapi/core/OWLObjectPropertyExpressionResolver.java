
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
	private final Map<String, OWLObjectPropertyExpression> id2OWLPropertyExpression;
	private final Map<OWLObjectPropertyExpression, String> owlPropertyExpression2ID;

	public OWLObjectPropertyExpressionResolver()
	{
		this.id2OWLPropertyExpression = new HashMap<String, OWLObjectPropertyExpression>();
		this.owlPropertyExpression2ID = new HashMap<OWLObjectPropertyExpression, String>();
	}

	public void reset()
	{
		this.id2OWLPropertyExpression.clear();
		this.owlPropertyExpression2ID.clear();
	}

	public void record(String propertyExpressionID, OWLObjectPropertyExpression propertyExpression)
	{
		this.id2OWLPropertyExpression.put(propertyExpressionID, propertyExpression);
		this.owlPropertyExpression2ID.put(propertyExpression, propertyExpressionID);
	}

	public boolean records(OWLObjectPropertyExpression propertyExpression)
	{
		return this.owlPropertyExpression2ID.containsKey(propertyExpression);
	}

	public String resolve(OWLObjectPropertyExpression propertyExpression)
	{
		return this.owlPropertyExpression2ID.get(propertyExpression);
	}

	public OWLObjectPropertyExpression resolve(String propertyExpressionID) throws TargetRuleEngineException
	{
		if (this.id2OWLPropertyExpression.containsKey(propertyExpressionID))
			return this.id2OWLPropertyExpression.get(propertyExpressionID);
		else
			throw new TargetRuleEngineException("internal error: no object property expression found with ID "
					+ propertyExpressionID);
	}
}
