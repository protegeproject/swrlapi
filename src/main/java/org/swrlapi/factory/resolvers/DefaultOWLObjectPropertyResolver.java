package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLObjectPropertyResolver implements OWLObjectPropertyResolver
{
  @NonNull private final Map<@NonNull String, OWLObjectProperty> id2OWLObjectProperty;
  @NonNull private final Map<OWLObjectProperty, String> owlObjectProperty2ID;

  @NonNull private final OWLDataFactory owlDataFactory;

  public DefaultOWLObjectPropertyResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.id2OWLObjectProperty = new HashMap<>();
    this.owlObjectProperty2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;

    this.id2OWLObjectProperty
      .put(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getPrefixedName(), owlDataFactory.getOWLTopObjectProperty());
    this.owlObjectProperty2ID
      .put(owlDataFactory.getOWLTopObjectProperty(), OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getPrefixedName());

    this.id2OWLObjectProperty
      .put(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getPrefixedName(), owlDataFactory.getOWLBottomObjectProperty());
    this.owlObjectProperty2ID
      .put(owlDataFactory.getOWLBottomObjectProperty(), OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getPrefixedName());
  }

  @Override public void reset()
  {
    this.id2OWLObjectProperty.clear();
    this.owlObjectProperty2ID.clear();

    recordOWLObjectProperty(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getPrefixedName(),
      getOWLDataFactory().getOWLTopObjectProperty());
    recordOWLObjectProperty(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getPrefixedName(),
      getOWLDataFactory().getOWLBottomObjectProperty());
  }

  @Override public void recordOWLObjectProperty(@NonNull String propertyID, @NonNull OWLObjectProperty property)
  {
    this.id2OWLObjectProperty.put(propertyID, property);
    this.owlObjectProperty2ID.put(property, propertyID);
  }

  @Override public boolean recordsOWLObjectProperty(@NonNull OWLObjectProperty property)
  {
    return this.owlObjectProperty2ID.containsKey(property);
  }

  @Override @NonNull public String resolveOWLObjectProperty(@NonNull OWLObjectProperty property)
  {
    if (this.owlObjectProperty2ID.containsKey(property))
      return this.owlObjectProperty2ID.get(property);
    else
      throw new IllegalArgumentException("no ID found for property " + property);
  }

  @Override @NonNull public OWLObjectProperty resolveOWLObjectProperty(@NonNull String propertyID)
  {
    if (this.id2OWLObjectProperty.containsKey(propertyID))
      return this.id2OWLObjectProperty.get(propertyID);
    else
      throw new IllegalArgumentException("no property found with ID " + propertyID);
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return this.owlDataFactory;
  }
}
