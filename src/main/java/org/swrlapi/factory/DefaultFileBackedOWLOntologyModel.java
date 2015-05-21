package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;
import org.swrlapi.ui.model.SWRLRuleEngineModel;

import java.io.File;
import java.util.List;

class DefaultFileBackedOWLOntologyModel implements FileBackedOWLOntologyModel, OWLOntologyChangeListener
{
  @NonNull private final OWLOntology ontology;
  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private File file;
  @NonNull private boolean hasOntologyChanged;

  public DefaultFileBackedOWLOntologyModel(@NonNull OWLOntology ontology,
    @NonNull SWRLRuleEngineModel swrlRuleEngineModel, @NonNull File file)
  {
    this.ontology = ontology;
    this.swrlRuleEngineModel = swrlRuleEngineModel;
    this.file = file;
    this.hasOntologyChanged = false;

    this.ontology.getOWLOntologyManager().addOntologyChangeListener(this);
  }

  @NonNull @Override public OWLOntology getOWLOntology()
  {
    return this.ontology;
  }

  @NonNull @Override public SWRLRuleEngineModel getSWRLRuleEngineModel()
  {
    return this.swrlRuleEngineModel;
  }

  @NonNull @Override public File getBackingFile()
  {
    return this.file;
  }

  @Override public void save() throws OWLOntologyStorageException
  {
    this.ontology.getOWLOntologyManager().saveOntology(ontology, IRI.create(this.file.toURI()));

    resetOntologyChanged();
  }

  @Override public void saveAs(@NonNull File newFile) throws OWLOntologyStorageException
  {
    this.ontology.getOWLOntologyManager().saveOntology(ontology, IRI.create(newFile.toURI()));

    this.file = newFile;

    resetOntologyChanged();
  }

  @Override public boolean hasOntologyChanged()
  {
    return this.hasOntologyChanged;
  }

  @Override public void resetOntologyChanged()
  {
    this.hasOntologyChanged = false;
  }

  @Override public void ontologiesChanged(@NonNull List<? extends OWLOntologyChange> var1) throws OWLException
  {
    this.hasOntologyChanged = true;
  }

  @Override public void updateView()
  {
    this.swrlRuleEngineModel.updateView();
  }
}
