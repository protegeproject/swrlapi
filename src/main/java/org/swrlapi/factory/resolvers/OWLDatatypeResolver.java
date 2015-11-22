package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;

interface OWLDatatypeResolver
{
  void reset();

  void recordOWLDatatype(@NonNull String datatypeID, @NonNull OWLDatatype datatype);

  boolean recordsOWLDatatype(@NonNull OWLDatatype datatype);

  /**
   * @param datatype A class to resolve
   * @return The ID of the resolved class
   * @throws IllegalArgumentException If the class cannot be resolved
   */
  @NonNull String resolveOWLDatatype(@NonNull OWLDatatype datatype);

  /**
   * @param datatypeID A datatype ID
   * @return The resolved datatype
   * @throws IllegalArgumentException If the datatype ID cannot be resolved
   */
  @NonNull OWLDatatype resolveOWLDatatype(@NonNull String datatypeID);
}
