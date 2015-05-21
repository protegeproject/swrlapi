package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.File;

/**
 * Describes a model that can be used to build an MVC-based GUI that uses a file-backed OWL ontology.
 */
public interface FileBackedOWLOntologyModel extends OWLOntologyModel
{
	/**
	 * @return The file containing the OWL ontology
	 */
	@NonNull File getBackingFile();

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
	void saveAs(@NonNull File file) throws OWLOntologyStorageException;
}
