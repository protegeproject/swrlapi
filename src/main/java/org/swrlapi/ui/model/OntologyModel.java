package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;

public interface OntologyModel extends SWRLAPIModel
{
  /**
   * @return An OWL ontology
   */
  @NonNull OWLOntology getOWLOntology();

  /**
   * @return True if the ontology has changed since construction or the last call to {@link #resetOntologyChanged()}.
   */
  boolean hasOntologyChanged();

  /**
   * Reset the ontology changed status
   */
  void resetOntologyChanged();
}
