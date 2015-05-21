package org.swrlapi.core.xsd;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;

/**
 * Base class for the small set of temporal XSD types provided by the SWRLAPI.
 */
abstract class XSDType<T> implements Comparable<T>
{
  @NonNull protected final String content;
  @NonNull private final IRI iri;

  protected XSDType(@NonNull String content, @NonNull IRI iri)
  {
    this.content = content;
    this.iri = iri;
  }

  @NonNull public abstract String getContent();

  @NonNull @Override
  public String toString()
  {
    return this.content;
  }

  @NonNull public IRI getIRI()
  {
    return this.iri;
  }

  protected abstract void validate();
}
