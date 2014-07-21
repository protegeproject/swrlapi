package org.swrlapi.owl2rl;

import java.util.HashMap;
import java.util.Map;

public class OWL2RLInconsistency
{
	private static Map<String, OWL2RLRuleArguments> argumentsDescriptionMap;

	static {
		argumentsDescriptionMap = new HashMap<String, OWL2RLRuleArguments>();

		createArgumentDescription(OWL2RLNames.Rule.EQ_DIFF1.toString(), 0, 2, 0, 0);
		createArgumentDescription(OWL2RLNames.Rule.PRP_PDW.toString(), 0, 2, 2, 0);
		createArgumentDescription(OWL2RLNames.Rule.PRP_ASYP.toString(), 0, 2, 1, 0);
		createArgumentDescription(OWL2RLNames.Rule.CAX_DW.toString(), 2, 1, 0, 0);
	}

	public static boolean hasInconsistencyRuleArgumentsDescription(String owl2RLRuleName)
	{
		return argumentsDescriptionMap.containsKey(owl2RLRuleName);
	}

	public static OWL2RLRuleArguments getRuleArguments(String owl2RLRuleName)
	{
		return argumentsDescriptionMap.get(owl2RLRuleName);
	}

	private static void createArgumentDescription(String owl2RLRuleName, int numberOfClassArguments,
			int numberOfIndividualArguments, int numberOfObjectPropertyArguments, int numberOfDataPropertyArguments)
	{
		argumentsDescriptionMap.put(owl2RLRuleName, new OWL2RLRuleArguments(numberOfClassArguments,
				numberOfIndividualArguments, numberOfObjectPropertyArguments, numberOfDataPropertyArguments));
	}

	public static class OWL2RLRuleArguments
	{
		private final int numberOfClassArguments;
		private final int numberOfIndividualArguments;
		private final int numberOfObjectPropertyArguments;
		private final int numberOfDataPropertyArguments;

		public OWL2RLRuleArguments(int numberOfClassArguments, int numberOfIndividualArguments,
				int numberOfObjectPropertyArguments, int numberOfDataPropertyArguments)
		{
			this.numberOfClassArguments = numberOfClassArguments;
			this.numberOfIndividualArguments = numberOfIndividualArguments;
			this.numberOfObjectPropertyArguments = numberOfObjectPropertyArguments;
			this.numberOfDataPropertyArguments = numberOfDataPropertyArguments;
		}

		public boolean hasClassArguments()
		{
			return getNumberOfClassArguments() != 0;
		}

		public boolean hasIndividualArguments()
		{
			return getNumberOfIndividualArguments() != 0;
		}

		public boolean hasObjectPropertyArguments()
		{
			return getNumberOfObjectPropertyArguments() != 0;
		}

		public boolean hasDataPropertyArguments()
		{
			return getNumberOfDataPropertyArguments() != 0;
		}

		public int getNumberOfClassArguments()
		{
			return numberOfClassArguments;
		}

		public int getNumberOfIndividualArguments()
		{
			return numberOfIndividualArguments;
		}

		public int getNumberOfDataPropertyArguments()
		{
			return numberOfDataPropertyArguments;
		}

		public int getNumberOfObjectPropertyArguments()
		{
			return numberOfObjectPropertyArguments;
		}
	}

}
