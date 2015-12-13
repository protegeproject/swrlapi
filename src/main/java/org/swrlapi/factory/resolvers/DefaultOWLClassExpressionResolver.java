package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLClassExpressionResolver implements OWLClassExpressionResolver
{
  @NonNull private final Map<@NonNull String, @NonNull OWLClassExpression> id2OWLClassExpression;
  @NonNull private final Map<@NonNull OWLClassExpression, @NonNull String> owlClassExpression2ID;
  @NonNull private final OWLDataFactory owlDataFactory;

  public DefaultOWLClassExpressionResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.id2OWLClassExpression = new HashMap<>();
    this.owlClassExpression2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;

    this.id2OWLClassExpression.put(OWLRDFVocabulary.OWL_THING.getPrefixedName(), owlDataFactory.getOWLThing());
    this.owlClassExpression2ID.put(owlDataFactory.getOWLThing(), OWLRDFVocabulary.OWL_THING.getPrefixedName());

    this.id2OWLClassExpression.put(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), owlDataFactory.getOWLNothing());
    this.owlClassExpression2ID.put(owlDataFactory.getOWLNothing(), OWLRDFVocabulary.OWL_NOTHING.getPrefixedName());
  }

  @Override public void reset()
  {
    this.id2OWLClassExpression.clear();
    this.owlClassExpression2ID.clear();

    recordOWLClassExpression(OWLRDFVocabulary.OWL_THING.getPrefixedName(), getOWLDataFactory().getOWLThing());
    recordOWLClassExpression(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), getOWLDataFactory().getOWLNothing());
  }

  @Override public void recordOWLClassExpression(@NonNull String classExpressionID,
      @NonNull OWLClassExpression classExpression)
  {
    this.id2OWLClassExpression.put(classExpressionID, classExpression);
    this.owlClassExpression2ID.put(classExpression, classExpressionID);
  }

  @Override public boolean recordsOWLClassExpression(@NonNull OWLClassExpression owlClassExpression)
  {
    return this.owlClassExpression2ID.containsKey(owlClassExpression);
  }

  @Override @NonNull public String resolveOWLClassExpression(@NonNull OWLClassExpression owlClassExpression)
  {
    if (this.owlClassExpression2ID.containsKey(owlClassExpression))
     return this.owlClassExpression2ID.get(owlClassExpression);
    else
      throw new IllegalArgumentException("no ID found for class expression " + owlClassExpression);
  }

  @Override @NonNull public OWLClassExpression resolveOWLClassExpression(@NonNull String classExpressionID)
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
