package org.swrlapi.ui.model;

public interface OntologyModel extends SWRLAPIModel
{
  /**
   * @return True if the ontology has changed since construction or the last call to {@link #resetOntologyChanged()}.
   */
  boolean hasOntologyChanged();

  /**
   * Reset the ontology changed status
   */
  void resetOntologyChanged();
}
