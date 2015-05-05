package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;

import java.io.File;
import java.util.List;

class DefaultFileBackedOWLOntologyModel implements FileBackedOWLOntologyModel, OWLOntologyChangeListener
{
  private final OWLOntology ontology;
  private final File file;
  private boolean hasOntologyChanged;

  public DefaultFileBackedOWLOntologyModel(OWLOntology ontology, File file)
  {
    this.ontology = ontology;
    this.file = file;
    this.hasOntologyChanged = false;

    this.ontology.getOWLOntologyManager().addOntologyChangeListener(this);
  }

  @Override public OWLOntology getOWLOntology()
  {
    return this.ontology;
  }

  @Override public File getBackingFile()
  {
    return file;
  }

  @Override public boolean hasOntologyChanged()
  {
    return this.hasOntologyChanged;
  }

  @Override public void resetOntologyChanged()
  {
    this.hasOntologyChanged = false;
  }

  @Override public void ontologiesChanged(List<? extends OWLOntologyChange> var1) throws OWLException
  {
    this.hasOntologyChanged = true;
  }
}
