package org.swrlapi.ui.core;

import java.util.Set;

import org.swrlapi.ui.model.SWRLRuleModel;

public interface SWRLRulesPersistenceLayer
{
	Set<SWRLRuleModel> getSWRLRuleModels(String fileName);

	void saveSWRLRules(Set<SWRLRuleModel> swrlRuleModels, String fileName);
}