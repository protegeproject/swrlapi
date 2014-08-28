package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.*;

/**
 * Interface describing the methods required to convert OWL class expressions to a target rule engine format.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 */
public interface TargetRuleEngineOWLClassExpressionConverter<C> extends TargetRuleEngineConverter
{
	C convert(OWLClass cls);

	C convert(OWLObjectOneOf objectOneOf);

	C convert(OWLObjectIntersectionOf objectIntersectionOf);

	C convert(OWLObjectUnionOf objectUnionOf);

	C convert(OWLObjectSomeValuesFrom objectSomeValuesFrom);

	C convert(OWLObjectComplementOf objectComplementOf);

	C convert(OWLDataSomeValuesFrom dataSomeValuesFrom);

	C convert(OWLDataExactCardinality dataExactCardinality);

	C convert(OWLObjectExactCardinality objectExactCardinality);

	C convert(OWLDataMinCardinality dataMinCardinality);

	C convert(OWLObjectMinCardinality objectMinCardinality);

	C convert(OWLDataMaxCardinality dataMaxCardinality);

	C convert(OWLObjectMaxCardinality objectMaxCardinality);

	C convert(OWLDataHasValue dataHasValue);

	C convert(OWLObjectHasValue objectHasValue);

	C convert(OWLObjectAllValuesFrom objectAllValuesFrom);

	C convert(OWLDataAllValuesFrom dataAllValuesFrom);
}
