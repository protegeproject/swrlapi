package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLNamedBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;

abstract class SWRLNamedBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLNamedBuiltInArgument
{
	private static final long serialVersionUID = 6305671503926589607L;

	private final IRI uri;
	private final String prefixedName;

	public SWRLNamedBuiltInArgumentImpl(IRI uri, String prefixedName)
	{
		this.uri = uri;
		this.prefixedName = prefixedName;
	}

	public SWRLNamedBuiltInArgumentImpl(OWLNamedObject entity)
	{
		this.uri = entity.getIRI();
		this.prefixedName = entity.getPrefixedName();
	}

	@Override
	public IRI getIRI()
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
		return this.uri.compareTo(((SWRLDatatypeBuiltInArgument)o).getIRI());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLNamedBuiltInArgumentImpl impl = (SWRLNamedBuiltInArgumentImpl)obj;
		return (getIRI() == impl.getIRI() || (getIRI() != null && getIRI().equals(impl.getIRI())));
	}

	@Override
	public int hashCode()
	{
		int hash = 152;
		hash = hash + (null == getIRI() ? 0 : getIRI().hashCode());
		return hash;
	}
}
