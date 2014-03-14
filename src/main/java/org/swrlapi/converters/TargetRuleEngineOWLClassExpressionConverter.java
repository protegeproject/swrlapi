package org.swrlapi.converters;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Interface describing the methods required to convert OWL class expressions to a target rule engine format.
 */
public interface TargetRuleEngineOWLClassExpressionConverter<C> extends TargetRuleEngineConverter
{
	C convert(OWLClass cls) throws TargetRuleEngineException;

	C convert(OWLObjectOneOf objectOneOf) throws TargetRuleEngineException;

	C convert(OWLObjectIntersectionOf objectIntersectionOf) throws TargetRuleEngineException;

	C convert(OWLObjectUnionOf objectUnionOf) throws TargetRuleEngineException;

	C convert(OWLObjectSomeValuesFrom objectSomeValuesFrom) throws TargetRuleEngineException;

	C convert(OWLObjectComplementOf objectComplementOf) throws TargetRuleEngineException;

	C convert(OWLDataSomeValuesFrom dataSomeValuesFrom) throws TargetRuleEngineException;

	C convert(OWLDataExactCardinality dataExactCardinality) throws TargetRuleEngineException;

	C convert(OWLObjectExactCardinality objectExactCardinality) throws TargetRuleEngineException;

	C convert(OWLDataMinCardinality dataMinCardinality) throws TargetRuleEngineException;

	C convert(OWLObjectMinCardinality objectMinCardinality) throws TargetRuleEngineException;

	C convert(OWLDataMaxCardinality dataMaxCardinality) throws TargetRuleEngineException;

	C convert(OWLObjectMaxCardinality objectMaxCardinality) throws TargetRuleEngineException;

	C convert(OWLDataHasValue dataHasValue) throws TargetRuleEngineException;

	C convert(OWLObjectHasValue objectHasValue) throws TargetRuleEngineException;

	C convert(OWLObjectAllValuesFrom objectAllValuesFrom) throws TargetRuleEngineException;

	C convert(OWLDataAllValuesFrom dataAllValuesFrom) throws TargetRuleEngineException;
}
