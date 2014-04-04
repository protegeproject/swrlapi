package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.core.arguments.SWRLNamedBuiltInArgument;

abstract class SWRLNamedBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLNamedBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private final IRI uri;

	public SWRLNamedBuiltInArgumentImpl(IRI uri)
	{
		this.uri = uri;
	}

	public SWRLNamedBuiltInArgumentImpl(OWLNamedObject entity)
	{
		this.uri = entity.getIRI();
	}

	@Override
	public IRI getIRI()
	{
		return this.uri;
	}

	@Override
	public String toDisplayText()
	{
		throw new RuntimeException("Not implemented");
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
