package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/**
 * @see OWLDataPropertyExpression
 */
public interface SWRLDataPropertyExpressionBuiltInArgument extends SWRLBuiltInArgument
{
  /**
   * @return An OWL data property expression
   */
  @NonNull OWLDataPropertyExpression getOWLDataPropertyExpression();
}