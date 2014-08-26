package org.swrlapi.core.resolvers;

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
 * @see org.swrlapi.core.resolvers.OWLObjectPropertyExpressionResolver
 */
public class OWLDataPropertyExpressionResolver
{
	private final Map<String, OWLDataPropertyExpression> id2OWLPropertyExpression;
	private final Map<OWLDataPropertyExpression, String> owlPropertyExpression2ID;

	public OWLDataPropertyExpressionResolver()
	{
		this.id2OWLPropertyExpression = new HashMap<String, OWLDataPropertyExpression>();
		this.owlPropertyExpression2ID = new HashMap<OWLDataPropertyExpression, String>();
	}

	public void reset()
	{
		this.id2OWLPropertyExpression.clear();
		this.owlPropertyExpression2ID.clear();
	}

	public void record(String propertyExpressionID, OWLDataPropertyExpression propertyExpression)
	{
		this.id2OWLPropertyExpression.put(propertyExpressionID, propertyExpression);
		this.owlPropertyExpression2ID.put(propertyExpression, propertyExpressionID);
	}

	public boolean records(OWLDataPropertyExpression propertyExpression)
	{
		return this.owlPropertyExpression2ID.containsKey(propertyExpression);
	}

	public String resolve(OWLDataPropertyExpression propertyExpression)
	{
		return this.owlPropertyExpression2ID.get(propertyExpression);
	}

	public OWLDataPropertyExpression resolve(String propertyExpressionID)
	{
		if (this.id2OWLPropertyExpression.containsKey(propertyExpressionID))
			return this.id2OWLPropertyExpression.get(propertyExpressionID);
		else
			throw new RuntimeException("internal error: no data property expression found with ID " + propertyExpressionID);
	}
}
