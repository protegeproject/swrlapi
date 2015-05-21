package org.swrlapi.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.HashMap;
import java.util.Map;

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
  @NonNull private final Map<String, OWLClassExpression> id2OWLClassExpression;
  @NonNull private final Map<OWLClassExpression, String> owlClassExpression2ID;
  @NonNull private final OWLDataFactory owlDataFactory;

  public OWLClassExpressionResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.id2OWLClassExpression = new HashMap<>();
    this.owlClassExpression2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;

    this.id2OWLClassExpression.put(OWLRDFVocabulary.OWL_THING.getPrefixedName(), owlDataFactory.getOWLThing());
    this.owlClassExpression2ID.put(owlDataFactory.getOWLThing(), OWLRDFVocabulary.OWL_THING.getPrefixedName());

    this.id2OWLClassExpression.put(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), owlDataFactory.getOWLNothing());
    this.owlClassExpression2ID.put(owlDataFactory.getOWLNothing(), OWLRDFVocabulary.OWL_NOTHING.getPrefixedName());
  }

  public void reset()
  {
    this.id2OWLClassExpression.clear();
    this.owlClassExpression2ID.clear();
    record(OWLRDFVocabulary.OWL_THING.getPrefixedName(), getOWLDataFactory().getOWLThing());
    record(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), getOWLDataFactory().getOWLNothing());
  }

  public void record(@NonNull String classExpressionID, @NonNull OWLClassExpression classExpression)
  {
    this.id2OWLClassExpression.put(classExpressionID, classExpression);
    this.owlClassExpression2ID.put(classExpression, classExpressionID);
  }

  public boolean records(@NonNull OWLClassExpression owlClassExpression)
  {
    return this.owlClassExpression2ID.containsKey(owlClassExpression);
  }

  /**
   *
   * @param owlClassExpression A class expression to resolve
   * @return The ID of the resolved class expression
   * @throws IllegalArgumentException If the class expression cannot be resolved
   */
  @NonNull public String resolve(@NonNull OWLClassExpression owlClassExpression)
  {
    if (this.owlClassExpression2ID.containsKey(owlClassExpression))
     return this.owlClassExpression2ID.get(owlClassExpression);
    else
      throw new IllegalArgumentException("no ID found for class expression " + owlClassExpression);
  }

  /**
   *
   * @param classExpressionID A class expression ID
   * @return The resolved class expression
   * @throws IllegalArgumentException If the class expression ID cannot be resolved
   */
  @NonNull public OWLClassExpression resolve(@NonNull String classExpressionID)
  {
    if (this.id2OWLClassExpression.containsKey(classExpressionID))
      return this.id2OWLClassExpression.get(classExpressionID);
    else
      throw new IllegalArgumentException("no class expression found with ID " + classExpressionID);
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return this.owlDataFactory;
  }
}
