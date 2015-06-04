package org.swrlapi.bridge.resolvers;

import checkers.nullness.quals.NonNull;

public interface OWLObjectResolver
{
	@NonNull OWLClassExpressionResolver getOWLClassExpressionResolver();

	@NonNull OWLIndividualResolver getOWLIndividualExpressionResolver();

	@NonNull OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver();

	@NonNull OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver();

	@NonNull OWLDataRangeResolver getOWLDataRangeResolver();
}
