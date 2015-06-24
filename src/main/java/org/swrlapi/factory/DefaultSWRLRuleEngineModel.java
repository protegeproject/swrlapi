package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.ui.model.OWL2RLModel;
import org.swrlapi.ui.model.SWRLAutoCompleter;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

import java.util.List;

public class DefaultSWRLRuleEngineModel implements SWRLRuleEngineModel, OWLOntologyChangeListener
{
  @NonNull private OWLOntology ontology;
  @NonNull private SWRLRuleEngine ruleEngine;
  @NonNull private SWRLParser swrlParser;
  @NonNull private SWRLRuleRenderer swrlRuleRenderer;
  @NonNull private SWRLAutoCompleter swrlAutoCompleter;
  @NonNull private OWL2RLEngine owl2RLEngine;

  @NonNull private final SWRLRulesTableModel swrlRulesTableModel;
  @NonNull private final OWL2RLModel owl2RLModel;

  private boolean hasOntologyChanged;

  public DefaultSWRLRuleEngineModel(@NonNull OWLOntology ontology, @NonNull SWRLRuleEngine ruleEngine)
  {
    this.ontology = ontology;
    this.ruleEngine = ruleEngine;
    this.swrlRuleRenderer = this.ruleEngine.createSWRLRuleRenderer();
    this.swrlParser = this.ruleEngine.createSWRLParser();
    this.swrlAutoCompleter = this.ruleEngine.createSWRLAutoCompleter();
    this.owl2RLEngine = this.ruleEngine.getOWL2RLEngine();

    this.swrlRulesTableModel = SWRLAPIFactory.createSWRLRulesTableModel(ruleEngine, this.swrlRuleRenderer);
    this.owl2RLModel = SWRLAPIFactory.createOWL2RLModel(owl2RLEngine);

    this.ontology.getOWLOntologyManager().addOntologyChangeListener(this);

    this.hasOntologyChanged = false;
  }

  protected void updateModel(@NonNull OWLOntology ontology, @NonNull SWRLRuleEngine ruleEngine)
  {
    this.ontology = ontology;
    this.ruleEngine = ruleEngine;
    this.swrlRuleRenderer = this.ruleEngine.createSWRLRuleRenderer();
    this.swrlParser = this.ruleEngine.createSWRLParser();
    this.swrlAutoCompleter = this.ruleEngine.createSWRLAutoCompleter();
    this.owl2RLEngine = this.ruleEngine.getOWL2RLEngine();

    this.swrlRulesTableModel.updateModel(ruleEngine, this.swrlRuleRenderer);
    this.owl2RLModel.updateModel(owl2RLEngine);

    this.hasOntologyChanged = false;

    updateView();
  }

  @NonNull @Override public OWLOntology getOWLOntology()
  {
    return this.ontology;
  }

  @NonNull @Override public SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.ruleEngine;
  }

  @NonNull @Override public SWRLParser getSWRLParser()
  {
    return this.swrlParser;
  }

  @NonNull @Override public SWRLAutoCompleter getSWRLAutoCompleter()
  {
    return this.swrlAutoCompleter;
  }

  @NonNull @Override public SWRLRuleRenderer getSWRLRuleRenderer()
  {
    return this.swrlRuleRenderer;
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

  @Override public void updateView() { this.swrlRulesTableModel.updateView(); }
}
