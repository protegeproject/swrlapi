package org.swrlapi.sqwrl.values;

import android.annotation.NonNull;

/**
 * Represents an OWL data range result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}
 *
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface SQWRLDataRangeResultValue extends SQWRLResultValue
{
  @NonNull String getRendering();
}
