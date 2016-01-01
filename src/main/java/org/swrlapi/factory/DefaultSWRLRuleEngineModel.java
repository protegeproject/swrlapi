package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

import java.io.File;

public class DefaultSWRLRuleEngineModel implements SWRLRuleEngineModel
{
  @NonNull private final OWLOntologyManager ontologyManager;
  @NonNull private SWRLAPIOWLOntology swrlapiOWLOntology;
  @NonNull private SWRLRuleEngine swrlRuleEngine;
  @NonNull private OWL2RLEngine owl2RLEngine;

  @NonNull private final SWRLRulesTableModel swrlRulesTableModel;
  @NonNull private final OWL2RLModel owl2RLModel;

  public DefaultSWRLRuleEngineModel(@NonNull SWRLRuleEngine swrlRuleEngine)
  {
    this.ontologyManager = OWLManager.createOWLOntologyManager();
    this.swrlRuleEngine = swrlRuleEngine;
    this.swrlapiOWLOntology = swrlRuleEngine.getSWRLAPIOWLOntology();
    this.owl2RLEngine = this.swrlRuleEngine.getOWL2RLEngine();

    this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(swrlRuleEngine);
    this.owl2RLModel = SWRLAPIFactory.createOWL2RLModel(owl2RLEngine);
  }

  @Override public void updateModel(@NonNull SWRLRuleEngine ruleEngine)
  {
    this.swrlRuleEngine = ruleEngine;
    this.swrlapiOWLOntology = ruleEngine.getSWRLAPIOWLOntology();
    this.owl2RLEngine = this.swrlRuleEngine.getOWL2RLEngine();

    this.swrlRulesTableModel.updateModel(ruleEngine);
    this.owl2RLModel.updateModel(owl2RLEngine);

    updateView();
  }

  @NonNull protected OWLOntology createOWLOntology() throws OWLOntologyCreationException
  {
    this.ontologyManager.removeOntology(this.swrlRuleEngine.getOWLOntology());
    return this.ontologyManager.createOntology();
  }

  protected void saveOWLOntology(@NonNull File file) throws OWLOntologyStorageException
  {
    this.getOWLOntology().getOWLOntologyManager().saveOntology(getOWLOntology(), IRI.create(file.toURI()));
  }

  @NonNull protected OWLOntology createOWLOntology(@NonNull File file) throws OWLOntologyCreationException
  {
    this.ontologyManager.removeOntology(this.swrlRuleEngine.getOWLOntology());
    return this.ontologyManager.loadOntologyFromOntologyDocument(file);
  }

  @NonNull @Override public SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.swrlRuleEngine;
  }

  @NonNull @Override public SWRLParser createSWRLParser()
  {
    return this.swrlRuleEngine.createSWRLParser();
  }

  @NonNull @Override public SWRLAutoCompleter createSWRLAutoCompleter()
  {
    return this.swrlRuleEngine.createSWRLAutoCompleter();
  }

  @NonNull @Override public SWRLRuleRenderer createSWRLRuleRenderer()
  {
    return this.swrlRuleEngine.createSWRLRuleRenderer();
  }

  @NonNull @Override public SWRLRulesTableModel getSWRLRulesTableModel()
  {
    return this.swrlRulesTableModel;
  }

  @NonNull @Override public OWL2RLModel getOWL2RLModel()
  {
    return this.owl2RLModel;
  }

  @Override public boolean areSWRLRulesModified()
  {
    return this.swrlRulesTableModel.hasBeenModified();
  }

  @Override public void clearSWRLRulesModified()
  {
    this.swrlRulesTableModel.clearModifiedStatus();
  }

  @Override public boolean hasOntologyChanged()
  {
    return this.swrlapiOWLOntology.hasOntologyChanged();
  }

  @Override public void resetOntologyChanged()
  {
    this.swrlapiOWLOntology.resetOntologyChanged();
  }

  @Override public void updateView() { this.swrlRulesTableModel.updateView(); }

  @NonNull private OWLOntology getOWLOntology()
  {
    return this.swrlRuleEngine.getOWLOntology();
  }
}
