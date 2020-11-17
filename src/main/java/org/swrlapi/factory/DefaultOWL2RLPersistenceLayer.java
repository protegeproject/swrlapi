package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

// TODO Implement OWL 2 RL persistence layer
public class DefaultOWL2RLPersistenceLayer implements OWL2RLPersistenceLayer
{
  @SuppressWarnings("unused") private final @NonNull OWLOntology ontology;

  private final Set<OWL2RLNames.OWL2RLRule> disabledRules;

  public DefaultOWL2RLPersistenceLayer(@NonNull OWLOntology ontology)
  {
    this.ontology = ontology;
    this.disabledRules = new HashSet<>();
  }

  @NonNull @Override public Set<OWL2RLNames.OWL2RLRule> getEnabledRules()
  {
    // If not explicitly disabled, assume enabled
    Set<OWL2RLNames.OWL2RLRule> enabledRules = new HashSet<>();
    for (OWL2RLNames.OWL2RLRule rule : EnumSet.allOf(OWL2RLNames.OWL2RLRule.class))
      if (!isOWL2RLRuleDisabled(rule))
        enabledRules.add(rule);
    return enabledRules;
  }

  @Override public void setEnabledRules(@NonNull Set<OWL2RLNames.OWL2RLRule> rules)
  {
    this.disabledRules.removeAll(rules);

    // TODO implement enable/disableRule persistence with annotation properties in ontology
    // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE +
    // rule.toString());
    // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
    // if (p3OWLIndividual != null && p3OWLDataProperty != null) // Rather than setting value to true, we remove
    // // property value so it will default to true
    // p3OWLIndividual.removePropertyValue(p3OWLDataProperty, false);
    //rules.stream().filter(this::isOWL2RLRuleDisabled).forEach(rule -> {
    // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE +
    // rule.toString());
    // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
    // if (p3OWLIndividual != null && p3OWLDataProperty != null) // Rather than setting value to true, we remove
    // // property value so it will default to true
    // p3OWLIndividual.removePropertyValue(p3OWLDataProperty, false);
    // });
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
    //EnumSet.allOf(OWL2RLNames.OWL2RLRule.class).forEach(this::disableRule);
    for (OWL2RLNames.OWL2RLRule rule : EnumSet.allOf(OWL2RLNames.OWL2RLRule.class))
      disableRule(rule);
  }

  private void disableRule(OWL2RLNames.OWL2RLRule rule)
  {
    if (!isOWL2RLRuleDisabled(rule)) {
      this.disabledRules.add(rule);
      // TODO implement disableRule persistence with annotation properties in ontology
      // OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE + rule.toString());
      // OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
      // if (p3OWLIndividual != null && p3OWLDataProperty != null)
      // p3OWLIndividual.setPropertyValue(p3OWLDataProperty, false);
    }
  }

  private boolean isOWL2RLRuleDisabled(OWL2RLNames.OWL2RLRule rule)
  {
    return this.disabledRules.contains(rule);
    // TODO implement disableRule persistence with annotation properties in ontology
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
  }
}
