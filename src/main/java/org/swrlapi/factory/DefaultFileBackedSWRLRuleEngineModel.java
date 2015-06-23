package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.ui.model.FileBackedSWRLRuleEngineModel;

import java.io.File;
import java.util.Optional;

public class DefaultFileBackedSWRLRuleEngineModel extends DefaultSWRLRuleEngineModel
		implements FileBackedSWRLRuleEngineModel
{
  @NonNull private final OWLOntologyManager ontologyManager;

  private Optional<File> file;

  public DefaultFileBackedSWRLRuleEngineModel(@NonNull OWLOntology ontology, @NonNull SWRLRuleEngine ruleEngine,
			Optional<File> file)
	{
		super(ontology, ruleEngine);
		this.file = file;
    this.ontologyManager = OWLManager.createOWLOntologyManager();
	}

	@Override public void open(@NonNull File file) throws OWLOntologyCreationException
	{
    OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(file);
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

		this.file = Optional.of(file);
    updateModel(ontology, ruleEngine);
	}

	@Override public void saveAs(@NonNull File file) throws OWLOntologyStorageException
	{
		this.file = Optional.of(file);

    this.getOWLOntology().getOWLOntologyManager().saveOntology(getOWLOntology(), IRI.create(this.file.get().toURI()));

    resetOntologyChanged();
	}

	@Override public void close() throws OWLOntologyCreationException
	{
    OWLOntology ontology = ontologyManager.createOntology();
    SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

    this.file = Optional.empty();
    updateModel(ontology, ruleEngine);
	}

	@Override public void save() throws OWLOntologyStorageException
	{
		if (this.file.isPresent())
			this.getOWLOntology().getOWLOntologyManager().saveOntology(getOWLOntology(), IRI.create(this.file.get().toURI()));

		resetOntologyChanged();
	}
}
