package org.protege.swrlapi.xsd;

import org.protege.swrlapi.exceptions.SQWRLLiteralException;
import org.semanticweb.owlapi.model.IRI;

public abstract class XSDType implements Comparable<XSDType>
{
	private final String content;
	private IRI uri = null;

	public XSDType(String content) throws SQWRLLiteralException
	{
		this.content = content;
		validate();
	}

	public String getContent()
	{
		return this.content;
	}

	@Override
	public String toString()
	{
		return this.content;
	}

	public IRI getURI()
	{
		return this.uri;
	}

	@Override
	public int compareTo(XSDType xsdType)
	{
		return this.content.compareTo(xsdType.getContent()); // TODO this is not correct - need to do actual value
																													// comparison
	}

	protected abstract void validate() throws SQWRLLiteralException;

	protected void setURI(IRI iri) throws SQWRLLiteralException
	{
		this.uri = iri;
	}
}
