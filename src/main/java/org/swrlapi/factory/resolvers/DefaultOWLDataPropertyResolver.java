package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLDataPropertyResolver implements OWLDataPropertyResolver
{
  private final @NonNull Map<String, OWLDataProperty> id2OWLDataProperty;
  @NonNull private final Map<OWLDataProperty, String> owlDataProperty2ID;

  private final @NonNull OWLDataFactory owlDataFactory;

  public DefaultOWLDataPropertyResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.id2OWLDataProperty = new HashMap<>();
    this.owlDataProperty2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;

    this.id2OWLDataProperty
      .put(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getPrefixedName(), owlDataFactory.getOWLTopDataProperty());
    this.owlDataProperty2ID
      .put(owlDataFactory.getOWLTopDataProperty(), OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getPrefixedName());

    this.id2OWLDataProperty
      .put(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getPrefixedName(), owlDataFactory.getOWLBottomDataProperty());
    this.owlDataProperty2ID
      .put(owlDataFactory.getOWLBottomDataProperty(), OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getPrefixedName());
  }

  @Override public void reset()
  {
    this.id2OWLDataProperty.clear();
    this.owlDataProperty2ID.clear();

    recordOWLDataProperty(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getPrefixedName(),
      getOWLDataFactory().getOWLTopDataProperty());
    recordOWLDataProperty(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getPrefixedName(),
      getOWLDataFactory().getOWLBottomDataProperty());
  }

  @Override public void recordOWLDataProperty(@NonNull String propertyID, @NonNull OWLDataProperty property)
  {
    this.id2OWLDataProperty.put(propertyID, property);
    this.owlDataProperty2ID.put(property, propertyID);
  }

  @Override public boolean recordsOWLDataProperty(@NonNull OWLDataProperty property)
  {
    return this.owlDataProperty2ID.containsKey(property);
  }

  @Override @NonNull public String resolveOWLDataProperty(@NonNull OWLDataProperty property)
  {
    if (this.owlDataProperty2ID.containsKey(property))
      return this.owlDataProperty2ID.get(property);
    else
      throw new IllegalArgumentException("no ID found for property " + property);
  }

  @Override @NonNull public OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID)
  {
    if (this.id2OWLDataProperty.containsKey(propertyID))
      return this.id2OWLDataProperty.get(propertyID);
    else
      throw new IllegalArgumentException("no property found with ID " + propertyID);
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return this.owlDataFactory;
  }
}
