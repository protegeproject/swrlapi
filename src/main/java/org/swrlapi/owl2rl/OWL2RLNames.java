package org.swrlapi.owl2rl;

public interface OWL2RLNames
{
	public final static String SWRLA_NAMESPACE = "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#";

	enum RuleTable
	{
		RuleTable4, RuleTable5, RuleTable6, RuleTable7, RuleTable8, RuleTable9
	}

	;

	enum Rule
	{
		EQ_REF, EQ_SYM, EQ_TRANS, EQ_REP_S, EQ_REP_P, EQ_REP_O, EQ_DIFF1, EQ_DIFF2, EQ_DIFF3, PRP_AP, PRP_DOM, PRP_RNG,
		PRP_FP,
		PRP_IFP, PRP_IRP, PRP_SYMP, PRP_ASYP, PRP_TRP, PRP_SPO1, PRP_SPO2, PRP_EQP1, PRP_EQP2, PRP_PDW, PRP_ADP, PRP_INV1,
		PRP_INV2, PRP_KEY, PRP_NPA1, PRP_NPA2,
		CLS_THING, CLS_NOTHING1, CLS_NOTHING2, CLS_INT1, CLS_INT2, CLS_UNI, CLS_COM, CLS_SFV1, CLS_SFV2, CLS_AVF, CLS_HV1, CLS_HV2, CLS_MAXC1, CLS_MAXC2,
		CLS_MAXQC1, CLS_MAXQC2, CLS_MAXQC3, CLS_MAXQC4, CLS_OO, CAX_SCO, CAX_EQC1, CAX_EQC2, CAX_DW, CAX_ADC, DT_TYPE1, DT_TYPE2, DT_EQ, DT_DIFF, DT_NOT_TYPE,
		SCM_CLS, SCM_SCO, SCM_EQC1, SCM_EQC2, SCM_OP, SCM_DP, SCM_SPO, SCM_EQP1, SCM_EQP2, SCM_DOM1, SCM_DOM2, SCM_RNG1, SCM_RNG2, SCM_HV, SCM_SVF1, SCM_SVF2,
		SCM_AVF1, SCM_AVF2, SCM_INT, SCM_UNI
	}

	;

	Rule[] Table4Rules = { Rule.EQ_REF, Rule.EQ_SYM, Rule.EQ_TRANS, Rule.EQ_REP_S, Rule.EQ_REP_P, Rule.EQ_REP_O,
			Rule.EQ_DIFF1, Rule.EQ_DIFF2, Rule.EQ_DIFF3 };

	Rule[] Table5Rules = { Rule.PRP_AP, Rule.PRP_DOM, Rule.PRP_RNG, Rule.PRP_FP, Rule.PRP_IFP, Rule.PRP_IRP,
			Rule.PRP_SYMP, Rule.PRP_ASYP, Rule.PRP_TRP, Rule.PRP_SPO1, Rule.PRP_SPO2, Rule.PRP_EQP1, Rule.PRP_EQP2,
			Rule.PRP_PDW, Rule.PRP_ADP, Rule.PRP_INV1, Rule.PRP_INV2, Rule.PRP_KEY, Rule.PRP_NPA1, Rule.PRP_NPA2 };

	Rule[] Table6Rules = { Rule.CLS_THING, Rule.CLS_NOTHING1, Rule.CLS_NOTHING2, Rule.CLS_INT1, Rule.CLS_INT2,
			Rule.CLS_UNI, Rule.CLS_COM, Rule.CLS_SFV1, Rule.CLS_SFV2, Rule.CLS_AVF, Rule.CLS_HV1,
			Rule.CLS_HV2, Rule.CLS_MAXC1, Rule.CLS_MAXC2, Rule.CLS_MAXQC1, Rule.CLS_MAXQC2, Rule.CLS_MAXQC3, Rule.CLS_MAXQC4,
			Rule.CLS_OO };

	Rule[] Table7Rules = { Rule.CAX_SCO, Rule.CAX_EQC1, Rule.CAX_EQC2, Rule.CAX_DW, Rule.CAX_ADC };

	Rule[] Table8Rules = { Rule.DT_TYPE1, Rule.DT_TYPE2, Rule.DT_EQ, Rule.DT_DIFF, Rule.DT_NOT_TYPE };

	Rule[] Table9Rules = { Rule.SCM_CLS, Rule.SCM_SCO, Rule.SCM_EQC1, Rule.SCM_EQC2, Rule.SCM_OP, Rule.SCM_DP,
			Rule.SCM_SPO, Rule.SCM_EQP1, Rule.SCM_EQP2, Rule.SCM_DOM1, Rule.SCM_DOM2, Rule.SCM_RNG1, Rule.SCM_RNG2,
			Rule.SCM_HV, Rule.SCM_SVF1, Rule.SCM_SVF2, Rule.SCM_AVF1, Rule.SCM_AVF2, Rule.SCM_INT, Rule.SCM_UNI };

	public static interface Annotations
	{
		public final static String OWL2RL_RULE = (SWRLA_NAMESPACE + "OWL2RLRule").intern();
		public final static String IS_OWL2RL_RULE_ENABLED = (SWRLA_NAMESPACE + "isOWL2RLRuleEnabled").intern();
	}
}
