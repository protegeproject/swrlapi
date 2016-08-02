package org.swrlapi.bridge.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/**
 * Interface describing the methods required to convert OWL class expressions to a target rule engine format.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 */
public interface TargetRuleEngineOWLClassExpressionConverter<C> extends TargetRuleEngineConverter
{
  @NonNull C convert(@NonNull OWLClassExpression classExpression);

  @NonNull C convert(@NonNull OWLClass cls);

  @NonNull C convert(@NonNull OWLObjectOneOf objectOneOf);

  @NonNull C convert(@NonNull OWLObjectIntersectionOf objectIntersectionOf);

  @NonNull C convert(@NonNull OWLObjectUnionOf objectUnionOf);

  @NonNull C convert(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom);

  @NonNull C convert(@NonNull OWLObjectComplementOf objectComplementOf);

  @NonNull C convert(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom);

  @NonNull C convert(@NonNull OWLDataExactCardinality dataExactCardinality);

  @NonNull C convert(@NonNull OWLObjectExactCardinality objectExactCardinality);

  @NonNull C convert(@NonNull OWLDataMinCardinality dataMinCardinality);

  @NonNull C convert(@NonNull OWLObjectMinCardinality objectMinCardinality);

  @NonNull C convert(@NonNull OWLDataMaxCardinality dataMaxCardinality);

  @NonNull C convert(@NonNull OWLObjectMaxCardinality objectMaxCardinality);

  @NonNull C convert(@NonNull OWLDataHasValue dataHasValue);

  @NonNull C convert(@NonNull OWLObjectHasValue objectHasValue);

  @NonNull C convert(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom);

  @NonNull C convert(@NonNull OWLDataAllValuesFrom dataAllValuesFrom);

  @NonNull C convert(@NonNull OWLObjectHasSelf owbjectHasSelf);
}
