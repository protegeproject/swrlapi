package org.swrlapi.ui.model;

import java.io.File;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Describes a model that can be used to build a MVC-based GUI that uses a file-backed OWL ontology.
 *
 * @see org.semanticweb.owlapi.model.OWLOntology
 */
public interface FileBackedOWLOntologyModel
{
	/**
	 * @return The underlying OWL ontology
	 */
	OWLOntology getOWLOntology();

	/**
	 * @return The file containing the OWL ontology
	 */
	File getBackingFile();

	/**
	 *
	 * @throws OWLOntologyStorageException If an error occurs during saving
	 */
	void save() throws OWLOntologyStorageException;

	/**
	 *
	 * @param file The file to save; updates the current backing file if save is successful
	 * @throws OWLOntologyStorageException If an error occurs during saving
	 */
	void saveAs(File file) throws OWLOntologyStorageException;

	/**
	 * @return True if the ontology has changed since construction or the last call to {@link #resetOntologyChanged()}.
	 */
	boolean hasOntologyChanged();

	/**
	 * Reset the ontology changed status
	 */
	void resetOntologyChanged();
}
