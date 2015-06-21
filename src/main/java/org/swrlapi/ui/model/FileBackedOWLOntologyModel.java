package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.File;
import java.util.Optional;

/**
 * Describes a model that can be used to build an MVC-based GUI that uses a file-backed OWL ontology.
 */
public interface FileBackedOWLOntologyModel extends OWLOntologyModel
{
	/**
	 * @param file The backing file
	 */
	void changeBackingFile(@NonNull File file);

	void clearBackingFile();

	/**
	 *
	 * @throws OWLOntologyStorageException If an error occurs during saving
	 */
	void save() throws OWLOntologyStorageException;
}
