package org.swrlapi.core;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class is used to keep track of class expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new class expressions as a result of inference (they do reason with supplied
 * class expressions, though) so rather than translating native rule engine representations of class expressions back to
 * the OWLAPI representation returning inferred axioms, the original expressions supplied to the engine can be tracked
 * via an ID and recorded and retrieved using this class.
 * */
public class OWLClassExpressionResolver
{
	private final Map<String, OWLClassExpression> classExpressionMap;

	private final OWLDataFactory owlDataFactory;

	public OWLClassExpressionResolver(OWLDataFactory owlDataFactory)
	{
		this.classExpressionMap = new HashMap<String, OWLClassExpression>();
		this.owlDataFactory = owlDataFactory;
		reset();
	}

	public void reset()
	{
		this.classExpressionMap.clear();
		recordOWLClassExpression(OWLRDFVocabulary.OWL_THING.getPrefixedName(), getOWLDataFactory().getOWLThing());
		recordOWLClassExpression(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), getOWLDataFactory().getOWLNothing());
	}

	public void recordOWLClassExpression(String classExpressionID, OWLClassExpression classExpression)
	{
		this.classExpressionMap.put(classExpressionID, classExpression);
	}

	public OWLClassExpression resolveOWLClassExpression(String classExpressionID) throws TargetRuleEngineException
	{
		if (this.classExpressionMap.containsKey(classExpressionID))
			return this.classExpressionMap.get(classExpressionID);
		else
			throw new TargetRuleEngineException("internal error: no class expression found with ID " + classExpressionID);
	}

	private OWLDataFactory getOWLDataFactory()
	{
		return this.owlDataFactory;
	}
}
