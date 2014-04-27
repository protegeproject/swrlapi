package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
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

	protected OWLEntity getOWLEntity()
	{
		return this.entity;
	}

	@Override
	public int compareTo(OWLObject o)
	{
		if (!(o instanceof SWRLNamedBuiltInArgumentImpl))
			return -1;

		SWRLNamedBuiltInArgumentImpl other = (SWRLNamedBuiltInArgumentImpl)o;

		return this.entity.getIRI().compareTo(other.entity.getIRI());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLNamedBuiltInArgumentImpl impl = (SWRLNamedBuiltInArgumentImpl)obj;
		return (this.entity.getIRI() == impl.entity.getIRI() || (this.entity.getIRI() != null && this.entity.getIRI()
				.equals(impl.entity.getIRI())));
	}

	@Override
	public int hashCode()
	{
		int hash = 152;
		hash = hash + (null == this.entity.getIRI() ? 0 : this.entity.getIRI().hashCode());
		return hash;
	}

	@Override
	public void accept(SWRLObjectVisitor visitor)
	{
		// TODO
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		return null; // TODO
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		// TODO
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		return null; // TODO
	}
}
