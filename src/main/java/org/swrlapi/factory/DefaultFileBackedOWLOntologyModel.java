package org.swrlapi.factory;

import java.io.File;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.ui.model.FileBackedOWLOntologyModel;

class DefaultFileBackedOWLOntologyModel implements FileBackedOWLOntologyModel, OWLOntologyChangeListener
{
	private final OWLOntology ontology;
	private File file;
	private boolean hasOntologyChanged;

	public DefaultFileBackedOWLOntologyModel(OWLOntology ontology, File file)
	{
		this.ontology = ontology;
		this.file = file;
		this.hasOntologyChanged = false;

		this.ontology.getOWLOntologyManager().addOntologyChangeListener(this);
	}

	@Override
	public OWLOntology getOWLOntology()
	{
		return this.ontology;
	}

	@Override
	public File getBackingFile()
	{
		return this.file;
	}

	@Override
	public void save() throws OWLOntologyStorageException
	{
		this.ontology.getOWLOntologyManager().saveOntology(ontology, IRI.create(this.file.toURI()));

		resetOntologyChanged();
	}

	@Override
	public void saveAs(File newFile) throws OWLOntologyStorageException
	{
		this.ontology.getOWLOntologyManager().saveOntology(ontology, IRI.create(newFile.toURI()));

		this.file = newFile;

		resetOntologyChanged();
	}

	@Override
	public boolean hasOntologyChanged()
	{
		return this.hasOntologyChanged;
	}

	@Override
	public void resetOntologyChanged()
	{
		this.hasOntologyChanged = false;
	}

	@Override
	public void ontologiesChanged(List<? extends OWLOntologyChange> var1) throws OWLException
	{
		this.hasOntologyChanged = true;
	}
}
