package org.swrlapi.sqwrl.values;

import org.semanticweb.owlapi.model.IRI;

/**
 * Represents an OWL entity result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 * 
 * @see org.swrlapi.sqwrl.SQWRLResult
 */
public interface SQWRLEntityResultValue extends SQWRLResultValue, Comparable<SQWRLEntityResultValue>
{
  /**
   * @return The IRI of the entity
   */
  IRI getIRI();

  /**
   * @return The prefixed names of the entity
   */
  String getPrefixedName();

  /**
   * @return The short name of the entity
   */
  String getShortName();
}