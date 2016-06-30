package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @see OWLClassExpression
 */
public interface SWRLClassExpressionBuiltInArgument extends SWRLBuiltInArgument
{
  /**
   * @return An OWL class expression
   */
  @NonNull OWLClassExpression getOWLClassExpression();
}