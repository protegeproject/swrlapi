package org.swrlapi.sqwrl.values;

import android.annotation.NonNull;

/**
 * Represents an OWL individual result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 *
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.semanticweb.owlapi.model.OWLIndividual
 */
public interface SQWRLIndividualResultValue extends SQWRLResultValue
{
  @NonNull String getRendering();
}
