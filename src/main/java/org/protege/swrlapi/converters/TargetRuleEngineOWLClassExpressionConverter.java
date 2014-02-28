
package org.protege.swrlapi.converters;

import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLObjectComplementOfAdapter;
import org.protege.owl.portability.model.OWLObjectIntersectionOfAdapter;
import org.protege.owl.portability.model.OWLObjectOneOfAdapter;
import org.protege.owl.portability.model.OWLObjectUnionOfAdapter;
import org.protege.owl.portability.restrictions.OWLDataAllValuesFromAdapter;
import org.protege.owl.portability.restrictions.OWLDataExactCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLDataHasValueAdapter;
import org.protege.owl.portability.restrictions.OWLDataMaxCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLDataMinCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLDataSomeValuesFromAdapter;
import org.protege.owl.portability.restrictions.OWLObjectAllValuesFromAdapter;
import org.protege.owl.portability.restrictions.OWLObjectExactCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLObjectHasValueAdapter;
import org.protege.owl.portability.restrictions.OWLObjectMaxCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLObjectMinCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLObjectSomeValuesFromAdapter;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Interface describing the methods required to convert OWL class expressions to a target rule engine format.
 */
public interface TargetRuleEngineOWLClassExpressionConverter<C> extends TargetRuleEngineConverter
{	
	C convert(OWLClassAdapter cls) throws TargetRuleEngineException;
	
	C convert(OWLObjectOneOfAdapter objectOneOf) throws TargetRuleEngineException;

	C convert(OWLObjectIntersectionOfAdapter objectIntersectionOf) throws TargetRuleEngineException;

	C convert(OWLObjectUnionOfAdapter objectUnionOf) throws TargetRuleEngineException;

	C convert(OWLObjectSomeValuesFromAdapter objectSomeValuesFrom) throws TargetRuleEngineException;

	C convert(OWLObjectComplementOfAdapter objectComplementOf) throws TargetRuleEngineException;

	C convert(OWLDataSomeValuesFromAdapter dataSomeValuesFrom) throws TargetRuleEngineException;

	C convert(OWLDataExactCardinalityAdapter dataExactCardinality) throws TargetRuleEngineException;

	C convert(OWLObjectExactCardinalityAdapter objectExactCardinality) throws TargetRuleEngineException;

	C convert(OWLDataMinCardinalityAdapter dataMinCardinality) throws TargetRuleEngineException;

	C convert(OWLObjectMinCardinalityAdapter objectMinCardinality) throws TargetRuleEngineException;

	C convert(OWLDataMaxCardinalityAdapter dataMaxCardinality) throws TargetRuleEngineException;

	C convert(OWLObjectMaxCardinalityAdapter objectMaxCardinality) throws TargetRuleEngineException;

	C convert(OWLDataHasValueAdapter dataHasValue) throws TargetRuleEngineException;

	C convert(OWLObjectHasValueAdapter objectHasValue) throws TargetRuleEngineException;

	C convert(OWLObjectAllValuesFromAdapter objectAllValuesFrom) throws TargetRuleEngineException;

	C convert(OWLDataAllValuesFromAdapter dataAllValuesFrom) throws TargetRuleEngineException;
}
