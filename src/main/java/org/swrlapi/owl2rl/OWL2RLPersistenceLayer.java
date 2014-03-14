package org.swrlapi.owl2rl;

import java.util.Set;

/**
 * Interface defining methods necessary to persist OWL 2 RL rule selections. Implementations will likely use OWL annotation properties to store these
 * selections. See the P3OWL2RLPersistenceLayer class in the owl-portability-p3 project for an example implementation.
 */
public interface OWL2RLPersistenceLayer
{
	Set<OWL2RLNames.Rule> getEnabledRules();

	void setEnabledRules(Set<OWL2RLNames.Rule> rules);

	void setDisabledRule(OWL2RLNames.Rule rule);

	void setDisabledRules(Set<OWL2RLNames.Rule> rules);

	void disableAll();
}
