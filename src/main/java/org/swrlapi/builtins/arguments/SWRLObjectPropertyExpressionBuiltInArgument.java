package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @see OWLObjectPropertyExpression
 */
public interface SWRLObjectPropertyExpressionBuiltInArgument extends SWRLBuiltInArgument
{
  /**
   * @return An OWL object property expression
   */
  @NonNull OWLObjectPropertyExpression getOWLObjectPropertyExpression();
}