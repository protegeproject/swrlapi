package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

// TODO Implement OWL 2 RL persistence layer
public class DefaultOWL2RLPersistenceLayer implements OWL2RLPersistenceLayer
{
  @SuppressWarnings("unused") private final @NonNull OWLOntology ontology;

  public DefaultOWL2RLPersistenceLayer(@NonNull OWLOntology ontology)
  {
    this.ontology = ontology;
  }

  @NonNull @Override public Set<OWL2RLNames.OWL2RLRule> getEnabledRules()
  { // If not explicitly disabled, assume enabled
    return EnumSet.allOf(OWL2RLNames.OWL2RLRule.class).stream().filter(rule -> !isOWL2RLRuleDisabled(rule))
      .collect(Collectors.toSet());
  }

  @Override public void setEnabledRules(@NonNull Set<OWL2RLNames.OWL2RLRule> rules)
  {
    // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE +
    // rule.toString());
    // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
    // if (p3OWLIndividual != null && p3OWLDataProperty != null) // Rather than setting value to true, we remove
    // // property value so it will default to true
    // p3OWLIndividual.removePropertyValue(p3OWLDataProperty, false);
    rules.stream().filter(this::isOWL2RLRuleDisabled).forEach(rule -> {
      // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE +
      // rule.toString());
      // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
      // if (p3OWLIndividual != null && p3OWLDataProperty != null) // Rather than setting value to true, we remove
      // // property value so it will default to true
      // p3OWLIndividual.removePropertyValue(p3OWLDataProperty, false);
    });
  }

  @Override public void setDisabledRule(OWL2RLNames.OWL2RLRule rule)
  {
    disableRule(rule);
  }

  @Override public void setDisabledRules(@NonNull Set<OWL2RLNames.OWL2RLRule> rules)
  {
    rules.forEach(this::disableRule);
  }

  @Override public void disableAll()
  {
    EnumSet.allOf(OWL2RLNames.OWL2RLRule.class).forEach(this::disableRule);
  }

  private void disableRule(OWL2RLNames.OWL2RLRule rule)
  {
    if (!isOWL2RLRuleDisabled(rule)) { // TODO
      // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE + rule.toString());
      // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
      // if (p3OWLIndividual != null && p3OWLDataProperty != null)
      // p3OWLIndividual.setPropertyValue(p3OWLDataProperty, false);
    }
  }

  private boolean isOWL2RLRuleDisabled(OWL2RLNames.OWL2RLRule rule)
  {
    // TODO
    // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE + rule.toString());
    // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
    //
    // if (p3OWLIndividual != null && p3OWLDataProperty != null) {
    // Object p3Value = p3OWLIndividual.getPropertyValue(p3OWLDataProperty);
    // if (p3Value != null && (p3Value instanceof Boolean)) {
    // return !(Boolean)p3Value;
    // } else
    // return false; // No value (or some odd value), so we default to enabled
    // } else
    // // If the individual or property are null, then the annotations ontology is not loaded so there is no persistence
    // // so we default to enabled.
    return false;
  }
}
