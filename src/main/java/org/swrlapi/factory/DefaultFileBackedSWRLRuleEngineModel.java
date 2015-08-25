package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;

import java.io.File;
import java.util.Optional;

public class DefaultFileBackedSWRLRuleEngineModel extends DefaultSWRLRuleEngineModel
    implements FileBackedSWRLRuleEngineModel
{
  private Optional<File> file;

  public DefaultFileBackedSWRLRuleEngineModel(@NonNull SWRLRuleEngine ruleEngine, Optional<File> file)
  {
    super(ruleEngine);
    this.file = file;
  }

  @Override public void open(@NonNull File file) throws OWLOntologyCreationException
  {
    OWLOntology ontology = createOWLOntology(file);
    OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);
    DefaultPrefixManager prefixManager = new DefaultPrefixManager();
    if (format.isPrefixOWLOntologyFormat())
      prefixManager.copyPrefixesFrom(format.asPrefixOWLOntologyFormat().getPrefixName2PrefixMap());

    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology, prefixManager);

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
    OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);
    DefaultPrefixManager prefixManager = new DefaultPrefixManager();
    if (format.isPrefixOWLOntologyFormat())
      prefixManager.copyPrefixesFrom(format.asPrefixOWLOntologyFormat().getPrefixName2PrefixMap());

    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology, prefixManager);

    this.file = Optional.empty();

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
