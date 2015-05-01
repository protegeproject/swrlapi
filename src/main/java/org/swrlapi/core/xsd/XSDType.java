package org.swrlapi.core.xsd;

import org.semanticweb.owlapi.model.IRI;

/**
 * Base class the the small set of XSD types provided by the SWRLAPI.
 */
public abstract class XSDType<T> implements Comparable<T>
{
  protected final String content;
  private final IRI iri;

  public XSDType(String content, IRI iri)
  {
    this.content = content;
    this.iri = iri;
    validate();
  }

  public abstract String getContent();

  @Override
  public String toString()
  {
    return this.content;
  }

  public IRI getIRI()
  {
    return this.iri;
  }

  protected abstract void validate();
}
