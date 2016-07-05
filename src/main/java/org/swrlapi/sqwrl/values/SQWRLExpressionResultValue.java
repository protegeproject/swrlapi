package org.swrlapi.sqwrl.values;

import android.annotation.NonNull;

/**
 * Represents an OWL expression result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 *
 * @see org.swrlapi.sqwrl.SQWRLResult
 */
public interface SQWRLExpressionResultValue extends SQWRLResultValue
{
  @NonNull public String getRendering();
}