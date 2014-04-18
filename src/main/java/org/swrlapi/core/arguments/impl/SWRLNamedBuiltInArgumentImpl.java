package org.swrlapi.core.arguments.impl;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
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
	public boolean containsEntityInSignature(OWLEntity owlEntity)
	{
		return false; // TODO
	}

	@Override
	public void accept(SWRLObjectVisitor visitor)
	{
		// TODO
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		// TODO
		return null;
	}

	@Override
	public Set<OWLEntity> getSignature()
	{
		return null; // TODO
	}

	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
		return Collections.emptySet(); // TODO
	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
		return Collections.emptySet(); // TODO
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
		return Collections.emptySet(); // TODO
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
		return Collections.emptySet(); // TODO
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
		return Collections.emptySet(); // TODO
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
		return Collections.emptySet(); // TODO
	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
		return Collections.emptySet(); // TODO
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

	@Override
	public boolean isTopEntity()
	{
		return false;
	}

	@Override
	public boolean isBottomEntity()
	{
		return false;
	}

	public int compareTo(SWRLNamedBuiltInArgument o)
	{
		return this.entity.getIRI().compareTo(o.getIRI());
	}

	@Override
	public int compareTo(OWLObject o)
	{
		if (!(o instanceof SWRLNamedBuiltInArgument))
			return -1;

		SWRLNamedBuiltInArgument other = (SWRLNamedBuiltInArgument)o;

		return compareTo(other);
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
