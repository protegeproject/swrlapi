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
import java.util.Optional;

class DefaultFileBackedOWLOntologyModel implements FileBackedOWLOntologyModel, OWLOntologyChangeListener
{
  @NonNull private final OWLOntology ontology;
  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private Optional<File> file;
  @NonNull private boolean hasOntologyChanged;

  public DefaultFileBackedOWLOntologyModel(@NonNull OWLOntology ontology,
    @NonNull SWRLRuleEngineModel swrlRuleEngineModel, Optional<File> file)
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

  @Override public void setBackingFile(Optional<File> file)
  {
    this.file = file;
  }

  @Override public void save() throws OWLOntologyStorageException
  {
    if (this.file.isPresent())
      this.ontology.getOWLOntologyManager().saveOntology(ontology, IRI.create(this.file.get().toURI()));

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
