package org.swrlapi.ui.model;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.File;

public interface FileBackedOntologyModel extends OntologyModel
{
  /**
   * @param file A file containing an OWL ontology
   * @throws OWLOntologyCreationException If an error occurs during opening
   */
  void open(@NonNull File file) throws OWLOntologyCreationException;

  /**
   * Close ontology; create an empty ontology with no backing file
   *
   * @throws OWLOntologyCreationException If an error occurs during creation of an empty ontology
   */
  void close() throws OWLOntologyCreationException;

  /**
   * @throws OWLOntologyStorageException If an error occurs during saving
   */
  void save() throws OWLOntologyStorageException;

  /**
   * @param file The backing file
   * @throws OWLOntologyStorageException If a storage error occurs
   */
  void saveAs(@NonNull File file) throws OWLOntologyStorageException;

  /**
   * @return True if there is a file associated with the ontology
   */
  boolean hasBackingFile();
}
