package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;

class DefaultSQWRLClassResultValue extends DefaultSQWRLEntityResultValue implements SQWRLClassResultValue
{
  public DefaultSQWRLClassResultValue(@NonNull IRI classIRI, @NonNull String prefixedName)
  {
    super(classIRI, prefixedName);
  }

  @Override public boolean isClass()
  {
    return true;
  }
}
