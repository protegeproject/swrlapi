package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Describes a model that can be used to build an MVC-based GUI with an OWL ontology and an associated SWRL rule engine.
 *
 * @see org.semanticweb.owlapi.model.OWLOntology
 */
public interface OWLOntologyModel extends SWRLAPIModel
{
	/**
	 * @return The underlying OWL ontology
	 */
	@NonNull OWLOntology getOWLOntology();

	/**
	 *
	 * @return An associated SWRL rule engine model
	 */
	@NonNull SWRLRuleEngineModel getSWRLRuleEngineModel();

	/**
	 * @return True if the ontology has changed since construction or the last call to {@link #resetOntologyChanged()}.
	 */
	boolean hasOntologyChanged();

	/**
	 * Reset the ontology changed status
	 */
	void resetOntologyChanged();
}

