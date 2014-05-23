package org.swrlapi.ui.core;

import java.util.Set;

public interface SWRLRulesPersistenceLayer
{
	Set<SWRLRuleModel> getSWRLRuleModels(String fileName);

	void saveSWRLRules(Set<SWRLRuleModel> swrlRuleModels, String fileName);
}