package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.FileBackedSQWRLQueryEngineModel;

import java.io.File;
import java.util.Optional;

public class DefaultFileBackedSQWRLQueryEngineModel extends DefaultSQWRLQueryEngineModel
    implements FileBackedSQWRLQueryEngineModel
{
  private Optional<File> file;

  public DefaultFileBackedSQWRLQueryEngineModel(@NonNull SQWRLQueryEngine queryEngine, Optional<File> file)
  {
    super(queryEngine);
    this.file = file;
  }

  @Override public void open(@NonNull File file) throws OWLOntologyCreationException
  {
    OWLOntology ontology = createOWLOntology(file);
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    this.file = Optional.of(file);

    resetOntologyChanged();
    updateModel(queryEngine);
  }

  @Override public void saveAs(@NonNull File file) throws OWLOntologyStorageException
  {
    this.file = Optional.of(file);

    saveOWLOntology(this.file.get());

    resetOntologyChanged();
  }

  @Override public void close() throws OWLOntologyCreationException
  {
    OWLOntology ontology = createOWLOntology();
    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

    this.file = Optional.empty();
    resetOntologyChanged();
    updateModel(queryEngine);
  }

  @Override public void save() throws OWLOntologyStorageException
  {
    if (this.file.isPresent())
      saveOWLOntology(this.file.get());

    resetOntologyChanged();
  }

  @Override public boolean hasBackingFile() { return file.isPresent(); }
}
