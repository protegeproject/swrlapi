package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.FileBackedModel;
import org.swrlapi.ui.model.FileBackedSQWRLQueryEngineModel;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;

import java.io.File;
import java.util.Optional;

public class DefaultFileBackedSQWRLQueryEngineModel extends DefaultSQWRLQueryEngineModel
		implements FileBackedSQWRLQueryEngineModel
{
	private @NonNull Optional<File> file;

	public DefaultFileBackedSQWRLQueryEngineModel(@NonNull OWLOntology ontology, @NonNull SQWRLQueryEngine queryEngine,
			Optional<File> file)
	{
		super(ontology, queryEngine);
		this.file = file;
	}

	@Override public void changeBackingFile(@NonNull File file)
	{
		this.file = Optional.of(file);
	}

	@Override public void clearBackingFile()
	{
		this.file = Optional.empty();
	}

	@Override public void save() throws OWLOntologyStorageException
	{
		if (this.file.isPresent())
			this.getOWLOntology().getOWLOntologyManager().saveOntology(getOWLOntology(), IRI.create(this.file.get().toURI()));

		resetOntologyChanged();
	}
}
