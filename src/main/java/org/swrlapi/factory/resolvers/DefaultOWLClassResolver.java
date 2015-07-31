package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLClassResolver implements OWLClassResolver
{
  @NonNull private final Map<String, OWLClass> id2OWLClass;
  @NonNull private final Map<OWLClass, String> owlClass2ID;

  @NonNull private final OWLDataFactory owlDataFactory;

  public DefaultOWLClassResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.id2OWLClass = new HashMap<>();
    this.owlClass2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;

    this.id2OWLClass.put(OWLRDFVocabulary.OWL_THING.getPrefixedName(), owlDataFactory.getOWLThing());
    this.owlClass2ID.put(owlDataFactory.getOWLThing(), OWLRDFVocabulary.OWL_THING.getPrefixedName());

    this.id2OWLClass.put(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), owlDataFactory.getOWLNothing());
    this.owlClass2ID.put(owlDataFactory.getOWLNothing(), OWLRDFVocabulary.OWL_NOTHING.getPrefixedName());
  }

  @Override public void reset()
  {
    this.id2OWLClass.clear();
    this.owlClass2ID.clear();

    recordOWLClass(OWLRDFVocabulary.OWL_THING.getPrefixedName(), getOWLDataFactory().getOWLThing());
    recordOWLClass(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName(), getOWLDataFactory().getOWLNothing());
  }

  @Override public void recordOWLClass(@NonNull String classID, @NonNull OWLClass cls)
  {
    this.id2OWLClass.put(classID, cls);
    this.owlClass2ID.put(cls ,classID);
  }

  @Override public boolean recordsOWLClass(@NonNull OWLClass cls)
  {
    return this.owlClass2ID.containsKey(cls);
  }

  @Override @NonNull public String resolveOWLClass(@NonNull OWLClass cls)
  {
    if (this.owlClass2ID.containsKey(cls))
      return this.owlClass2ID.get(cls);
    else
      throw new IllegalArgumentException("no ID found for class " + cls);
  }

  @Override @NonNull public OWLClass resolveOWLClass(@NonNull String classID)
  {
    if (this.id2OWLClass.containsKey(classID))
      return this.id2OWLClass.get(classID);
    else
      throw new IllegalArgumentException("no class found with ID " + classID);
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return this.owlDataFactory;
  }
}
