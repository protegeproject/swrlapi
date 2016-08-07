package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLDatatypeResultValue;

class DefaultSQWRLDatatypeResultValue extends DefaultSQWRLEntityResultValue implements SQWRLDatatypeResultValue
{
  public DefaultSQWRLDatatypeResultValue(@NonNull IRI datatypeIRI, @NonNull String prefixedName,
    @NonNull String shortForm)
  {
    super(datatypeIRI, prefixedName, shortForm);
  }

  @Override public boolean isDatatype()
  {
    return true;
  }

  @NonNull @Override public SQWRLDatatypeResultValue asDatatypeResult() throws SQWRLException
  {
    return this;
  }
}
