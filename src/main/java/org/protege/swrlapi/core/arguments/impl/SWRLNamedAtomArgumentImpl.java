package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLNamedAtomArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;

abstract class SWRLNamedAtomArgumentImpl extends SWRLAtomArgumentImpl implements SWRLNamedAtomArgument
{
	private static final long serialVersionUID = -5547990984547544944L;

	private final IRI uri;
	private final String prefixedName;

	public SWRLNamedAtomArgumentImpl(IRI uri, String prefixedName)
	{
		this.uri = uri;
		this.prefixedName = prefixedName;
	}

	public SWRLNamedAtomArgumentImpl(OWLNamedObject entity)
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

	public int compareTo(SWRLNamedAtomArgument o)
	{
		return this.uri.compareTo(o.getIRI());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLNamedAtomArgumentImpl impl = (SWRLNamedAtomArgumentImpl)obj;
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
