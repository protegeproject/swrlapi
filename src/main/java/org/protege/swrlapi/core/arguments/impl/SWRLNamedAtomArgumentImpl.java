package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLNamedObjectAdapter;
import org.protege.swrlapi.core.arguments.SWRLNamedAtomArgument;

abstract class SWRLNamedAtomArgumentImpl implements SWRLNamedAtomArgument
{
	private final URI uri;
	private final String prefixedName;

	public SWRLNamedAtomArgumentImpl(URI uri, String prefixedName)
	{
		this.uri = uri;
		this.prefixedName = prefixedName;
	}

	public SWRLNamedAtomArgumentImpl(OWLNamedObjectAdapter entity)
	{
		this.uri = entity.getURI();
		this.prefixedName = entity.getPrefixedName();
	}

	@Override
	public URI getURI()
	{
		return this.uri;
	}

	@Override
	public String getPrefixedName()
	{
		return this.prefixedName;
	}

	@Override
	public String toDisplayText()
	{
		return getPrefixedName();
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	public int compareTo(SWRLNamedAtomArgument o)
	{
		return this.uri.compareTo(o.getURI());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLNamedAtomArgumentImpl impl = (SWRLNamedAtomArgumentImpl)obj;
		return (getURI() == impl.getURI() || (getURI() != null && getURI().equals(impl.getURI())));
	}

	@Override
	public int hashCode()
	{
		int hash = 152;
		hash = hash + (null == getURI() ? 0 : getURI().hashCode());
		return hash;
	}
}
