package org.protege.swrlapi.core;

import java.util.HashMap;
import java.util.Map;

import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * This class is used to keep track of class expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new class expressions as a result of inference (they do reason with supplied
 * class expressions, though) so rather than translating native rule engine representations of class expressions back to
 * the Portability API when returning inferred axioms, the original expressions supplied to the engine can be tracked
 * via an ID and recorded and retrieved using this class.
 * */
public class OWLClassExpressionResolver
{
	private final Map<String, OWLClassExpression> classExpressionMap;

	public OWLClassExpressionResolver()
	{
		this.classExpressionMap = new HashMap<String, OWLClassExpression>();
	}

	public void reset()
	{
		this.classExpressionMap.clear();
	}

	public void record(String classExpressionID, OWLClassExpression classExpression)
	{
		this.classExpressionMap.put(classExpressionID, classExpression);
	}

	public OWLClassExpression resolve(String classExpressionID) throws TargetRuleEngineException
	{
		if (this.classExpressionMap.containsKey(classExpressionID))
			return this.classExpressionMap.get(classExpressionID);
		else
			throw new TargetRuleEngineException("internal error: no class expression found with ID " + classExpressionID);
	}
}
