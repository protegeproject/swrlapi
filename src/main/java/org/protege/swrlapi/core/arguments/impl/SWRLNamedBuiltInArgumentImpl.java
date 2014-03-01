package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLNamedBuiltInArgument;
import org.semanticweb.owlapi.model.OWLNamedObject;

abstract class SWRLNamedBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLNamedBuiltInArgument
{
	private static final long serialVersionUID = 6305671503926589607L;

	private final URI uri;
	private final String prefixedName;

	public SWRLNamedBuiltInArgumentImpl(URI uri, String prefixedName)
	{
		this.uri = uri;
		this.prefixedName = prefixedName;
	}

	public SWRLNamedBuiltInArgumentImpl(OWLNamedObject entity)
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

	// TODO fix
	@Override
	public int compareTo(SWRLBuiltInArgument o)
	{
		return this.uri.compareTo(((SWRLDatatypeBuiltInArgument)o).getURI());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLNamedBuiltInArgumentImpl impl = (SWRLNamedBuiltInArgumentImpl)obj;
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
