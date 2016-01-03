package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;

import java.io.File;
import java.util.Optional;

public class DefaultFileBackedSWRLRuleEngineModel extends DefaultSWRLRuleEngineModel
    implements FileBackedSWRLRuleEngineModel
{
  private Optional<@NonNull File> file;

  public DefaultFileBackedSWRLRuleEngineModel(@NonNull SWRLRuleEngine ruleEngine, Optional<File> file)
  {
    super(ruleEngine);
    this.file = file;
  }

  @Override public void open(@NonNull File file) throws OWLOntologyCreationException
  {
    OWLOntology ontology = createOWLOntology(file);
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

    this.file = Optional.of(file);
    updateModel(ruleEngine);
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
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

    this.file = Optional.<@NonNull File>empty();

    updateModel(ruleEngine);
  }

  @Override public void save() throws OWLOntologyStorageException
  {
    if (this.file.isPresent())
      saveOWLOntology(this.file.get());

    resetOntologyChanged();
  }

  @Override public boolean hasBackingFile() { return file.isPresent(); }
}
