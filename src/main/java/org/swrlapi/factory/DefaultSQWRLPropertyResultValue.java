package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLPropertyResultValue;

abstract class DefaultSQWRLPropertyResultValue extends DefaultSQWRLEntityResultValue implements SQWRLPropertyResultValue
{
  DefaultSQWRLPropertyResultValue(@NonNull IRI propertyIRI, @NonNull String prefixedName, @NonNull String shortForm)
  {
    super(propertyIRI, prefixedName, shortForm);
  }
}
