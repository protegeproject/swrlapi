package org.protege.swrlapi.core.arguments.impl;

import java.util.Set;

import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
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

class SQWRLCollectionBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SQWRLCollectionBuiltInArgument
{
	private static final long serialVersionUID = 1518745061145936538L;

	private final String queryName, collectionName, collectionGroupID;

	public SQWRLCollectionBuiltInArgumentImpl(String queryName, String collectionName, String collectionGroupID)
	{
		this.queryName = queryName;
		this.collectionName = collectionName;
		this.collectionGroupID = collectionGroupID;
	}

	@Override
	public String getGroupID()
	{
		return this.collectionGroupID;
	}

	@Override
	public String getQueryName()
	{
		return this.queryName;
	}

	@Override
	public String getCollectionName()
	{
		return this.collectionName;
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	@Override
	public String toDisplayText()
	{
		return getQueryName() + ":" + getCollectionName() + "@" + getGroupID();
	}

	@Override
	public void accept(SWRLObjectVisitor visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLEntity> getSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isTopEntity()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isBottomEntity()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int compareTo(OWLObject o)
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
		SQWRLCollectionBuiltInArgumentImpl impl = (SQWRLCollectionBuiltInArgumentImpl)obj;
		return (getQueryName() == impl.getQueryName() || (getQueryName() != null && getQueryName().equals(
				impl.getQueryName())))
				&& (getCollectionName() == impl.getCollectionName() || (getCollectionName() != null && getCollectionName()
						.equals(impl.getCollectionName())))
				&& (getGroupID() == impl.getGroupID() || (getGroupID() != null && getGroupID().equals(impl.getGroupID())));
	}

	@Override
	public int hashCode()
	{
		int hash = 12;
		hash = hash + (null == getQueryName() ? 0 : getQueryName().hashCode());
		hash = hash + (null == getCollectionName() ? 0 : getCollectionName().hashCode());
		hash = hash + (null == getGroupID() ? 0 : getGroupID().hashCode());
		return hash;
	}
}
