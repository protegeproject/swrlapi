package org.swrlapi.owl2rl;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.swrlapi.core.SWRLAPIOWLOntology;

// TODO Implement OWL 2 RL persistence layer
public class DefaultOWL2RLPersistenceLayer implements OWL2RLPersistenceLayer
{
	@SuppressWarnings("unused")
	private final SWRLAPIOWLOntology swrlapiOWLOntology;

	public DefaultOWL2RLPersistenceLayer(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;
	}

	@Override
	public Set<OWL2RLNames.OWL2RLRule> getEnabledRules()
	{
		Set<OWL2RLNames.OWL2RLRule> enabledRules = new HashSet<>();

		for (OWL2RLNames.OWL2RLRule rule : EnumSet.allOf(OWL2RLNames.OWL2RLRule.class))
			if (!isOWL2RLRuleDisabled(rule)) // If not explicitly disabled, assume enabled
				enabledRules.add(rule);

		return enabledRules;
	}

	@Override
	public void setEnabledRules(Set<OWL2RLNames.OWL2RLRule> rules)
	{
		for (OWL2RLNames.OWL2RLRule rule : rules) {
			if (isOWL2RLRuleDisabled(rule)) {
				// OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE +
				// rule.toString());
				// OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
				// if (p3OWLIndividual != null && p3OWLDataProperty != null) // Rather than setting value to true, we remove
				// // property value so it will default to true
				// p3OWLIndividual.removePropertyValue(p3OWLDataProperty, false);
			}
		}
	}

	@Override
	public void setDisabledRule(OWL2RLNames.OWL2RLRule rule)
	{
		disableRule(rule);
	}

	@Override
	public void setDisabledRules(Set<OWL2RLNames.OWL2RLRule> rules)
	{
		for (OWL2RLNames.OWL2RLRule rule : rules)
			disableRule(rule);
	}

	@Override
	public void disableAll()
	{
		for (OWL2RLNames.OWL2RLRule rule : EnumSet.allOf(OWL2RLNames.OWL2RLRule.class))
			disableRule(rule);
	}

	private void disableRule(OWL2RLNames.OWL2RLRule rule)
	{
		if (!isOWL2RLRuleDisabled(rule)) {
			// OWLIndividual p3OWLIndividual = getOWLModel().getOWLIndividual(OWL2RLNames.SWRLA_NAMESPACE + rule.toString());
			// OWLDatatypeProperty p3OWLDataProperty = getIsOWL2RLRuleEnabledProperty();
			// if (p3OWLIndividual != null && p3OWLDataProperty != null)
			// p3OWLIndividual.setPropertyValue(p3OWLDataProperty, false);
		}
	}

	private boolean isOWL2RLRuleDisabled(OWL2RLNames.OWL2RLRule rule)
	{
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
