package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLPropertyResultValue;

abstract class DefaultSQWRLPropertyResultValue extends DefaultSQWRLEntityResultValue implements SQWRLPropertyResultValue
{
  DefaultSQWRLPropertyResultValue(@NonNull IRI propertyIRI, @NonNull String prefixedName)
  {
    super(propertyIRI, prefixedName);
  }
}
