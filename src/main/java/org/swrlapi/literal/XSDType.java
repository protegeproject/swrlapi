package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;

/**
 * Base class for the small set of temporal XSD types provided by the SWRLAPI.
 */
abstract class XSDType<T extends @NonNull Object> implements Comparable<T>
{
  @NonNull private final String content;
  @NonNull private final IRI iri;

  protected XSDType(@NonNull String content, @NonNull IRI iri)
  {
    this.content = content;
    this.iri = iri;
  }

  @NonNull public String getContent() { return this.content; }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return this.content;
  }

  @NonNull public IRI getIRI()
  {
    return this.iri;
  }

  protected abstract void validate();
}
