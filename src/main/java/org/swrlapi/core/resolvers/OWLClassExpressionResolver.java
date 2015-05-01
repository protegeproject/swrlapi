package org.swrlapi.core.resolvers;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.exceptions.SWRLAPIInternalException;

/**
 * This class is used to keep track of class expressions, typically by a rule engine implementation. OWL 2 RL-based
 * reasoners, for example, do not create new class expressions as a result of inference (they do reason with supplied
 * class expressions, though) so rather than translating native rule engine representations of class expressions back to
 * the OWLAPI representation when returning inferred axioms, the original expressions supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 */
public class OWLClassExpressionResolver
{
  private final Map<String, OWLClassExpression> id2OWLClassExpression;
  private final Map<OWLClassExpression, String> owlClassExpression2ID;

  private final OWLDataFactory owlDataFactory;

  public OWLClassExpressionResolver(OWLDataFactory owlDataFactory)
  {
    this.id2OWLClassExpression = new HashMap<>();
    this.owlClassExpression2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;
    reset();
  }

  public void reset()
  {
    this.id2OWLClassExpression.clear();
    this.owlClassExpression2ID.clear();
    record(OWLRDFVocabulary.OWL_THING.getPrefixedName(), getOWLDataFactory().getOWLThing());
    record(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), getOWLDataFactory().getOWLNothing());
  }

  public void record(String classExpressionID, OWLClassExpression classExpression)
  {
    this.id2OWLClassExpression.put(classExpressionID, classExpression);
    this.owlClassExpression2ID.put(classExpression, classExpressionID);
  }

  public boolean records(OWLClassExpression owlClassExpression)
  {
    return this.owlClassExpression2ID.containsKey(owlClassExpression);
  }

  public String resolve(OWLClassExpression owlClassExpression)
  {
    return this.owlClassExpression2ID.get(owlClassExpression);
  }

  public OWLClassExpression resolve(String classExpressionID)
  {
    if (this.id2OWLClassExpression.containsKey(classExpressionID))
      return this.id2OWLClassExpression.get(classExpressionID);
    else
      throw new SWRLAPIInternalException("no class expression found with ID " + classExpressionID);
  }

  private OWLDataFactory getOWLDataFactory()
  {
    return this.owlDataFactory;
  }
}
