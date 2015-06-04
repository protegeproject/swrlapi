package org.swrlapi.resolvers;

import checkers.nullness.quals.NonNull;

/**
 * Created by martin on 6/3/15.
 */
public interface OWLResolver
{
	@NonNull OWLClassExpressionResolver getOWLClassExpressionResolver();

	@NonNull OWLIndividualResolver getOWLIndividualExpressionResolver();

	@NonNull OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver();

	@NonNull OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver();

	@NonNull OWLDataRangeResolver getOWLDataRangeResolver();
}
