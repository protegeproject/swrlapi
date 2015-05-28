package org.swrlapi.sqwrl;

import checkers.nullness.quals.NonNull;
import org.swrlapi.visitors.SWRLAPIEntityVisitorEx;

/**
 * A renderer for a {@link org.swrlapi.sqwrl.SQWRLQuery}.
 *
 * Can create using the {@link org.swrlapi.core.SWRLAPIOWLOntology}.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 */
public interface SQWRLQueryRenderer extends SWRLAPIEntityVisitorEx<String>
{
  /**
   * @param query The query to render
   * @return A query rendering
   */
  @NonNull String renderSQWRLQuery(SQWRLQuery query);
}
