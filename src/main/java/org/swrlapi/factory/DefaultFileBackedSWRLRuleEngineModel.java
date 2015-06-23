package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;

import java.io.File;
import java.util.Optional;

public class DefaultFileBackedSWRLRuleEngineModel extends DefaultSWRLRuleEngineModel
		implements FileBackedSWRLRuleEngineModel
{
	private @NonNull Optional<File> file;

	public DefaultFileBackedSWRLRuleEngineModel(@NonNull OWLOntology ontology, @NonNull SWRLRuleEngine ruleEngine,
			Optional<File> file)
	{
		super(ontology, ruleEngine);
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
