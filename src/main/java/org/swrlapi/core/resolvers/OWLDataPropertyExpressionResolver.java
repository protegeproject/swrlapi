package org.swrlapi.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to keep track of OWL data property expressions, typically by a rule engine implementation. OWL 2
 * RL-based reasoners, for example, do not create new property expressions as a result of inference (they do reason with
 * supplied property expressions, though) so rather than translating native rule engine representations of property
 * expressions back to the OWLAPI when returning inferred axioms, the original expressions supplied to the engine can be
 * tracked via an ID and recorded and retrieved using this class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 * @see org.swrlapi.core.resolvers.OWLObjectPropertyExpressionResolver
 */
public class OWLDataPropertyExpressionResolver
{
  @NonNull private final Map<String, OWLDataPropertyExpression> id2OWLPropertyExpression;
  @NonNull private final Map<OWLDataPropertyExpression, String> owlPropertyExpression2ID;

  public OWLDataPropertyExpressionResolver()
  {
    this.id2OWLPropertyExpression = new HashMap<>();
    this.owlPropertyExpression2ID = new HashMap<>();
  }

  public void reset()
  {
    this.id2OWLPropertyExpression.clear();
    this.owlPropertyExpression2ID.clear();
  }

  public void record(@NonNull String propertyExpressionID, @NonNull OWLDataPropertyExpression propertyExpression)
  {
    this.id2OWLPropertyExpression.put(propertyExpressionID, propertyExpression);
    this.owlPropertyExpression2ID.put(propertyExpression, propertyExpressionID);
  }

  public boolean records(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    return this.owlPropertyExpression2ID.containsKey(propertyExpression);
  }

  @NonNull public String resolve(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    if (this.owlPropertyExpression2ID.containsKey(propertyExpression))
      return this.owlPropertyExpression2ID.get(propertyExpression);
    else
      throw new IllegalArgumentException("no ID found for data property expression " + propertyExpression);
  }

  @NonNull public OWLDataPropertyExpression resolve(@NonNull String propertyExpressionID)
  {
    if (this.id2OWLPropertyExpression.containsKey(propertyExpressionID))
      return this.id2OWLPropertyExpression.get(propertyExpressionID);
    else
      throw new IllegalArgumentException("no data property expression found with ID " + propertyExpressionID);
  }
}
