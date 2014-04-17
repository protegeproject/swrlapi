package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.swrlapi.core.arguments.SWRLNamedBuiltInArgument;

abstract class SWRLNamedBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLNamedBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private final OWLEntity entity;

	public SWRLNamedBuiltInArgumentImpl(OWLEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public IRI getIRI()
	{
		return this.entity.getIRI();
	}

	@Override
	public String toDisplayText()
	{
		return getIRI().toString();
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	protected OWLEntity getOWLEntity()
	{
		return this.entity;
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
