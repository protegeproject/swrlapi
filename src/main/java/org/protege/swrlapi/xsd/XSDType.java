
package org.protege.swrlapi.xsd;

import java.net.URI;
import java.net.URISyntaxException;

import org.protege.swrlapi.exceptions.SQWRLLiteralException;

public abstract class XSDType implements Comparable<XSDType>
{
	private final String content;
	private URI uri = null;

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

	public URI getURI()
	{
		return this.uri;
	}

	public int compareTo(XSDType xsdType)
	{
		return this.content.compareTo(xsdType.getContent()); // TODO this is not correct - need to do actual value comparison
	}

	protected abstract void validate() throws SQWRLLiteralException;

	protected void setURI(String uriString) throws SQWRLLiteralException
	{
		try {
			this.uri = new URI(uriString);
		} catch (URISyntaxException e) {
			throw new SQWRLLiteralException("invalid URI " + this.uri + " associated with value " + this.content + "");
		}
	}
}
