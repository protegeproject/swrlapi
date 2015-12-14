package org.swrlapi.factory.resolvers;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;

import java.util.HashMap;
import java.util.Map;

class DefaultOWLDatatypeResolver implements OWLDatatypeResolver
{
  @NonNull private final Map<@NonNull String, @NonNull OWLDatatype> id2OWLDatatype;
  @NonNull private final Map<@NonNull OWLDatatype, @NonNull String> owlDatatype2ID;

  private final @NonNull OWLDataFactory owlDataFactory;

  public DefaultOWLDatatypeResolver(@NonNull OWLDataFactory owlDataFactory)
  {
    this.id2OWLDatatype = new HashMap<>();
    this.owlDatatype2ID = new HashMap<>();
    this.owlDataFactory = owlDataFactory;
  }

  @Override public void reset()
  {
    this.id2OWLDatatype.clear();
    this.owlDatatype2ID.clear();
  }

  @Override public void recordOWLDatatype(@NonNull String datatypeID, @NonNull OWLDatatype datatype)
  {
    this.id2OWLDatatype.put(datatypeID, datatype);
    this.owlDatatype2ID.put(datatype, datatypeID);
  }

  @Override public boolean recordsOWLDatatype(@NonNull OWLDatatype datatype)
  {
    return this.owlDatatype2ID.containsKey(datatype);
  }

  @Override @NonNull public String resolveOWLDatatype(@NonNull OWLDatatype datatype)
  {
    if (this.owlDatatype2ID.containsKey(datatype))
      return this.owlDatatype2ID.get(datatype);
    else
      throw new IllegalArgumentException("no ID found for datatype " + datatype);
  }

  @Override @NonNull public OWLDatatype resolveOWLDatatype(@NonNull String datatypeID)
  {
    if (this.id2OWLDatatype.containsKey(datatypeID))
      return this.id2OWLDatatype.get(datatypeID);
    else
      throw new IllegalArgumentException("no property found with ID " + datatypeID);
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return this.owlDataFactory;
  }
}
