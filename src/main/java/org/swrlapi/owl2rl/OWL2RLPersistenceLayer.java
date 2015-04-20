package org.swrlapi.owl2rl;

import java.util.Set;

/**
 * Interface defining methods necessary to persist OWL 2 RL rule selections. Implementations will likely use OWL
 * annotation properties to store these selections.
 *
 * @see org.swrlapi.owl2rl.DefaultOWL2RLPersistenceLayer
 */
public interface OWL2RLPersistenceLayer
{
	/**
	 * @return The enabled OWL 2 RL rules
	 */
	Set<OWL2RLNames.Rule> getEnabledRules();

	/**
	 * @param rules The OWL 2 RL rule to enable
	 */
	void setEnabledRules(Set<OWL2RLNames.Rule> rules);

	/**
	 * @param rule The OWL 2 RL rule to disable
	 */
	void setDisabledRule(OWL2RLNames.Rule rule);

	/**
	 * @param rules The OWL 2 RL rules to disable
	 */
	void setDisabledRules(Set<OWL2RLNames.Rule> rules);

	/**
	 * Disable all rules
	 */
	void disableAll();
}
