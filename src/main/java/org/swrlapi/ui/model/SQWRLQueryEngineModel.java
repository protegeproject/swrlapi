package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

public interface SQWRLQueryEngineModel extends SWRLRuleEngineModel
{
  /**
   * Update the model's ontology and query engine
   *
   * @param ontology A new OWL ontology
   * @param queryEngine A new SQWRL query engine
   */
  void updateModel(@NonNull OWLOntology ontology, @NonNull SQWRLQueryEngine queryEngine);

  /**
   * @return A SQWRL query engine
   */
  @NonNull SQWRLQueryEngine getSQWRLQueryEngine();
}
